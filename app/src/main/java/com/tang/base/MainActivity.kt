package com.tang.base

import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.tang.base.databinding.ActivityMainBinding
import com.tang.baseframe.base.ui.BaseActivity
import com.tang.baseframe.base.vm.BaseViewModel
import kotlinx.coroutines.launch

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val mainVm: MainVm by viewModels()

    override fun layoutId(): Int = R.layout.activity_main

    override fun loadingVms(): Array<out BaseViewModel> = arrayOf(mainVm)


    override fun initData() {
        binding.tvSkip.setOnClickListener {
            mainVm.mockRequest()
        }
        collectEvent()
    }

    private fun collectEvent() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainVm.eventFlow.collect {
                    if (it) startActivity(Intent(this@MainActivity, TestActivity::class.java))
                }
            }
        }
    }


}