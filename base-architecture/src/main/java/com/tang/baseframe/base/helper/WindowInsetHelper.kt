package com.tang.baseframe.base.helper

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.updateLayoutParams

/**
 *@Author tangtao
 *@Description: 9.0以下，延申状态栏没法适配间距，不好用
 *@Date:2023/12/15 14:58
 */


private fun Activity.insetsControllerCompat(): WindowInsetsControllerCompat {
    return WindowCompat.getInsetsController(window, window.decorView)
}


fun Activity.changeStatusBar(barColor: Int = Color.RED, darkText: Boolean = true) {
    window.statusBarColor = barColor
    insetsControllerCompat().apply {
        isAppearanceLightStatusBars = darkText
    }

}


fun Activity.immerse(fitSys: Boolean = true, marginView: View? = null) {
    WindowCompat.setDecorFitsSystemWindows(window, fitSys)
    ViewCompat.setOnApplyWindowInsetsListener(
        window.decorView
    ) { v, insets ->
        val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        marginView?.updateLayoutParams<MarginLayoutParams> {
            topMargin = statusBarInsets.top
        }
        insets
    }
}