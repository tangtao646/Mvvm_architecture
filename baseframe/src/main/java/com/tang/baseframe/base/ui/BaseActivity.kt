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
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.tang.baseframe.base.callback.EmptyCallBack
import com.tang.baseframe.base.callback.ErrorCallBack
import com.tang.baseframe.base.ext.showEmpty
import com.tang.baseframe.base.ext.showError
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
    protected lateinit var loadService: LoadService<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(layoutInflater, layoutId(), null, false)
        setRealContent()
        viewModelLoadingCollect()
        initView()
        initData()
        initListener()
    }

    protected open fun setRealContent() {
        setContentView(binding.root)
    }


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


    abstract fun layoutId(): Int

    protected open fun loadingVms(): Array<out BaseViewModel> = emptyArray()


    protected fun initLoadDialog() {
        loadingDialog = ProgressDialog(this)
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

                            is BaseViewModel.UILoadingToastState.EmptyList -> {
                                showEmptyPage()
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
        loadService.showError()
    }

    protected fun showEmptyPage() {
        loadService.showEmpty()
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