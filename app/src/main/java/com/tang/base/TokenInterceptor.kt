package com.tang.base

import okhttp3.Interceptor
import okhttp3.Response

/**
 *@Author tangtao
 *@Description:
 *@Date:2023/12/18 14:19
 */
class TokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return Response.Builder().build()
    }
}