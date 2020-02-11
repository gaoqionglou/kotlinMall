package com.kotlin.goods.presenter

import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.goods.data.protocol.Goods
import com.kotlin.goods.presenter.view.GoodsView
import com.kotlin.goods.service.GoodsService
import javax.inject.Inject

class GoodsPresenter @Inject constructor() : BasePresenter<GoodsView>() {

    @Inject
    lateinit var goodsService: GoodsService

    /*
     获取商品列表
  */
    fun getGoodsList(categoryId: Int, pageNo: Int) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        goodsService.getGoodsList(categoryId, pageNo)
            .execute(object : BaseSubscriber<MutableList<Goods>?>(mView) {
                override fun onNext(t: MutableList<Goods>?) {
                    mView.onGetGoodsListResult(t)
                }
            }, lifecycleProvider)

    }

    /*
        根据关键字查询商品
     */
    fun getGoodsListByKeyword(keyword: String, pageNo: Int) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        goodsService.getGoodsListByKeyword(keyword, pageNo)
            .execute(object : BaseSubscriber<MutableList<Goods>?>(mView) {
                override fun onNext(t: MutableList<Goods>?) {
                    mView.onGetGoodsListResult(t)
                }
            }, lifecycleProvider)
    }


}


