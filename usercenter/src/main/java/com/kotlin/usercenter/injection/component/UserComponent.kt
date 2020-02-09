package com.kotlin.usercenter.injection.component

import com.kotlin.base.injection.component.ActivityComponent
import com.kotlin.base.injection.scope.PreComponentScope
import com.kotlin.usercenter.injection.module.UserModule
import com.kotlin.usercenter.ui.activity.ForgetPwdActivity
import com.kotlin.usercenter.ui.activity.LoginActivity
import com.kotlin.usercenter.ui.activity.RegisterActivity
import com.kotlin.usercenter.ui.activity.ResetPwdActivity
import dagger.Component

@PreComponentScope
@Component(dependencies = [ActivityComponent::class], modules = [UserModule::class])
interface UserComponent {
    fun inject(activity: RegisterActivity)
    fun inject(activity: LoginActivity)
    fun inject(activity: ForgetPwdActivity)
    fun inject(activity: ResetPwdActivity)
}