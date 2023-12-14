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
import com.tang.baseframe.base.state.UILoadState
import com.tang.baseframe.base.ui.listener.ViewBehavior
import com.tang.baseframe.base.ui.load_callback.EmptyCallBack
import com.tang.baseframe.base.ui.load_callback.ErrorCallBack
import com.tang.baseframe.base.vm.BaseViewModel
import kotlinx.coroutines.launch

/**
 *@Author tangtao
 *@Description:
 *@Date:2023/12/9 9:53
 */
abstract class BaseFragment<VB : ViewDataBinding> : Fragment(), ViewBehavior {

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
        loadService.showCallback(ErrorCallBack::class.java)
    }

    override fun showEmptyPage() {
        loadService.showCallback(EmptyCallBack::class.java)
    }

    override fun showToast(msg: String) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading(tip: String) {
        loadingDialog.show()
    }

    override fun hideLoading() {
        loadingDialog.dismiss()
    }


}