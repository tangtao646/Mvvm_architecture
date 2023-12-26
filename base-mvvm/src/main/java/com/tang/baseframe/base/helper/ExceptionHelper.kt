package com.tang.baseframe.base.helper

import android.net.ParseException
import com.tang.base_mvvm.BuildConfig
import com.tang.baseframe.base.state.UILoadState
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 *@Author tangtao
 *@Description:
 *@Date:2023/12/8 14:51
 */

object ExceptionHelper {

    var listener: WatchTokenListener? = null

    fun handleException(error: Throwable, showErrorPage: Boolean = false): UILoadState.Error {

        return when (error) {
            is HttpException -> handleBusinessExp(error)
            is ConnectException -> UILoadState.Error("网络连接异常",showErrorPage)
            is ParseException -> UILoadState.Error("数据解析异常",showErrorPage)
            is SocketTimeoutException -> UILoadState.Error("连接超时,请重试",showErrorPage)
            is UnknownHostException -> UILoadState.Error("连接超市，请重试",showErrorPage)
            else -> UILoadState.Error("请求失败，稍后重试",showErrorPage)
        }


    }

    /**
     * 从http异常信息获取业务异常
     */
    private fun handleBusinessExp(
        httpExp: HttpException,
    ): UILoadState.Error {
        return if (httpExp.code() == 401) {
            listener?.onTokenExpired()
            UILoadState.Error("token过期，请重新登录！")

        } else {
            val errorBody = httpExp.response()?.errorBody()?.string()
            val errorMsg = try {
                val bodyJson = JSONObject(errorBody)
                val msg = (bodyJson.opt("msg") ?: "") as String
                val traceId = (bodyJson.opt("traceId") ?: "") as String

                fun traceIdTips() = if (BuildConfig.DEBUG) "traceId:${traceId}" else traceId

                if (BuildConfig.DEBUG) "${traceIdTips()}\n报错信息：${msg}" else
                    "${traceIdTips()}\n${msg}"

            } catch (e: JSONException) {
                e.printStackTrace()
                if (BuildConfig.DEBUG) "报错信息：${httpExp.message ?: ""}" else httpExp.message
                    ?: ""
            }

            UILoadState.Error(errorMsg)
        }

    }


    interface WatchTokenListener {
        fun onTokenExpired()
    }
}
