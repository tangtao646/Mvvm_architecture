package com.tang.baseframe.base.ext

import android.view.View

/**
 *@Author tangtao
 *@Description:
 *@Date:2023/12/9 14:54
 */
fun View.dp2px(dp: Int): Int {
    val scale = resources.displayMetrics.density
    return (dp * scale + 0.5f).toInt()
}
