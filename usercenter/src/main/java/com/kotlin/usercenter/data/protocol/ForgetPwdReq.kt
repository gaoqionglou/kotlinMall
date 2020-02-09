package com.kotlin.usercenter.data.protocol

data class ForgetPwdReq(val mobile: String, val verifyCode: String) {
}