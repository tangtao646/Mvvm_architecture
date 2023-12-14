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
import com.tang.baseframe.base.ext.showEmpty
import com.tang.baseframe.base.ext.showError
import com.tang.baseframe.base.state.UILoadState
import com.tang.baseframe.base.ui.listener.ViewBehavior
import com.tang.baseframe.base.vm.BaseViewModel
import kotlinx.coroutines.launch

/**
 *@Author tangtao
 *@Description:
 *@Date:2023/12/8 16:02
 */
abstract class BaseActivity<VB : ViewDataBinding> : AppCompatActivity(), ViewBehavior {

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


    private fun viewModelLoadingCollect() {
        loadingVms().forEach { vm ->
            lifecycleScope.launch {
                lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                    vm.loadingFlow.collect {
                        when (it) {
                            is UILoadState.ShowLoading -> {
                                showLoading()
                            }

                            is UILoadState.HideLoading -> {
                                hideLoading()
                            }

                            is UILoadState.EmptyList -> {
                                showEmptyPage()
                            }

                            is UILoadState.Error -> {
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

    protected fun initLoadDialog() {
        loadingDialog = ProgressDialog(this)
    }

    override fun initListener() {

    }

    override fun initData() {

    }

    override fun onReloadData() {

    }


    override fun showSuccess() {
        loadService.showSuccess()
    }

    override fun showErrorPage() {
        loadService.showError()
    }

    override fun showEmptyPage() {
        loadService.showEmpty()
    }

    override fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading(tip: String) {
        if (::loadingDialog.isInitialized) loadingDialog.show()
    }

    override fun hideLoading() {
        if (::loadingDialog.isInitialized) loadingDialog.dismiss()
    }
}