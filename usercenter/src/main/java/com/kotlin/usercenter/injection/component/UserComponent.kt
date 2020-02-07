package com.kotlin.usercenter.injection.component

import com.kotlin.base.injection.component.ActivityComponent
import com.kotlin.base.injection.scope.PreComponentScope
import com.kotlin.usercenter.injection.module.UserModule
import com.kotlin.usercenter.ui.activity.RegisterActivity
import dagger.Component

@PreComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class), modules = arrayOf(UserModule::class))
interface UserComponent {
    fun inject(activity: RegisterActivity)
}