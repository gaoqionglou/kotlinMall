package com.kotlin.order.presenter.view

import com.kotlin.base.presenter.view.BaseView

interface EditShipAddressView : BaseView {
    fun onAddShipAddressResult(result: Boolean)

    fun onEditAddressResult(result: Boolean)
}