package com.tang.baseframe.base

import android.app.Application
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.LoadSir
import com.tang.baseframe.base.ui.load_callback.EmptyCallBack
import com.tang.baseframe.base.ui.load_callback.ErrorCallBack

/**
 *@Author tangtao
 *@Description:
 *@Date:2023/12/9 14:14
 */
open class BaseApp : Application() {

    companion object {
        lateinit var appContext: Application
    }


    override fun onCreate() {
        super.onCreate()
        appContext = this
        initSdk()
    }

    protected open fun initSdk() {
        LoadSir.beginBuilder()
            .addCallback(EmptyCallBack())
            .addCallback(ErrorCallBack())
            .setDefaultCallback(SuccessCallback::class.java)
            .commit()
    }
}