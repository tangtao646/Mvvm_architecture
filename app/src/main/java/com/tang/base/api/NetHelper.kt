package com.tang.base.api

import com.tang.baseframe.base.net.BaseNetHelper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 *@Author tangtao
 *@Description:
 *@Date:2023/12/18 14:15
 */
object NetHelper : BaseNetHelper() {

    const val BaseUrl = "https://www.wanandroid.com/"

    val apiService = createApiService(ApiService::class.java, BaseUrl)

    override fun setRetrofitBuilder(builder: Retrofit.Builder): Retrofit.Builder {
        return builder.addConverterFactory(GsonConverterFactory.create())
    }

    override fun setOkhttpBuilder(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        return builder
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
    }


}