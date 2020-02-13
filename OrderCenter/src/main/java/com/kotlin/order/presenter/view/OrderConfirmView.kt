package com.kotlin.order.presenter.view

import com.kotlin.base.presenter.view.BaseView
import com.kotlin.order.data.protocol.Order

interface OrderConfirmView : BaseView {

    fun onGetOrderByIdResult(result: Order)
    fun onSubmitOrderResult(result: Boolean)
}