package com.kotlin.usercenter.data.protocol

data class LoginReq(val mobile: String, val pwd: String, val pushId: String) {
}