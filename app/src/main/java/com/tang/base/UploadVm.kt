package com.tang.base

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.tang.base.response.ApiResponse
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
class UploadVm : BaseViewModel() {

    private val eventChannel = Channel<Boolean>()
    val eventFlow get() = eventChannel.receiveAsFlow()

    fun upload(success: Boolean = true) {
        request(request = {
            delay(500)
            queryData(success)
        }, showLoading = true,
            loadingMsg = "上传文件中...",
            showErrorPage = true,
            onSuccess = {
                Log.e("data", "==$it")
                viewModelScope.launch {
                    eventChannel.send(true)
                }
            })
    }

    private suspend fun queryData(success: Boolean): ApiResponse<List<String>> {
        return if (success) ApiResponse(200, "请求成功", arrayListOf("哈哈哈", "大哥说")) else
            ApiResponse(200, "请求成功", emptyList())

    }
}