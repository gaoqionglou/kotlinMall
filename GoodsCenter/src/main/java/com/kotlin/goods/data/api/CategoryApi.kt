package com.kotlin.goods.data.api

import com.kotlin.base.data.protocol.BaseResp
import com.kotlin.goods.data.protocol.Category
import com.kotlin.goods.data.protocol.GetCategoryReq
import retrofit2.http.Body
import retrofit2.http.POST
import rx.Observable


interface CategoryApi {

    /*
        获取商品分类列表
     */
    @POST("category/getTopCategory")
    fun getTopCategory(@Body req: GetCategoryReq): Observable<BaseResp<MutableList<Category>?>>

    /*
    获取商品分类列表
 */
    @POST("category/getSecondaryCategory")
    fun getSecondaryCategory(@Body req: GetCategoryReq): Observable<BaseResp<MutableList<Category>?>>
}