package com.kotlin.messege.injection.component


import com.kotlin.base.injection.component.ActivityComponent
import com.kotlin.base.injection.scope.PerComponentScope

import com.kotlin.messege.injection.module.MessageModule
import com.kotlin.messege.ui.fragment.MessageFragment
import dagger.Component

/*
    消息模块注入组件
 */
@PerComponentScope
@Component(
    dependencies = arrayOf(ActivityComponent::class),
    modules = arrayOf(MessageModule::class)
)
interface MessageComponent {
    fun inject(fragment: MessageFragment)
}
