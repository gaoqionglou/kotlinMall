package com.kotlin.goods.presenter.view

import com.kotlin.base.presenter.view.BaseView
import com.kotlin.goods.data.protocol.Goods

interface GoodsView : BaseView {

    fun onGetGoodsListResult(result: MutableList<Goods>?)

}