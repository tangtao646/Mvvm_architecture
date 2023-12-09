package com.tang.base

import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.tang.base.databinding.ActivityTestBinding
import com.tang.baseframe.base.ui.BaseActivity
import com.tang.baseframe.base.ui.BaseTitleBarActivity
import com.tang.baseframe.base.vm.BaseViewModel

class TestActivity : BaseTitleBarActivity<ActivityTestBinding>() {

    private val vm: BaseViewModel by viewModels()

    override fun layoutId(): Int = R.layout.activity_test
    override fun loadingVms(): Array<out BaseViewModel> = arrayOf(vm)

    override fun title(): String = intent?.getStringExtra("title") ?: ""
    override fun initView() {
        super.initView()
        supportFragmentManager.beginTransaction().add(R.id.container, HomeFragment()).commitNow()
    }

    override fun initData() {

    }
}