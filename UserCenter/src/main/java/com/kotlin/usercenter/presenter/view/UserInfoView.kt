package com.kotlin.usercenter.presenter.view

import com.kotlin.base.presenter.view.BaseView
import com.kotlin.usercenter.data.protocol.UserInfo

interface UserInfoView : BaseView {

    fun onGetUploadToken(result: String)
    fun onEditUserResult(result: UserInfo)
}