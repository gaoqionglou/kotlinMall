package com.kotlin.usercenter.data.protocol

data class RegisterReq(val mobile: String, val verifyCode: String, val pwd: String) {
}