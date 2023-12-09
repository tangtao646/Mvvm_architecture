package com.tang.baseframe.base.callback

import com.kingja.loadsir.callback.Callback
import com.tang.commonres.R

/**
 *@Author tangtao
 *@Description:
 *@Date:2023/12/9 14:15
 */
class ErrorCallBack: Callback() {
    override fun onCreateView(): Int {
        return R.layout.layout_error_callback
    }
}