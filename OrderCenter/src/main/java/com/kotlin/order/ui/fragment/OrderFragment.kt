package com.kotlin.order.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.kennyc.view.MultiStateView
import com.kotlin.base.ext.startLoading
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.base.ui.fragment.BaseMvpFragment
import com.kotlin.order.R
import com.kotlin.order.common.OrderConstant
import com.kotlin.order.data.protocol.Order
import com.kotlin.order.injection.component.DaggerOrderComponent
import com.kotlin.order.presenter.OrderListPresenter
import com.kotlin.order.presenter.view.OrderListView
import com.kotlin.order.ui.activity.OrderDetailActivity
import com.kotlin.order.ui.adapter.OrderAdapter
import com.kotlin.provider.common.ProviderConstant
import com.kotlin.provider.router.RouterPath
import kotlinx.android.synthetic.main.fragment_order.*

class OrderFragment : BaseMvpFragment<OrderListPresenter>(), OrderListView {

    private lateinit var mAdapter: OrderAdapter
    override fun initComponent() {
        DaggerOrderComponent.builder().activityComponent(mActivityComponent).build().inject(this)
        mPresenter.mView = this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_order, container, false)
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
//        loadData()
    }

    private fun initView() {
        mOrderRv.layoutManager = LinearLayoutManager(activity)
        mAdapter = OrderAdapter(activity as Context)
        mOrderRv.adapter = mAdapter
        mAdapter.listener = object : OrderAdapter.OnOptClickListener {
            override fun onOptClick(optType: Int, order: Order) {
                when (optType) {
                    OrderConstant.OPT_ORDER_CANCEL -> {
                        AlertView(
                            "取消",
                            "确定取消该订单？",
                            "取消",
                            null,
                            arrayOf("确定"),
                            activity,
                            AlertView.Style.Alert, OnItemClickListener { o, position ->
                                if (position == 0) {
                                    mPresenter.cancelOrder(order.id)
                                }
                            }

                        ).show()

                    }
                    OrderConstant.OPT_ORDER_CONFIRM -> {
                        mPresenter.confirmOrder(order.id)
                    }
                    OrderConstant.OPT_ORDER_PAY -> {

                        ARouter.getInstance().build(RouterPath.PaySDK.PATH_PAY)
                            .withInt(ProviderConstant.KEY_ORDER_ID, order.id)
                            .withLong(ProviderConstant.KEY_ORDER_PRICE, order.totalPrice)
                            .navigation()
                    }
                }
            }

        }
        mAdapter.setOnItemClickListener(object :
            BaseRecyclerViewAdapter.OnItemClickListener<Order> {
            override fun onItemClick(item: Order, position: Int) {
                val intent = Intent(activity, OrderDetailActivity::class.java)
                intent.putExtra(ProviderConstant.KEY_ORDER_ID, item.id)
                startActivity(intent)
            }
        })
    }

    private fun loadData() {
        mMultiStateView.startLoading()
        mPresenter.getOrderList(arguments?.getInt(OrderConstant.KEY_ORDER_STATUS, -1) ?: -1)
    }

    override fun onGetOrderListResult(result: MutableList<Order>?) {
        if (result != null && result.size > 0) {
            mAdapter.setData(result)
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
        } else {
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }
    }

    override fun onConfirmOrderResult(orderId: Int, result: Boolean) {
        Toast.makeText(activity, "确认收货成功", Toast.LENGTH_SHORT).show()
        if (result) {
            val data =
                mAdapter.dataList.filter { it.id != orderId } as MutableList
            mAdapter.setData(data)
        }
    }

    override fun onCancelOrderResult(orderId: Int, result: Boolean) {

        Toast.makeText(activity, "取消订单成功", Toast.LENGTH_SHORT).show()
        if (result) {
            val data =
                mAdapter.dataList.filter { it.id != orderId } as MutableList
            mAdapter.setData(data)
        }
    }
}