package com.tang.baseframe.base.ui.listener

/**
 *@Author tangtao
 *@Description:
 *@Date:2023/12/14 10:32
 */
interface ViewBehavior {
    fun initListener()
    fun initData()
    fun onReloadData()
    fun showSuccess()

    fun showErrorPage()

    fun showEmptyPage()

    fun showToast(msg: String)

    fun showLoading(tip: String = "加载中...")
    fun hideLoading()

}