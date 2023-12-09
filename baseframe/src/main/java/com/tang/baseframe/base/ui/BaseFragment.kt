package com.tang.baseframe.base.ui

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.tang.baseframe.base.callback.EmptyCallBack
import com.tang.baseframe.base.callback.ErrorCallBack
import com.tang.baseframe.base.vm.BaseViewModel
import kotlinx.coroutines.launch

/**
 *@Author tangtao
 *@Description:
 *@Date:2023/12/9 9:53
 */
abstract class BaseFragment<VB : ViewDataBinding> : Fragment() {

    protected lateinit var binding: VB
    private lateinit var loadingDialog: ProgressDialog
    protected lateinit var loadService: LoadService<*>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId(), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        viewModelLoadingCollect()
        initData()
        initListener()

    }

    abstract fun layoutId(): Int
    abstract fun loadingVms(): Array<out BaseViewModel>

    protected open fun initView() {
        initLoadService()
        showSuccess()
        initLoadDialog()
    }

    private fun initLoadService() {
        loadService = LoadSir.getDefault().register(binding.root) {
            showSuccess()
            onReloadData()
        }
    }


    protected fun initLoadDialog() {
        loadingDialog = ProgressDialog(requireActivity())
    }

    protected open fun initListener() {

    }

    protected open fun initData() {

    }

    protected open fun onReloadData() {

    }

    private fun viewModelLoadingCollect() {
        loadingVms().forEach { vm ->
            lifecycleScope.launch {
                lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                    vm.loadingFlow.collect {
                        when (it) {
                            is BaseViewModel.UILoadingToastState.ShowLoading -> {
                                showLoading()
                            }

                            is BaseViewModel.UILoadingToastState.HideLoading -> {
                                hideLoading()
                            }

                            is BaseViewModel.UILoadingToastState.Error -> {
                                showToast(it.msg)
                                if (it.showErrorPage) {
                                    showErrorPage()
                                }
                            }

                            else -> {}
                        }
                    }
                }
            }
        }
    }

    protected fun showSuccess() {
        loadService.showSuccess()
    }

    protected fun showErrorPage() {
        loadService.showCallback(ErrorCallBack::class.java)
    }

    protected fun showEmptyPage() {
        loadService.showCallback(EmptyCallBack::class.java)
    }

    protected fun showToast(msg: String) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
    }

    protected fun showLoading(tip: String = "加载中...") {
        loadingDialog.show()
    }

    protected fun hideLoading() {
        loadingDialog.dismiss()
    }


}