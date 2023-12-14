package com.tang.baseframe.base.state

/**
 *@Author tangtao
 *@Description:
 *@Date:2023/12/14 10:18
 */
sealed class UILoadState {
    data class ShowLoading(val tips: String = "加载中...") : UILoadState()
    data object HideLoading : UILoadState()
    data class Error(val msg: String, val showErrorPage: Boolean = false) :
        UILoadState()

    data object EmptyList:UILoadState()
}
