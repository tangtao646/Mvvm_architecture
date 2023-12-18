package com.tang.base.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.tang.base.api.NetHelper
import com.tang.base.response.Banner
import com.tang.baseframe.base.ext.request
import com.tang.baseframe.base.vm.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

/**
 *@Author tangtao
 *@Description:
 *@Date:2023/12/18 14:32
 */
class WanViewModel : BaseViewModel() {
    val bannerFlow = MutableStateFlow<List<Banner>>(emptyList())

    fun queryBanner() {
        request(
            request = { NetHelper.apiService.queryArticles() },
            onSuccess = { list ->
                Log.e("banners", "==$list")
                viewModelScope.launch {
                    bannerFlow.emit(list)
                }
            }, showLoading = true
        )
    }
}