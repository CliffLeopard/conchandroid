package com.cliff.conch.box.process

object ProcessScheduler {
    private const val PROCESS_PREFIX = "plugin_p"
    private val used: MutableMap<String, String> = mutableMapOf()

    // 获取PluginApp的进程名
    fun getProcess(packageName: String): String {
        if (!used.contains(packageName)) {
            used[packageName] = "$PROCESS_PREFIX${nextProcessIndex()}"
        }
        return used[packageName]!!
    }

    // 分发IdIndex
    private fun nextProcessIndex(): Int {
        var index = 1
        while (used.values.contains(PROCESS_PREFIX + index)) {
            index++
        }
        return index
    }

    // 进程结束
    private fun died(packageName: String) {
        used.remove(packageName)
    }

    data class ProcessInfo(val packageName: String, val processName: String, val processId: Int,val processProvider: ProcessProvider)
}