package com.tang.baseframe.base.ui.listener

import android.view.View

/**
 *@Author tangtao
 *@Description:
 *@Date:2023/12/9 11:43
 */
interface TitleBar {
    fun titleBar(): View

    fun title(): String

    fun leftAction()

    fun rightAction()
}