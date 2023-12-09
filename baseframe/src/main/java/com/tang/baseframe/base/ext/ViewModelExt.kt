package com.tang.baseframe.base.ext

import androidx.lifecycle.viewModelScope
import com.tang.baseframe.base.data.BaseResponse
import com.tang.baseframe.base.vm.BaseViewModel
import com.tang.baseframe.handleException
import kotlinx.coroutines.launch

/**
 *@Author tangtao
 *@Description:
 *@Date:2023/12/8 14:49
 */
fun <T> BaseViewModel.request(
    request: suspend () -> BaseResponse<T>,
    onSuccess: (T) -> Unit,
    showLoading: Boolean = false,
    showErrorPage: Boolean = false,
    loadingMsg: String = "加载中..."
) {
    viewModelScope.launch {
        runCatching {
            if (showLoading) setState(BaseViewModel.UILoadingToastState.ShowLoading(loadingMsg))
            request()
        }.onSuccess {
            setState(BaseViewModel.UILoadingToastState.HideLoading)
            if (it.success()) {
                onSuccess(it.responseData())
            } else {
                setState(BaseViewModel.UILoadingToastState.Error(it.responseMsg(), showErrorPage))
            }

        }.onFailure {
            setState(BaseViewModel.UILoadingToastState.HideLoading)
            val toastError = handleException(it)
            setState(toastError)
        }
    }
}