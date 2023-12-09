package com.tang.base

import androidx.fragment.app.viewModels
import com.tang.base.databinding.FragmentHomeBinding
import com.tang.baseframe.base.ui.BaseFragment
import com.tang.baseframe.base.vm.BaseViewModel

/**
 *@Author tangtao
 *@Description:
 *@Date:2023/12/9 10:03
 */
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val mainVm: MainVm by viewModels()

    override fun layoutId(): Int = R.layout.fragment_home

    override fun loadingVms(): Array<out BaseViewModel> = arrayOf(mainVm)


    override fun initData() {
        binding.tvFragment.setOnClickListener {
            mainVm.mockRequest()
        }
    }


}