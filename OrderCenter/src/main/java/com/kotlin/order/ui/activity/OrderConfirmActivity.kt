package com.kotlin.order.ui.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.kotlin.base.ext.onClick
import com.kotlin.base.ext.setVisible
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.base.utils.YuanFenConverter
import com.kotlin.order.R
import com.kotlin.order.data.protocol.Order
import com.kotlin.order.event.SelectAddressEvent
import com.kotlin.order.injection.component.DaggerOrderComponent
import com.kotlin.order.injection.module.OrderModule
import com.kotlin.order.presenter.OrderConfirmPresenter
import com.kotlin.order.presenter.view.OrderConfirmView
import com.kotlin.order.ui.adapter.OrderGoodsAdapter
import com.kotlin.provider.common.ProviderConstant
import com.kotlin.provider.router.RouterPath
import kotlinx.android.synthetic.main.activity_order_confirm.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


@Route(path = RouterPath.OrderCenter.PATH_ORDER_CONFIRM)
class OrderConfirmActivity : BaseMvpActivity<OrderConfirmPresenter>(), OrderConfirmView {


    @Autowired(name = ProviderConstant.KEY_ORDER_ID)
    @JvmField
    var mOrderId: Int = 0

    private var mCurrentOrder: Order? = null

    private lateinit var mAdapter: OrderGoodsAdapter
    override fun initComponent() {
        DaggerOrderComponent.builder().activityComponent(mActivityComponent)
            .orderModule(OrderModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_confirm)

        initView()
        loadData()
        initObserve()
    }

    private fun initObserve() {
        Bus.observe<SelectAddressEvent>()
            .subscribe { t: SelectAddressEvent ->
                run {
                    mCurrentOrder?.let {
                        it.shipAddress = t.address
                    }
                    updateAddressView()
                }
            }
            .registerInBus(this)
    }


    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }

    private fun loadData() {
//        mOrderId = intent.getIntExtra(ProviderConstant.KEY_ORDER_ID, 0)
        if (mOrderId != 0) {
            mPresenter.getOrderById(mOrderId)
        }
    }

    private fun initView() {
        mSelectShipTv.onClick {
            startActivity<ShipAddressActivity>()
        }

        mShipView.onClick {
            startActivity<ShipAddressActivity>()
        }

        mSubmitOrderBtn.onClick {
            mCurrentOrder?.let {
                mPresenter.submitOrder(it)
            }
        }
        mOrderGoodsRv.layoutManager = LinearLayoutManager(this)
        mAdapter = OrderGoodsAdapter(this)
        mOrderGoodsRv.adapter = mAdapter
    }


    override fun onGetOrderByIdResult(result: Order) {
        mCurrentOrder = result
        mAdapter.setData(result.orderGoodsList)
        mTotalPriceTv.text = "合计：${YuanFenConverter.changeF2YWithUnit(result.totalPrice)}"

    }

    private fun updateAddressView() {
        mCurrentOrder?.let {
            if (it.shipAddress == null) {
                mSelectShipTv.setVisible(true)
                mShipView.setVisible(false)
            } else {
                mSelectShipTv.setVisible(false)
                mShipView.setVisible(true)

                mShipNameTv.text = it.shipAddress!!.shipUserName + "  " +
                        it.shipAddress!!.shipUserMobile
                mShipAddressTv.text = it.shipAddress!!.shipAddress
            }
        }
    }

    override fun onSubmitOrderResult(result: Boolean) {
        if (result) {
            toast("订单提交成功")
            ARouter.getInstance().build(RouterPath.PaySDK.PATH_PAY)
                .withInt(ProviderConstant.KEY_ORDER_ID, mCurrentOrder!!.id)
                .withLong(ProviderConstant.KEY_ORDER_PRICE, mCurrentOrder!!.totalPrice)
                .navigation()
        }
    }
}