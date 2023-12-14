package com.tang.base

import androidx.activity.viewModels
import com.tang.base.databinding.ActivitySampleTitleBinding
import com.tang.baseframe.base.helper.startPage
import com.tang.baseframe.base.ui.BaseTitleBarActivity
import com.tang.baseframe.base.vm.BaseViewModel

/**
 *@Author tangtao
 *@Description:
 *@Date:2023/12/9 15:18
 */
class SampleTitleActivity : BaseTitleBarActivity<ActivitySampleTitleBinding>() {

    private val testVm:UploadVm by viewModels()
    override fun layoutId(): Int = R.layout.activity_sample_title

    override fun title(): String = intent?.getStringExtra("title") ?: ""

    override fun loadingVms(): Array<out BaseViewModel> = arrayOf(testVm)


    override fun initListener() {
        super.initListener()
        binding.tvTest.setOnClickListener {
            testVm.upload(false)

        }
    }




}