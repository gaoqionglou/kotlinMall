package com.kotlin.usercenter.presenter

import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.usercenter.data.protocol.UserInfo
import com.kotlin.usercenter.presenter.view.UserInfoView
import com.kotlin.usercenter.service.UploadService
import com.kotlin.usercenter.service.UserService
import javax.inject.Inject

class UserInfoPresenter @Inject constructor() : BasePresenter<UserInfoView>() {

    @Inject
    lateinit var userService: UserService

    @Inject
    lateinit var uploadService: UploadService

    fun getUploadToken() {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        uploadService.getUploadToken().execute(object : BaseSubscriber<String>(mView) {
            override fun onNext(t: String) {
                super.onNext(t)
                mView.onGetUploadToken(t)
            }
        }, lifecycleProvider)
    }

    fun editUser(
        userIcon: String,
        userName: String,
        userGender: String,
        userSign: String
    ) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userService.editUser(userIcon, userName, userGender, userSign)
            .execute(object : BaseSubscriber<UserInfo>(mView) {
                override fun onNext(t: UserInfo) {
                    mView.onEditUserResult(t)
                }
            }, lifecycleProvider)

    }

}