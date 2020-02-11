package com.kotlin.goods.injection.module

import com.kotlin.goods.service.GoodsService
import com.kotlin.goods.service.impl.GoodsServiceImpl
import dagger.Module
import dagger.Provides


@Module
class GoodsModule {


    @Provides
    fun provideGoodsService(service: GoodsServiceImpl): GoodsService {
        return service
    }

}