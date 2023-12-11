package com.tang.baseframe.base.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 *@Author tangtao
 *@Description:
 *@Date:2023/12/8 14:00
 */
open class BaseViewModel : ViewModel() {
    private val loadingChannel = Channel<UILoadingToastState>()
    val loadingFlow get() = loadingChannel.receiveAsFlow()

    sealed class UILoadingToastState {
        data class ShowLoading(val tips: String = "加载中...") : UILoadingToastState()
        data object HideLoading : UILoadingToastState()
        data class Error(val msg: String, val showErrorPage: Boolean = false) :
            UILoadingToastState()

        data object EmptyList:UILoadingToastState()
    }

    fun setState(state: UILoadingToastState) {
        viewModelScope.launch {
            loadingChannel.send(state)
        }
    }
}