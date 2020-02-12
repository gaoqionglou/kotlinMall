package com.kotlin.goods.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.base.ui.fragment.BaseMvpFragment
import com.kotlin.goods.R
import com.kotlin.goods.data.protocol.CartGoods
import com.kotlin.goods.injection.component.DaggerCartComponent
import com.kotlin.goods.injection.module.CartModule
import com.kotlin.goods.presenter.CartPresenter
import com.kotlin.goods.presenter.view.CartView

class CartFragment : BaseMvpFragment<CartPresenter>(), CartView {
    override fun initComponent() {
        DaggerCartComponent.builder().activityComponent(mActivityComponent).cartModule(CartModule())
            .build().inject(this)
        mPresenter.mView = this
    }

    override fun onGetCartList(result: MutableList<CartGoods>?) {

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {

    }
}