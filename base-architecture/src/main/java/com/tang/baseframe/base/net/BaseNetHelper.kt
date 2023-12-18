package com.tang.baseframe.base.net

import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 *@Author tangtao
 *@Description:
 *@Date:2023/12/18 13:53
 */
abstract class BaseNetHelper {

    fun <T> createApiService(clazz: Class<T>, baseUrl: String = ""): T {
        val builder = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okhttpClient)
        return setRetrofitBuilder(builder).build().create(clazz)
    }

    abstract fun setRetrofitBuilder(builder: Retrofit.Builder): Retrofit.Builder

    abstract fun setOkhttpBuilder(builder: OkHttpClient.Builder): OkHttpClient.Builder

    private val okhttpClient: OkHttpClient
        get() = setOkhttpBuilder(OkHttpClient.Builder()).build()


}