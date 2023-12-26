package com.tang.baseframe.base.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tang.baseframe.base.state.UILoadState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 *@Author tangtao
 *@Description:
 *@Date:2023/12/8 14:00
 */
open class BaseViewModel : ViewModel() {
    private val loadingChannel = Channel<UILoadState>()
    val loadingFlow get() = loadingChannel.receiveAsFlow()


    fun setState(state: UILoadState) {
        viewModelScope.launch {
            loadingChannel.send(state)
        }
    }

}