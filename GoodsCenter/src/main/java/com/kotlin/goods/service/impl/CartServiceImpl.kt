package com.kotlin.goods.service.impl

import com.kotlin.base.ext.convert
import com.kotlin.goods.data.repository.CartRepository
import com.kotlin.goods.service.CartService
import rx.Observable
import javax.inject.Inject

class CartServiceImpl @Inject constructor() : CartService {
//    override fun getCartList(): Observable<MutableList<CartGoods>?> {
//
//    }

    @Inject
    lateinit var repository: CartRepository

    /*
        加入购物车
     */
    override fun addCart(
        goodsId: Int,
        goodsDesc: String,
        goodsIcon: String,
        goodsPrice: Long,
        goodsCount: Int,
        goodsSku: String
    ): Observable<Int> {
        return repository.addCart(
            goodsId, goodsDesc, goodsIcon, goodsPrice,
            goodsCount, goodsSku
        ).convert()
    }


}