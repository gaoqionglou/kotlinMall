package com.kotlin.goods.injection.component

import com.kotlin.base.injection.component.ActivityComponent
import com.kotlin.base.injection.scope.PerComponentScope
import com.kotlin.goods.injection.module.CartModule
import com.kotlin.goods.ui.fragment.CartFragment
import dagger.Component

@PerComponentScope
@Component(dependencies = [ActivityComponent::class], modules = [CartModule::class])
interface CartComponent {
    fun inject(fragment: CartFragment)
}