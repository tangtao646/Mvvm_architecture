package com.tang.baseframe.base.ext

import androidx.lifecycle.viewModelScope
import com.tang.baseframe.base.data.BaseResponse
import com.tang.baseframe.base.state.UILoadState
import com.tang.baseframe.base.vm.BaseViewModel
import com.tang.baseframe.base.helper.handleException
import kotlinx.coroutines.delay
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
            if (showLoading) setState(UILoadState.ShowLoading(loadingMsg))
            delay(300)
            request()
        }.onSuccess {
            setState(UILoadState.HideLoading)
            if (it.success()) {
                onSuccess(it.responseData())
            } else {
                setState(UILoadState.Error(it.responseMsg(), showErrorPage))
            }

        }.onFailure {
            setState(UILoadState.HideLoading)
            val toastError = handleException(it)
            setState(toastError)
        }
    }

}

private fun <T> responseListEmpty(it: BaseResponse<T>) =
    it.responseData() is List<*> && it.responseData() != null && (it.responseData() as List<*>).isEmpty()