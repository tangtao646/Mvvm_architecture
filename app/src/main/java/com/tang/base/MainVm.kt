package com.tang.base

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.tang.baseframe.base.data.BaseResponse
import com.tang.baseframe.base.vm.BaseViewModel
import com.tang.baseframe.request
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

    private val eventChannel= Channel<Boolean>()
    val eventFlow get() = eventChannel.receiveAsFlow()

    fun mockRequest() {
        request(request = {
            delay(500)
            queryData()
        }, showLoading = true,
            loadingMsg = "客官别着急...",
            onSuccess = {
            Log.e("data","==$it")
            viewModelScope.launch {
                eventChannel.send(true)
            }
        })
    }

    private suspend fun queryData(): BaseResponse<List<String>> {
        return object : BaseResponse<List<String>>() {
            override fun responseCode(): Int {
                return 200
            }

            override fun responseMsg(): String {
                return "请求失败哦"
            }

            override fun success(): Boolean {
                return true
            }

            override fun responseData(): List<String> {
                return arrayListOf("哈哈哈", "大哥说")
            }

        }
    }
}