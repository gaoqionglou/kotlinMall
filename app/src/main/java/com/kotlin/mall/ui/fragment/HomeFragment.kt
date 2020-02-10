package com.kotlin.mall.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.base.ui.fragment.BaseFragment
import com.kotlin.base.widget.BannerImageLoader
import com.kotlin.mall.R
import com.kotlin.mall.common.*
import com.kotlin.mall.ui.adapter.HomeDiscountAdapter
import com.kotlin.mall.ui.adapter.TopicAdapter
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.fragment_home.*
import me.crosswall.lib.coverflow.CoverFlow

class HomeFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, null)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //必须在onViewCreated 视图完成创建之后才能直接使用xml id
        initBanner()
        initNews()
        initDiscount()
        initTopic()
    }

    private fun initBanner() {
        mHomeBanner.setImageLoader(BannerImageLoader())
        mHomeBanner.setImages(
            arrayListOf(
                HOME_BANNER_ONE, HOME_BANNER_TWO,
                HOME_BANNER_THREE, HOME_BANNER_FOUR
            )
        )
        mHomeBanner.setBannerAnimation(Transformer.Accordion)
        mHomeBanner.isAutoPlay(true)
        mHomeBanner.setDelayTime(2000)
        mHomeBanner.setIndicatorGravity(BannerConfig.RIGHT)
        mHomeBanner.start()

    }

    private fun initNews() {
        //公告
        mNewsFlipperView.setData(arrayOf("夏日炎炎，第一波福利还有30秒到达战场", "新用户立领1000元优惠券"))
    }

    private fun initDiscount() {
        val layoutManager = LinearLayoutManager(activity as Context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        mHomeDiscountRv.layoutManager = layoutManager
        val discountAdapter = HomeDiscountAdapter(activity as Context)
        mHomeDiscountRv.adapter = discountAdapter
        discountAdapter.setData(
            mutableListOf(
                HOME_DISCOUNT_ONE,
                HOME_DISCOUNT_TWO,
                HOME_DISCOUNT_THREE,
                HOME_DISCOUNT_FOUR,
                HOME_DISCOUNT_FIVE
            )
        )

    }

    /*
        初始化主题
     */
    private fun initTopic() {
        //话题
        mTopicPager.adapter = TopicAdapter(
            activity as Context,
            listOf(
                HOME_TOPIC_ONE,
                HOME_TOPIC_TWO,
                HOME_TOPIC_THREE,
                HOME_TOPIC_FOUR,
                HOME_TOPIC_FIVE
            )
        )
        mTopicPager.currentItem = 1
        mTopicPager.offscreenPageLimit = 5

        CoverFlow.Builder().with(mTopicPager).scale(0.3f).pagerMargin(30.0f).spaceSize(0.0f)
            .build()
    }
}