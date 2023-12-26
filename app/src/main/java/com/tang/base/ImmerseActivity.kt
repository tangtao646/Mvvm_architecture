package com.tang.base

import android.os.Bundle
import com.tang.base.databinding.ActivityImmerseBinding
import com.tang.baseframe.base.ui.base.BaseActivity

/**
 *@Author tangtao
 *@Description:
 *@Date:2023/12/15 16:39
 */
class ImmerseActivity : BaseActivity<ActivityImmerseBinding>() {


    override fun layoutId(): Int {
        return R.layout.activity_immerse
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
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