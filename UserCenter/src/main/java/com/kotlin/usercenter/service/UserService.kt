package com.kotlin.usercenter.service

import com.kotlin.usercenter.data.protocol.UserInfo
import rx.Observable

interface UserService {
    fun register(mobile: String, verifyCode: String, pwd: String): Observable<Boolean>

    fun login(mobile: String, pwd: String, pushId: String): Observable<UserInfo>

    fun forgetPwd(mobile: String, verifyCode: String): Observable<Boolean>

    fun resetPwd(mobile: String, pwd: String): Observable<Boolean>

    fun editUser(
        userIcon: String,
        userName: String,
        userGender: String,
        userSign: String
    ): Observable<UserInfo>
}