package com.kotlin.goods.data.api

import com.kotlin.base.data.protocol.BaseResp
import com.kotlin.goods.data.protocol.GetGoodsDetailReq
import com.kotlin.goods.data.protocol.GetGoodsListByKeywordReq
import com.kotlin.goods.data.protocol.GetGoodsListReq
import com.kotlin.goods.data.protocol.Goods
import retrofit2.http.Body
import retrofit2.http.POST
import rx.Observable

/*
    商品接口
 */
interface GoodsApi {

    @POST("goods/getGoodsList")
    fun getGoodsList(@Body req: GetGoodsListReq): Observable<BaseResp<MutableList<Goods>?>>


    @POST("goods/getGoodsListByKeyword")
    fun getGoodsListByKeyword(@Body req: GetGoodsListByKeywordReq): Observable<BaseResp<MutableList<Goods>?>>


    @POST("goods/getGoodsDetail")
    fun getGoodsDetail(@Body req: GetGoodsDetailReq): Observable<BaseResp<Goods>>
}
