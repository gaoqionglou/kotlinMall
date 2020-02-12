package com.kotlin.goods.presenter.view

import com.kotlin.base.presenter.view.BaseView
import com.kotlin.goods.data.protocol.CartGoods

interface CartView : BaseView {

    fun onGetCartList(result: MutableList<CartGoods>?)

}