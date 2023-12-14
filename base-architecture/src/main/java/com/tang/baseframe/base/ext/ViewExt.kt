package com.tang.baseframe.base.ext

import android.view.View
import androidx.databinding.ObservableList
import com.kingja.loadsir.core.LoadService
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.tang.baseframe.base.ui.load_callback.EmptyCallBack
import com.tang.baseframe.base.ui.load_callback.ErrorCallBack
import com.tang.baseframe.base.state.ListDataUiState

/**
 *@Author tangtao
 *@Description:
 *@Date:2023/12/9 14:54
 */
fun View.dp2px(dp: Int): Int {
    val scale = resources.displayMetrics.density
    return (dp * scale + 0.5f).toInt()
}


fun LoadService<*>.showError() {
    showCallback(ErrorCallBack::class.java)
}

fun LoadService<*>.showEmpty() {
    showCallback(EmptyCallBack::class.java)
}

/**
 * 加载列表数据
 */
fun <T> loadListData(
    data: ListDataUiState<T>,
    observableList: ObservableList<T>,
    loadService: LoadService<*>,
    refreshLayout: SmartRefreshLayout
) {
    if (data.isSuccess) {
        //成功
        when {
            //第一页并没有数据 显示空布局界面
            data.isFirstEmpty -> {
                loadService.showEmpty()
            }
            //是第一页
            data.isRefresh -> {
                refreshLayout.finishRefresh()
                observableList.clear()
                observableList.addAll(data.listData)
                loadService.showSuccess()

            }
            //不是第一页
            else -> {
                if (!data.hasMore) {
                    refreshLayout.finishLoadMoreWithNoMoreData()
                } else {
                    observableList.addAll(data.listData)
                    refreshLayout.finishLoadMore()
                }
                loadService.showSuccess()
            }
        }
    } else {
        //失败
        if (data.isRefresh) {
            //如果是第一页，则显示错误界面，并提示错误信息
            refreshLayout.finishRefresh()
            loadService.showError()
        } else {
            //加载失败
            refreshLayout.finishLoadMore(500, false, true)
        }
    }
}