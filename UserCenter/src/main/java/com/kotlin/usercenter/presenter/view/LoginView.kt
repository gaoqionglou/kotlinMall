package com.kotlin.usercenter.presenter.view

import com.kotlin.base.presenter.view.BaseView
import com.kotlin.usercenter.data.protocol.UserInfo

interface LoginView : BaseView {
    fun onLoginResult(result: UserInfo)
}