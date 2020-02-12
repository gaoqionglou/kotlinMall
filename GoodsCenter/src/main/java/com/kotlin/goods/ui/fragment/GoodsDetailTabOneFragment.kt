package com.kotlin.goods.ui.fragment

import android.app.Activity
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.Toast
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.activity.BaseActivity
import com.kotlin.base.ui.fragment.BaseMvpFragment
import com.kotlin.base.utils.YuanFenConverter
import com.kotlin.base.widget.BannerImageLoader
import com.kotlin.goods.R
import com.kotlin.goods.common.GoodsConstant
import com.kotlin.goods.data.protocol.Goods
import com.kotlin.goods.event.AddCartEvent
import com.kotlin.goods.event.SkuChangedEvent
import com.kotlin.goods.event.UpdateCartSizeEvent
import com.kotlin.goods.injection.component.DaggerGoodsComponent
import com.kotlin.goods.injection.module.GoodsModule
import com.kotlin.goods.presenter.GoodsDetailPresenter
import com.kotlin.goods.presenter.view.GoodsDetailView
import com.kotlin.goods.widget.GoodsSkuPopView
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.fragment_goods_detail_tab_one.*

class GoodsDetailTabOneFragment : BaseMvpFragment<GoodsDetailPresenter>(), GoodsDetailView {

    private lateinit var mSkuPop: GoodsSkuPopView
    //SKU弹层出场动画
    private lateinit var mAnimationStart: Animation
    //SKU弹层退场动画
    private lateinit var mAnimationEnd: Animation
    private var mCurGoods: Goods? = null

    override fun initComponent() {
        DaggerGoodsComponent.builder().activityComponent(mActivityComponent)
            .goodsModule(GoodsModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_goods_detail_tab_one, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initAnim()
        initPopupWindow()
        loadData()
        initObserve()

    }


    private fun initView() {
        mGoodsDetailBanner.setImageLoader(BannerImageLoader())
        mGoodsDetailBanner.setBannerAnimation(Transformer.Accordion)
        mGoodsDetailBanner.isAutoPlay(true)
        mGoodsDetailBanner.setDelayTime(2000)
        mGoodsDetailBanner.setBannerStyle(BannerConfig.NUM_INDICATOR)
        mGoodsDetailBanner.setIndicatorGravity(BannerConfig.RIGHT)


        mSkuView.onClick {
            mSkuPop.showAtLocation(
                (activity as BaseActivity).contentView,
                Gravity.BOTTOM and Gravity.CENTER_HORIZONTAL,
                0,
                0
            )

            (activity as BaseActivity).contentView.startAnimation(mAnimationStart)
        }
    }

    private fun initPopupWindow() {
        mSkuPop = GoodsSkuPopView(activity as Activity)
        mSkuPop.setOnDismissListener {
            (activity as BaseActivity).contentView.startAnimation(mAnimationEnd)
        }

    }

    private fun loadData() {
        mPresenter.getGoodsDetail(
            activity?.intent?.getIntExtra(GoodsConstant.KEY_GOODS_ID, -1) ?: -1
        )
    }

    override fun onGetGoodsDetailResult(result: Goods) {

        mCurGoods = result

        mGoodsDetailBanner.setImages(result.goodsBanner.split(","))
        mGoodsDetailBanner.start()

        mGoodsDescTv.text = result.goodsDesc
        mGoodsPriceTv.text = YuanFenConverter.changeF2YWithUnit(result.goodsDefaultPrice)
        mSkuSelectedTv.text = result.goodsDefaultSku

        loadPopData(result)
    }


    private fun loadPopData(result: Goods) {
        mSkuPop.setGoodsIcon(result.goodsDefaultIcon)
        mSkuPop.setGoodsCode(result.goodsCode)
        mSkuPop.setGoodsPrice(result.goodsDefaultPrice)
        mSkuPop.setSkuData(result.goodsSku)
    }

    private fun initObserve() {
        Bus.observe<SkuChangedEvent>()
            .subscribe {
                mSkuSelectedTv.text =
                    mSkuPop.getSelectSku() + GoodsConstant.SKU_SEPARATOR + mSkuPop.getSelectCount() + "件"
            }.registerInBus(this)

        Bus.observe<AddCartEvent>()
            .subscribe {
                addCart()
            }.registerInBus(this)
    }


    /*
      初始化缩放动画
   */
    private fun initAnim() {
        mAnimationStart = ScaleAnimation(
            1f, 0.95f, 1f, 0.95f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        )
        mAnimationStart.duration = 500
        mAnimationStart.fillAfter = true

        mAnimationEnd = ScaleAnimation(
            0.95f, 1f, 0.95f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        )
        mAnimationEnd.duration = 500
        mAnimationEnd.fillAfter = true
    }

    override fun onAddCartResult(result: Int) {
        Bus.send(UpdateCartSizeEvent())
        Toast.makeText(activity, "添加购物车成功", Toast.LENGTH_SHORT).show()
    }


    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }


    fun addCart() {
        mCurGoods?.let {
            mPresenter.addCart(
                it.id,
                it.goodsDesc,
                it.goodsDefaultIcon,
                it.goodsDefaultPrice,
                mSkuPop.getSelectCount(),
                mSkuPop.getSelectSku()
            )
        }

    }
}

