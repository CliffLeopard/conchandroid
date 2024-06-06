package com.cliff.conch.box.service

object ServerConst {
    const val SEVER_PROXY_AUTHORITY = "com.cliff.conch.box.system_server_proxy"
    const val BUNDLE_KEY = "ss_bk"
    const val BUNDLE_BACK_KEY = "ss_bbk"
    const val NAME_ATMS = "activity_task"
    const val NAME_PMS = ""

    // 其它进程获取系统服务
    const val GET_SERVICE = "ss_gs"
    // 其它进程注册系统服务，基本用不到
    const val REGISTER_SERVICE = "ss_rs"
    // 其它进程删除系统服务，基本用不到
    const val REMOVE_SERVICE = "ss_rms"
    // 其它进程清除系统服务，基本用不到
    const val CLEAN_SERVICE = "ss_cls"
}