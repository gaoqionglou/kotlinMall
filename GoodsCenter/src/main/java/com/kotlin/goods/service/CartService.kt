package com.kotlin.goods.service

import rx.Observable

interface CartService {
    //    fun getCartList(): Observable<MutableList<CartGoods>?>
    fun addCart(
        goodsId: Int,
        goodsDesc: String,
        goodsIcon: String,
        goodsPrice: Long,
        goodsCount: Int,
        goodsSku: String
    ): Observable<Int>

}