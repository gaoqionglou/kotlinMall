package com.kotlin.order.presenter.view

import com.kotlin.base.presenter.view.BaseView
import com.kotlin.order.data.protocol.ShipAddress

interface ShipAddressView : BaseView {
    fun onGetShipAddressListResult(result: MutableList<ShipAddress>?)

    fun onEditAddressResult(result: Boolean)

    fun onDeleteAddressResult(addressID: Int, result: Boolean)
    fun onSetDefaultResult(result: ShipAddress, t: Boolean) {

    }
}