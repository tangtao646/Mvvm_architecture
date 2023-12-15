package com.tang.baseframe.base.helper

import android.R
import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.updateLayoutParams

/**
 *@Author tangtao
 *@Description:
 *@Date:2023/12/15 14:58
 */

private fun Activity.windowRootView(): FrameLayout {
    return findViewById(R.id.content)
}

private fun Activity.insetsControllerCompat(): WindowInsetsControllerCompat {
    val windowRootView = windowRootView()
    return WindowCompat.getInsetsController(window, windowRootView)
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
        windowRootView()
    ) { v, insets ->
        val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars())
        marginView?.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            topMargin = statusBarInsets.top
        }
        insets
    }
}