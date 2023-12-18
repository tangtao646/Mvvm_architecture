package com.tang.base.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.tang.baseframe.base.data.BaseResponse
import com.tang.baseframe.base.vm.BaseViewModel
import com.tang.baseframe.base.ext.request
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 *@Author tangtao
 *@Description:
 *@Date:2023/12/8 10:55
 */
class MainVm : BaseViewModel() {

    private val eventChannel = Channel<Boolean>()
    val eventFlow get() = eventChannel.receiveAsFlow()

    fun mockRequest(success: Boolean = true) {
        request(request = {
            delay(500)
            queryData(success)
        }, showLoading = true,
            loadingMsg = "客官别着急...",
            showErrorPage = true,
            onSuccess = {
                Log.e("data","==$it")
                viewModelScope.launch {
                    eventChannel.send(true)
                }
            })
    }

    private suspend fun queryData(success: Boolean): BaseResponse<String> {
        return object : BaseResponse<String>() {
            override fun responseCode(): Int {
                return if (success) 200 else 400
            }

            override fun responseMsg(): String {
                return if (success) "请求成功" else "请求失败哦"
            }

            override fun success(): Boolean {
                return success
            }

            override fun responseData(): String {
                return "哈哈哈哈哈哈，我是测试数据"
            }

        }
    }
}