package com.tang.base.response

import com.tang.baseframe.base.data.BaseResponse

/**
 *@Author tangtao
 *@Description:
 *@Date:2023/12/11 9:27
 */
class ApiResponse<T>(val code: Int, val msg: String, val data: T) : BaseResponse<T>() {
    override fun responseCode(): Int {
        return code
    }

    override fun responseMsg(): String {
        return msg
    }

    override fun success(): Boolean {
        return code == 200
    }

    override fun responseData(): T {
        return data
    }
}