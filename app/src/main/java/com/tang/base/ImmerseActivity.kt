package com.tang.base

import com.tang.base.databinding.ActivityImmerseBinding
import com.tang.baseframe.base.ui.BaseActivity

/**
 *@Author tangtao
 *@Description:
 *@Date:2023/12/15 16:39
 */
class ImmerseActivity : BaseActivity<ActivityImmerseBinding>() {


    override fun layoutId(): Int {
        return R.layout.activity_immerse
    }

    override fun initView() {
        super.initView()
        supportFragmentManager.beginTransaction().replace(R.id.container, HomeFragment())
            .commitNow()
    }

    override fun initListener() {
        super.initListener()

        binding.tvHome.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.container, HomeFragment())
                .commitNow()
        }

        binding.tvMe.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.container, MeFragment())
                .commitNow()
        }
    }
}