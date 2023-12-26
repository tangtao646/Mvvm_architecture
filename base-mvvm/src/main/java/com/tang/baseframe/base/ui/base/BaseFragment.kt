package com.tang.baseframe.base.ui.base

import android.app.Dialog
import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.tang.baseframe.base.ext.showEmpty
import com.tang.baseframe.base.ext.showError
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
    protected lateinit var loadingDialog: Dialog
    protected lateinit var loadService: LoadService<*>

    //是否第一次加载
    private var isFirst: Boolean = true
    private val handler = Handler()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId(), container, false)
        initLoadService()
        return loadService.loadLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isFirst = true
        initView(savedInstanceState)
        viewModelLoadingCollect()
        initData()
        initListener()
        createObserver()
    }

    abstract fun layoutId(): Int
    abstract fun loadingVms(): Array<out BaseViewModel>
    abstract fun lazyLoadData()

    @CallSuper
    protected open fun initView(savedInstanceState: Bundle?) {
        showSuccess()
        initLoadDialog()
    }


    override fun onResume() {
        super.onResume()
        onVisible()
    }

    /**
     * 是否需要懒加载
     */
    private fun onVisible() {
        if (lifecycle.currentState == Lifecycle.State.STARTED && isFirst) {
            // 延迟加载 防止 切换动画还没执行完毕时数据就已经加载好了，这时页面会有渲染卡顿
            handler.postDelayed({
                lazyLoadData()
            }, lazyLoadTime())
        }
    }

    private fun initLoadService() {
        loadService = LoadSir.getDefault().register(binding.root) {
            showSuccess()
            onReloadData()
        }
    }


    protected open fun initLoadDialog() {
        loadingDialog = ProgressDialog(requireActivity())
    }

    private fun viewModelLoadingCollect() {
        loadingVms().forEach { vm ->
            lifecycleScope.launch {
                lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                    vm.loadingFlow.collect {
                        when (it) {
                            is UILoadState.ShowLoading -> {
                                showLoading(it.tips)
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

    protected open fun createObserver() {

    }


    /**
     * 延迟加载 防止 切换动画还没执行完毕时数据就已经加载好了，这时页面会有渲染卡顿  bug
     * 这里传入你想要延迟的时间，延迟时间可以设置比转场动画时间长一点 单位： 毫秒
     * 不传默认 300毫秒
     * @return Long
     */
    open fun lazyLoadTime(): Long {
        return 300
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
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading(tip: String) {
        loadingDialog.show()
    }

    override fun hideLoading() {
        loadingDialog.dismiss()
    }


}