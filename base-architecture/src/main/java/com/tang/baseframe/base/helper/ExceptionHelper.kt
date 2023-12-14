package com.tang.baseframe.base.helper

import android.net.ParseException
import com.tang.baseframe.base.state.UILoadState
import java.net.ConnectException
import java.net.SocketTimeoutException

/**
 *@Author tangtao
 *@Description:
 *@Date:2023/12/8 14:51
 */

fun handleException(error: Throwable): UILoadState.Error {
    val errorMsg = when (error) {
        is ConnectException -> "拒绝连接异常"
        is ParseException -> "数据解析异常"
        is SocketTimeoutException -> "连接超时,请重试"
        else -> "未知错误"
    }
    return UILoadState.Error(errorMsg)

}
