package com.tang.baseframe.base.ext

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.getSystemService

/**
 *@Author tangtao
 *@Description:
 *@Date:2023/12/19 11:12
 */

val Context.layoutInflater get() = getSystemService<LayoutInflater>()

/**
 * 获取屏幕宽度
 */
val Context.screenWidth
    get() = resources.displayMetrics.widthPixels

/**
 * 获取屏幕高度
 */
val Context.screenHeight
    get() = resources.displayMetrics.heightPixels


/**
 * 获取状态栏高度
 */
fun Context.statusBarHeight(): Int {
    val resId = resources.getIdentifier("status_bar_height", "dimen", "android")
    return resources.getDimensionPixelSize(resId)
}


/**
 * dp值转换为px
 */
fun Context.dp2px(dp: Int): Int {
    val scale = resources.displayMetrics.density
    return (dp * scale + 0.5f).toInt()
}

/**
 * px值转换成dp
 */
fun Context.px2dp(px: Int): Int {
    val scale = resources.displayMetrics.density
    return (px / scale + 0.5f).toInt()
}


