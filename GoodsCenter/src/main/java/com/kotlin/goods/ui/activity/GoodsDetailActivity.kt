package com.kotlin.goods.ui.activity

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.google.android.material.tabs.TabLayout
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.activity.BaseActivity
import com.kotlin.goods.R
import com.kotlin.goods.ui.adapter.GoodsDetailVpAdapter
import com.kotlin.provider.router.RouterPath
import kotlinx.android.synthetic.main.activity_goods_detail.*

class GoodsDetailActivity : BaseActivity() {

    fun getRootView(): View {
        return mRootView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goods_detail)
        initView()
    }

    private fun initView() {
        mGoodsDetailTab.tabMode = TabLayout.MODE_FIXED
        mGoodsDetailVp.adapter = GoodsDetailVpAdapter(supportFragmentManager, this)

        mGoodsDetailTab.setupWithViewPager(mGoodsDetailVp)
        mLeftIv.onClick { finish() }
        mAddCartBtn.onClick {
            ARouter.getInstance().build(RouterPath.UserCenter.PATH_LOGIN).navigation()
        }
    }


}