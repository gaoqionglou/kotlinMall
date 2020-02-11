package com.kotlin.goods.service

import com.kotlin.goods.data.protocol.Category
import rx.Observable

interface CategoryService {
    fun getTopCategory(parentId: Int): Observable<MutableList<Category>?>
    fun getSecondaryCategory(parentId: Int): Observable<MutableList<Category>?>
}