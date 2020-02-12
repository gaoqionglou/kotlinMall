package com.kotlin.goods.presenter

import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.goods.data.protocol.CartGoods
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
        cartsService.getCartList().execute(object : BaseSubscriber<MutableList<CartGoods>?>(mView) {
            override fun onNext(t: MutableList<CartGoods>?) {
                mView.onGetCartList(t)
            }
        }, lifecycleProvider)

    }

    fun deleteCart(list: List<Int>) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        cartsService.deleteCartList(list).execute(object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {
                mView.onDeleteCartResult(t)
            }
        }, lifecycleProvider)
    }


    fun submitCart(list: MutableList<CartGoods>, totalPrice: Long) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        cartsService.submitCart(list, totalPrice).execute(object : BaseSubscriber<String>(mView) {
            override fun onNext(t: String) {
                mView.onSubmitCartListResult(t)
            }
        }, lifecycleProvider)
    }


}