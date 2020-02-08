package com.kotlin.usercenter.service.impl

import com.kotlin.base.ext.convertBoolean
import com.kotlin.usercenter.data.repository.UserRepository
import com.kotlin.usercenter.service.UserService
import rx.Observable
import javax.inject.Inject

class UserServiceImpl @Inject constructor() : UserService {
    @Inject
    lateinit var repository: UserRepository

    override fun register(mobile: String, verifyCode: String, pwd: String): Observable<Boolean> {
//        val repository = UserRepository()
        return repository.register(mobile, verifyCode, pwd)
            .convertBoolean()
    }

}