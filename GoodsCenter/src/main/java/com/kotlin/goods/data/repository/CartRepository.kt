package com.kotlin.goods.data.repository

import com.kotlin.base.data.net.RetrofitFactory
import com.kotlin.base.data.protocol.BaseResp
import com.kotlin.goods.data.api.CartApi
import com.kotlin.goods.data.protocol.AddCartReq
import com.kotlin.goods.data.protocol.CartGoods
import com.kotlin.goods.data.protocol.DeleteCartReq
import com.kotlin.goods.data.protocol.SubmitCartReq
import rx.Observable
import javax.inject.Inject

class CartRepository @Inject constructor() {

    /*
       获取购物车列表
    */

    fun getCartList(): Observable<BaseResp<MutableList<CartGoods>?>> {
        return RetrofitFactory.instance.create(CartApi::class.java)
            .getCartList()
    }

    /*
        添加商品到购物车
     */

    fun addCart(
        goodsId: Int, goodsDesc: String, goodsIcon: String, goodsPrice: Long,
        goodsCount: Int, goodsSku: String
    ): Observable<BaseResp<Int>> {
        return RetrofitFactory.instance.create(CartApi::class.java)
            .addCart(AddCartReq(goodsId, goodsDesc, goodsIcon, goodsPrice, goodsCount, goodsSku))
    }

    /*
        删除购物车商品
     */

    fun deleteCartList(list: List<Int>): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.create(CartApi::class.java)
            .deleteCartList(DeleteCartReq(list))
    }

    /*
        提交购物车商品
     */

    fun submitCart(list: MutableList<CartGoods>, totalPrice: Long): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.create(CartApi::class.java)
            .submitCart(SubmitCartReq(list, totalPrice))

    }
}