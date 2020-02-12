package com.kotlin.goods.service.impl

import com.kotlin.base.ext.convert
import com.kotlin.base.ext.convertBoolean
import com.kotlin.goods.data.protocol.CartGoods
import com.kotlin.goods.data.repository.CartRepository
import com.kotlin.goods.service.CartService
import rx.Observable
import javax.inject.Inject

class CartServiceImpl @Inject constructor() : CartService {


    @Inject
    lateinit var repository: CartRepository

    override fun deleteCartList(list: List<Int>): Observable<Boolean> {
        return repository.deleteCartList(list).convertBoolean()
    }

    override fun getCartList(): Observable<MutableList<CartGoods>?> {
        return repository.getCartList().convert()
    }

    /*
            提交购物车商品
         */
    override fun submitCart(list: MutableList<CartGoods>, totalPrice: Long): Observable<String> {
        return repository.submitCart(list, totalPrice).convert()
    }

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