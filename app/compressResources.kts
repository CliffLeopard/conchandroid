override fun apply(p0: Project) {
    // 总配置
    p0.extensions.create(CONFIG_NAME, Config::class.java)
    // 重复资源配置
    p0.extensions.create(REPEAT_RES_CONFIG_NAME, RepeatResConfig::class.java)
    // 压缩图片配置
    p0.extensions.create(COMPRESS_IMG_CONFIG_NAME, CompressImgConfig::class.java)

    val hasAppPlugin = p0.plugins.hasPlugin(AppPlugin::class.java)
    if (hasAppPlugin) {
        p0.afterEvaluate {
            FileUtil.setRootDir(p0.rootDir.path)
            print("PluginTest Config " + p0.extensions.findByName(CONFIG_NAME))
            val config: Config? = p0.extensions.findByName(CONFIG_NAME) as? Config
            val repeatResConfig =
                p0.extensions.findByName(REPEAT_RES_CONFIG_NAME) as? RepeatResConfig
            val compressImgConfig =
                p0.extensions.findByName(COMPRESS_IMG_CONFIG_NAME) as? CompressImgConfig

            // 不开启插件
            if (config?.enable == false) {
                return@afterEvaluate
            }

            val byType = p0.extensions.getByType(AppExtension::class.java)

            byType.applicationVariants.forEach {
                val variantName = it.name.capitalize()
                val processRes = p0.tasks.getByName("process${variantName}Resources")
                processRes.doLast {
                    val resourcesTask =
                        it as LinkApplicationAndroidResourcesTask
                    val files = resourcesTask.resPackageOutputFolder.asFileTree.files
                    files.filter { file ->
                        file.name.endsWith(".ap_")
                    }.forEach { apFile ->
                        val mapping =
                            "${p0.buildDir}${File.separator}ResDeduplication${File.separator}mapping${File.separator}"
                        File(mapping).takeIf { fileMapping ->
                            !fileMapping.exists()
                        }?.apply {
                            mkdirs()
                        }

                        val originalLength = apFile.length()
                        val resCompressFile = File(mapping, REPEAT_RES_MAPPING)
                        val unZipPath = "${apFile.parent}${File.separator}resCompress"
                        ZipFile(apFile).unZipFile(unZipPath)

                        // 删除重复图片
                        deleteRepeatRes(
                            unZipPath,
                            resCompressFile,
                            apFile,
                            repeatResConfig?.whiteListName
                        )
                        // 压缩图片
                        compressImg(mapping, compressImgConfig, unZipPath)
                        apFile.delete()
                        ZipOutputStream(apFile.outputStream()).use { output ->
                            output.zip(unZipPath, File(unZipPath))
                        }

                        val lastLength = apFile.length()
                        print("优化结束缩减：${lastLength - originalLength}")
                        deleteDir(File(unZipPath))
                    }
                }
            }
        }
    }
}

private fun deleteRepeatRes(
    unZipPath: String,
    mappingFile: File,
    apFile: File,
    ignoreName: MutableList<String>?
) {

    val fileWriter = FileWriter(mappingFile)
    val groupsResources = ZipFile(apFile).groupsResources()

    val arscFile = File(unZipPath, RESOURCE_NAME)
    val newResource = FileInputStream(arscFile).use { input ->
        val fromInputStream = ResourceFile.fromInputStream(input)
        groupsResources.asSequence().filter {
            it.value.size > 1
        }.filter { entry ->
            val name = File(entry.value[0].name).name
            ignoreName?.contains(name)?.let {
                !it
            } ?: true
        }.forEach { zipMap ->
            val zips = zipMap.value

            val coreResources = zips[0]

            for (index in 1 until zips.size) {

                val repeatZipFile = zips[index]
                fileWriter.synchronizedWriteString("${repeatZipFile.name} => ${coreResources.name}")

                File(unZipPath, repeatZipFile.name).delete()

                fromInputStream
                    .chunks
                    .asSequence()
                    .filter {
                        it is ResourceTableChunk
                    }
                    .map {
                        it as ResourceTableChunk
                    }
                    .forEach { chunk ->
                        val stringPoolChunk = chunk.stringPool
                        val index = stringPoolChunk.indexOf(repeatZipFile.name)
                        if (index != -1) {
                            stringPoolChunk.setString(index, coreResources.name)
                        }
                    }
            }

        }


        fileWriter.close()
        fromInputStream
    }

    arscFile.delete()

    FileOutputStream(arscFile).use {
        it.write(newResource.toByteArray())
    }

}

private suspend fun CoroutineScope.compressionImg(
    mappingFile: File,
    unZipDir: String,
    config: CompressImgConfig,
    webpsLsit: CopyOnWriteArrayList<WebpFileData>
) {
    val mappginWriter = FileWriter(mappingFile)
    launch {

        val file = File("$unZipDir${File.separator}res")
        file.listFiles()
            .filter {
                it.isDirectory && (it.name.startsWith("drawable") || it.name.startsWith("mipmap"))
            }
            .flatMap {
                it.listFiles().toList()
            }
            .asSequence()
            .filter {
                config.whiteListName?.contains(it.name)?.let { !it } ?: true
            }
            .filter {
                ImageUtil.isImage(it)
            }
            .forEach {
                // 图片压缩
                launch(Dispatchers.Default) {
                    when (config.optimizeType) {

                        OPTIMIZE_COMPRESS_PICTURE -> {
                            val originalPath =
                                it.absolutePath.replace("${unZipDir}${File.separator}", "")
                            val reduceSize = compressImg(it)
                            if (reduceSize > 0) {
                                mappginWriter.synchronizedWriteString("$originalPath => 减少[$reduceSize]")
                            } else {
                                mappginWriter.synchronizedWriteString("$originalPath => 压缩失败")
                            }
                        }

                        OPTIMIZE_WEBP_CONVERT -> {
                            val webp0K = ImageUtil.securityFormatWebp(it, config)

                            webp0K?.apply {
                                val originalPath = original.absolutePath.replace(
                                    "${unZipDir}${File.separator}",
                                    ""
                                )
                                val webpFilePath = webpFile.absolutePath.replace(
                                    "${unZipDir}${File.separator}",
                                    ""
                                )
                                mappginWriter.synchronizedWriteString("$originalPath => $webpFilePath => 减少[$reduceSize]")
                                webpsLsit.add(this)
                            }
                        }

                        else -> {
                            println("图片优化类型 optimizeType [${config.optimizeType}] 不存在,使用 ${OPTIMIZE_COMPRESS_PICTURE} 类型压缩图片!")
                        }
                    }
                }
            }


    }.join()
    mappginWriter.close()
}

