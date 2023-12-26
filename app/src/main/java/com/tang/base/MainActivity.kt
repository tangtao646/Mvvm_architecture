package com.tang.base

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.tang.base.databinding.ActivityMainBinding
import com.tang.base.viewmodel.MainVm
import com.tang.base.viewmodel.WanViewModel
import com.tang.baseframe.base.helper.changeStatusBar
import com.tang.baseframe.base.helper.startPage
import com.tang.baseframe.base.ui.base.BaseActivity
import com.tang.baseframe.base.vm.BaseViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val mainVm: MainVm by viewModels()
    private val wanViewModel: WanViewModel by viewModels()

    override fun layoutId(): Int = R.layout.activity_main

    override fun loadingVms(): Array<out BaseViewModel> = arrayOf(mainVm, wanViewModel)

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        changeStatusBar()
    }

    override fun initData() {
        binding.tvSkip.setOnClickListener {
            // mainVm.mockRequest(false)
            wanViewModel.queryBanner()
        }
        collectEvent()
        collectData()
    }

    override fun onReloadData() {
//        mainVm.mockRequest(true)
        wanViewModel.queryBanner()
    }

    private fun collectData() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                wanViewModel.bannerFlow.collectLatest {
                    binding.bannerInfo.text = it.toString()
                }
            }
        }
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