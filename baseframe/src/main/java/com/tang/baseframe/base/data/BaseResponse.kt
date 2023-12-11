package com.tang.baseframe.base.data

/**
 *@Author tangtao
 *@Description:
 *@Date:2023/12/5 15:50
 */
abstract class BaseResponse<T> {
    abstract fun responseCode(): Int
    abstract fun responseMsg(): String
    abstract fun success(): Boolean
    abstract fun responseData(): T
}

