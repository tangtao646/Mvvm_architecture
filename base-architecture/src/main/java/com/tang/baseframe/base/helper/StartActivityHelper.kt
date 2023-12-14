package com.tang.baseframe.base.helper

import android.app.Activity
import android.content.Context
import android.content.Intent

/**
 *@Author tangtao
 *@Description:
 *@Date:2023/12/14 10:59
 */

fun Context.startPage(
    targetClazz: Class<out Activity>,
    option: (Intent.() -> Unit)? = null
) {
    Intent(this, targetClazz).apply { option?.invoke(this) }.also {
        startActivity(it)
    }

}