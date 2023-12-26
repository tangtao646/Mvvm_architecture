package com.tang.baseframe.base.ext

import androidx.lifecycle.viewModelScope
import com.tang.baseframe.base.data.BaseResponse
import com.tang.baseframe.base.helper.ExceptionHelper
import com.tang.baseframe.base.state.UILoadState
import com.tang.baseframe.base.vm.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 *@Author tangtao
 *@Description:
 *@Date:2023/12/8 14:49
 */
fun <T> BaseViewModel.request(
    block: suspend () -> BaseResponse<T>,
    success: (T) -> Unit,
    error: (String) -> Unit = {},
    isShowDialog: Boolean = false,
    showErrorPage: Boolean = false,
    loadingMsg: String = "加载中...",
    apiName: String = ""
) {
    viewModelScope.launch {
        runCatching {
            if (isShowDialog) setState(UILoadState.ShowLoading(loadingMsg))
            delay(300)
            block()
        }.onSuccess {
            setState(UILoadState.HideLoading)
            if (it.success()) {
                success(it.responseData())
            } else {
                val errorMsg = it.traceId() + "\n" + it.responseMsg()
                setState(UILoadState.Error(errorMsg, showErrorPage))
                error(errorMsg)
            }

        }.onFailure {
            setState(UILoadState.HideLoading)
            val errorState = ExceptionHelper.handleException(it, showErrorPage)
            setState(errorState)
            error(errorState.msg)
        }
    }

}


fun <T> BaseViewModel.requestNoCheck(
    block: suspend () -> T,
    success: (T) -> Unit,
    error: (String) -> Unit = {},
    isShowDialog: Boolean = false,
    loadingMsg: String = "加载中...",
    apiName: String = ""
) {
    viewModelScope.launch {
        runCatching {
            if (isShowDialog) setState(UILoadState.ShowLoading(loadingMsg))
            delay(300)
            block()
        }.onSuccess {
            setState(UILoadState.HideLoading)
            success(it)

        }.onFailure {
            setState(UILoadState.HideLoading)
            val errorState = ExceptionHelper.handleException(it)
            setState(errorState)
            error(errorState.msg)
        }
    }

}


