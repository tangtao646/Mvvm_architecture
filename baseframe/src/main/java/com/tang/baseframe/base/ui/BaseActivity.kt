package com.tang.baseframe.base.ui

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.tang.baseframe.base.vm.BaseViewModel
import kotlinx.coroutines.launch

/**
 *@Author tangtao
 *@Description:
 *@Date:2023/12/8 16:02
 */
abstract class BaseActivity<VB : ViewDataBinding> : AppCompatActivity() {

    protected lateinit var binding: VB
    private lateinit var loadingDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(layoutInflater, layoutId(), null, false)
        setContentView(binding.root)
        viewModelLoadingCollect()
        initView()
        initData()
        initListener()
    }

    abstract fun layoutId(): Int

    abstract fun loadingVms(): Array<out BaseViewModel>

    protected open fun initView() {
        loadingDialog = ProgressDialog(this)
    }


    protected open fun initListener() {

    }

    protected open fun initData() {

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

                            is BaseViewModel.UILoadingToastState.ToastError -> {
                                showToast(it.error)
                            }

                            else -> {}
                        }
                    }
                }
            }
        }
    }

    protected fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    protected fun showLoading(tip: String = "加载中...") {
        loadingDialog.show()
    }

    protected fun hideLoading() {
        loadingDialog.dismiss()
    }
}