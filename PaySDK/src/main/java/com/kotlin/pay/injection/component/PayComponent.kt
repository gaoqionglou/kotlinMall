package com.kotlin.pay.injection.component

import com.kotlin.base.injection.component.ActivityComponent
import com.kotlin.base.injection.scope.PerComponentScope
import com.kotlin.pay.injection.module.PayModule
import com.kotlin.pay.ui.activity.PayActivity
import dagger.Component

@PerComponentScope
@Component(dependencies = [ActivityComponent::class], modules = [PayModule::class])
interface PayComponent {
    fun inject(activity: PayActivity)
}