package com.kotlin.goods.data.repository

import com.kotlin.base.data.net.RetrofitFactory
import com.kotlin.base.data.protocol.BaseResp
import com.kotlin.goods.data.api.CategoryApi
import com.kotlin.goods.data.protocol.Category
import com.kotlin.goods.data.protocol.GetCategoryReq
import rx.Observable
import javax.inject.Inject

class CategoryRepository @Inject constructor() {


    fun getTopCategory(parentId: Int): Observable<BaseResp<MutableList<Category>?>> {
        return RetrofitFactory.instance.create(CategoryApi::class.java)
            .getTopCategory(GetCategoryReq(parentId))
    }

    fun getSecondaryCategory(parentId: Int): Observable<BaseResp<MutableList<Category>?>> {
        return RetrofitFactory.instance.create(CategoryApi::class.java)
            .getSecondaryCategory(GetCategoryReq(parentId))
    }
}