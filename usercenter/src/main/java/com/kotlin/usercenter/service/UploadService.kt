package com.kotlin.usercenter.service

import rx.Observable

interface UploadService {

    fun getUploadToken(): Observable<String>
}