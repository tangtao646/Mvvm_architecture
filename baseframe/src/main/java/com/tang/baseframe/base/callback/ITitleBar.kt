package com.tang.baseframe.base.callback

import android.view.View

/**
 *@Author tangtao
 *@Description:
 *@Date:2023/12/9 11:43
 */
interface ITitleBar {
    fun titleBar(): View

    fun title(): String

    fun leftAction()

    fun rightAction()
}