package com.tang.base

import android.graphics.Color
import androidx.fragment.app.viewModels
import com.tang.base.databinding.FragmentHomeBinding
import com.tang.base.databinding.FragmentMeBinding
import com.tang.baseframe.base.helper.changeStatusBar
import com.tang.baseframe.base.helper.immerse
import com.tang.baseframe.base.ui.BaseFragment
import com.tang.baseframe.base.vm.BaseViewModel

/**
 *@Author tangtao
 *@Description:
 *@Date:2023/12/9 10:03
 */
class MeFragment : BaseFragment<FragmentMeBinding>() {

    private val mainVm: MainVm by viewModels()
    private val uploadVm: UploadVm by viewModels()

    override fun layoutId(): Int = R.layout.fragment_me

    override fun loadingVms(): Array<out BaseViewModel> = arrayOf(mainVm, uploadVm)

    override fun initView() {
        super.initView()
        requireActivity().changeStatusBar(Color.TRANSPARENT, false)
        requireActivity().immerse(false, binding.tvTitle)

    }

    override fun initData() {
//        binding.tvFragment.setOnClickListener {
//           uploadVm.upload(false)
//        }
    }


    override fun onReloadData() {
        uploadVm.upload(true)
    }

}