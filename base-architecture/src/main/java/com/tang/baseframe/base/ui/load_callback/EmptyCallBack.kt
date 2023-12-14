package com.tang.baseframe.base.ui.load_callback

import com.kingja.loadsir.callback.Callback
import com.tang.commonres.R

/**
 *@Author tangtao
 *@Description:
 *@Date:2023/12/9 14:15
 */
class EmptyCallBack: Callback() {
    override fun onCreateView(): Int {
        return R.layout.layout_empty_callback
    }
}