package com.kotlin.goods.injection.component

import com.kotlin.base.injection.component.ActivityComponent
import com.kotlin.base.injection.scope.PerComponentScope
import com.kotlin.goods.injection.module.GoodsModule
import com.kotlin.goods.ui.activity.GoodsActivity
import dagger.Component

@PerComponentScope
@Component(dependencies = [ActivityComponent::class], modules = [GoodsModule::class])
interface GoodsComponent {

    fun inject(activity: GoodsActivity)
}