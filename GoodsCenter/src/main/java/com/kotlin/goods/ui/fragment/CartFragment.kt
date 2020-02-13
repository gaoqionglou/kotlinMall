package com.kotlin.goods.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.kennyc.view.MultiStateView
import com.kotlin.base.ext.onClick
import com.kotlin.base.ext.setVisible
import com.kotlin.base.ext.startLoading
import com.kotlin.base.ui.fragment.BaseMvpFragment
import com.kotlin.base.utils.AppPrefsUtils
import com.kotlin.base.utils.YuanFenConverter
import com.kotlin.goods.R
import com.kotlin.goods.common.GoodsConstant
import com.kotlin.goods.data.protocol.CartGoods
import com.kotlin.goods.event.CartAllCheckedEvent
import com.kotlin.goods.event.UpdateCartSizeEvent
import com.kotlin.goods.event.UpdateTotalPriceEvent
import com.kotlin.goods.injection.component.DaggerCartComponent
import com.kotlin.goods.injection.module.CartModule
import com.kotlin.goods.presenter.CartPresenter
import com.kotlin.goods.presenter.view.CartView
import com.kotlin.goods.ui.adapter.CartGoodsAdapter
import kotlinx.android.synthetic.main.fragment_cart.*

class CartFragment : BaseMvpFragment<CartPresenter>(), CartView {


    val cartIdDeleteList: MutableList<Int> = arrayListOf()
    private lateinit var cartGoodsAdapter: CartGoodsAdapter

    private var mTotalPrice: Long = 0
    override fun initComponent() {
        DaggerCartComponent.builder().activityComponent(mActivityComponent).cartModule(CartModule())
            .build().inject(this)
        mPresenter.mView = this
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
//        loadData()
        initObserve()
    }

    private fun initView() {
        mCartGoodsRv.layoutManager = LinearLayoutManager(activity as Context)
        cartGoodsAdapter = CartGoodsAdapter(activity as Context)
        mCartGoodsRv.adapter = cartGoodsAdapter
        mAllCheckedCb.onClick {
            for (item in cartGoodsAdapter.dataList) {
                item.isSelected = mAllCheckedCb.isChecked
            }
            cartGoodsAdapter.notifyDataSetChanged()
            updateTotalPrice()
        }
        mHeaderBar.getRightTextView()?.onClick {
            refreshEditStatus()
        }
        mDeleteBtn.onClick {

            cartGoodsAdapter.dataList.filter { it.isSelected }
                .mapTo(cartIdDeleteList) { it.id }
            if (cartIdDeleteList.size == 0) {
                Toast.makeText(activity, "请选择想要删除的商品", Toast.LENGTH_SHORT).show()
            } else {
                mPresenter.deleteCart(cartIdDeleteList)
            }

        }

        mSettleAccountsBtn.onClick {
            val cartGoodsList: MutableList<CartGoods> = arrayListOf()

            cartGoodsAdapter.dataList.filter { it.isSelected }
                .mapTo(cartGoodsList) { it }

            if (cartGoodsList.size == 0) {
                Toast.makeText(activity, "请选择想要购买的商品", Toast.LENGTH_SHORT).show()
            } else {
                mPresenter.submitCart(cartGoodsList, mTotalPrice)
            }
        }

    }

    private fun refreshEditStatus() {
        val isEditStatus =
            getString(R.string.common_edit).equals(mHeaderBar.getRightTextView()?.text.toString())

        mTotalPriceTv.setVisible(isEditStatus.not())
        mSettleAccountsBtn.setVisible(isEditStatus.not())
        mDeleteBtn.setVisible(isEditStatus)
        mHeaderBar.getRightTextView()?.text =
            if (isEditStatus) getString(R.string.common_complete) else getString(R.string.common_edit)

    }

    private fun loadData() {
        mMultiStateView.startLoading()
        mPresenter.getCartList()
    }

    private fun initObserve() {
        Bus.observe<CartAllCheckedEvent>()
            .subscribe {
                mAllCheckedCb.isChecked = it.isAllChecked
                updateTotalPrice()
            }.registerInBus(this)
        Bus.observe<UpdateTotalPriceEvent>()
            .subscribe {
                updateTotalPrice()
            }.registerInBus(this)

    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }

    override fun onGetCartList(result: MutableList<CartGoods>?) {
        if (result != null && result.size > 0) {
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
            cartGoodsAdapter.setData(result)
            mAllCheckedCb.isChecked = false
            mHeaderBar.getRightTextView()?.setVisible(true)
        } else {
            mHeaderBar.getRightTextView()?.setVisible(false)
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }
        AppPrefsUtils.putInt(GoodsConstant.SP_CART_SIZE, result?.size ?: 0)
        Bus.send(UpdateCartSizeEvent())
        updateTotalPrice()
    }

    private fun updateTotalPrice() {
        mTotalPrice = cartGoodsAdapter.dataList
            .filter { it.isSelected }
            .map { it.goodsCount * it.goodsPrice }
            .sum()


        mTotalPriceTv.text = "合计：${YuanFenConverter.changeF2YWithUnit(mTotalPrice)}"
    }

    override fun onDeleteCartResult(result: Boolean) {
        refreshEditStatus()
//
//        loadData()
        if (result) {
            val data =
                cartGoodsAdapter.dataList.filter { (it.id in cartIdDeleteList).not() } as MutableList
            cartGoodsAdapter.setData(data)
        }
    }

    override fun onSubmitCartListResult(result: String) {
        Toast.makeText(activity, "单号：${result}", Toast.LENGTH_SHORT).show()
    }

    fun setBackVisible(isVisible: Boolean) {
        mHeaderBar.getLeftView()?.setVisible(isVisible)
    }
}