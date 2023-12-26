package com.tang.baseframe.base.ext

import android.view.View
import com.kingja.loadsir.core.LoadService
import com.tang.baseframe.base.ui.load_callback.EmptyCallBack
import com.tang.baseframe.base.ui.load_callback.ErrorCallBack

/**
 *@Author tangtao
 *@Description:
 *@Date:2023/12/9 14:54
 */
fun View.dp2px(dp: Int): Int {
    val scale = resources.displayMetrics.density
    return (dp * scale + 0.5f).toInt()
}


fun LoadService<*>.showError() {
    showCallback(ErrorCallBack::class.java)
}

fun LoadService<*>.showEmpty() {
    showCallback(EmptyCallBack::class.java)
}

