package com.tang.base.api

import com.tang.base.response.ApiResponse
import com.tang.base.response.Banner
import retrofit2.http.GET

/**
 *@Author tangtao
 *@Description:
 *@Date:2023/12/18 14:24
 */
interface ApiService {
    @GET("banner/json")
    suspend fun queryArticles(): ApiResponse<List<Banner>>
}