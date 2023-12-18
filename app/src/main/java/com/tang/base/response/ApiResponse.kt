package com.tang.base.response

import com.tang.baseframe.base.data.BaseResponse

/**
 *@Author tangtao
 *@Description:
 *@Date:2023/12/11 9:27
 */
class ApiResponse<T>(val errorCode: Int, val errorMsg: String, val data: T) : BaseResponse<T>() {
    override fun responseCode(): Int {
        return errorCode
    }

    override fun responseMsg(): String {
        return errorMsg
    }

    override fun success(): Boolean {
        return errorCode == 0
    }

    override fun responseData(): T {
        return data
    }
}