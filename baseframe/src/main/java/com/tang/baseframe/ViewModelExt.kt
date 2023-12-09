package com.tang.baseframe

import androidx.lifecycle.viewModelScope
import com.tang.baseframe.base.data.BaseResponse
import com.tang.baseframe.base.vm.BaseViewModel
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
               setState(BaseViewModel.UILoadingToastState.ToastError(it.responseMsg()))
            }

        }.onFailure {
            setState(BaseViewModel.UILoadingToastState.HideLoading)
            val toastError = handleException(it)
            setState(toastError)
        }
    }
}