package com.kotlin.goods.presenter

import com.kotlin.base.presenter.BasePresenter
import com.kotlin.goods.presenter.view.CartView
import com.kotlin.goods.service.CartService
import javax.inject.Inject

class CartPresenter @Inject constructor() : BasePresenter<CartView>() {

    @Inject
    lateinit var cartsService: CartService

    fun getCartList() {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
//        cartsService.getCartList().execute(object : BaseSubscriber<MutableList<CartGoods>?>(mView) {
//            override fun onNext(t: MutableList<CartGoods>?) {
//                mView.onGetCartList(t)
//            }
//        }, lifecycleProvider)

    }


}