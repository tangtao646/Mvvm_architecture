package com.tang.base

import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.tang.base.databinding.ActivityMainBinding
import com.tang.baseframe.base.helper.changeStatusBar
import com.tang.baseframe.base.helper.immerse
import com.tang.baseframe.base.helper.startPage
import com.tang.baseframe.base.ui.BaseActivity
import com.tang.baseframe.base.vm.BaseViewModel
import kotlinx.coroutines.launch

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val mainVm: MainVm by viewModels()

    override fun layoutId(): Int = R.layout.activity_main

    override fun loadingVms(): Array<out BaseViewModel> = arrayOf(mainVm)

    override fun initView() {
        super.initView()
        changeStatusBar()

    }

    override fun initData() {
        binding.tvSkip.setOnClickListener {
            mainVm.mockRequest(false)
        }
        collectEvent()
    }

    override fun onReloadData() {
        mainVm.mockRequest(true)
    }

    private fun collectEvent() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainVm.eventFlow.collect {
                    if (it) {
                        startPage(ImmerseActivity::class.java) {
                            putExtra("title", "我是测试标题")
                        }
                    }
                }
            }
        }
    }


}