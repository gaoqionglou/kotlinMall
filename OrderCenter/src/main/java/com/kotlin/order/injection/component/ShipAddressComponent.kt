package com.kotlin.order.injection.component

import com.kotlin.base.injection.component.ActivityComponent
import com.kotlin.base.injection.scope.PerComponentScope
import com.kotlin.order.injection.module.ShipAddressModule
import com.kotlin.order.ui.activity.ShipAddressActivity
import com.kotlin.order.ui.activity.ShipAddressEditActivity
import dagger.Component

@PerComponentScope
@Component(dependencies = [ActivityComponent::class], modules = [ShipAddressModule::class])
interface ShipAddressComponent {
    fun inject(activity: ShipAddressEditActivity)
    //    fun inject(fragment:OrderFragment)
//
    fun inject(activity: ShipAddressActivity)
}