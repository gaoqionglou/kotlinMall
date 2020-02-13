package com.kotlin.order.ui.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.eightbitlab.rxbus.Bus
import com.kennyc.view.MultiStateView
import com.kotlin.base.ext.onClick
import com.kotlin.base.ext.startLoading
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.order.R
import com.kotlin.order.common.OrderConstant
import com.kotlin.order.data.protocol.ShipAddress
import com.kotlin.order.event.SelectAddressEvent
import com.kotlin.order.injection.component.DaggerShipAddressComponent
import com.kotlin.order.injection.module.ShipAddressModule
import com.kotlin.order.presenter.ShipAddressPresenter
import com.kotlin.order.presenter.view.ShipAddressView
import com.kotlin.order.ui.adapter.ShipAddressAdapter
import kotlinx.android.synthetic.main.activity_address.*
import org.jetbrains.anko.startActivity

class ShipAddressActivity : BaseMvpActivity<ShipAddressPresenter>(), ShipAddressView {

    private lateinit var mAdapter: ShipAddressAdapter
    override fun initComponent() {
        DaggerShipAddressComponent.builder().activityComponent(mActivityComponent)
            .shipAddressModule(
                ShipAddressModule()
            ).build().inject(this)
        mPresenter.mView = this
    }

    override fun onGetShipAddressListResult(result: MutableList<ShipAddress>?) {
        if (result == null || result.isEmpty()) {
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
            return
        }

        mAdapter.setData(result)

        mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    private fun loadData() {
        mMultiStateView.startLoading()
        mPresenter.getShipAddressList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)
        initView()
    }

    private fun initView() {
        mAddAddressBtn.onClick {
            startActivity<ShipAddressEditActivity>()
        }


        mAddressRv.layoutManager = LinearLayoutManager(this)
        mAdapter = ShipAddressAdapter(this)
        mAddressRv.adapter = mAdapter
        mAdapter.mOptClickListener = object : ShipAddressAdapter.OnOptClickListener {
            override fun onSetDefault(address: ShipAddress) {
                mPresenter.setDefaultAddress(address)
            }

            override fun onEdit(address: ShipAddress) {
                startActivity<ShipAddressEditActivity>(OrderConstant.KEY_SHIP_ADDRESS to address)

            }

            override fun onDelete(address: ShipAddress) {
                AlertView(
                    "删除",
                    "确定删除该地址？",
                    "取消",
                    null,
                    arrayOf("确定"),
                    this@ShipAddressActivity,
                    AlertView.Style.Alert,
                    OnItemClickListener { o, position ->
                        if (position == 0) {
                            mPresenter.deleteAddress(address.id)
                        }
                    }

                ).show()
            }

        }
        mAdapter.setOnItemClickListener(object :
            BaseRecyclerViewAdapter.OnItemClickListener<ShipAddress> {
            override fun onItemClick(item: ShipAddress, position: Int) {
                Bus.send(SelectAddressEvent(item))
                this@ShipAddressActivity.finish()
            }

        })

    }

    override fun onEditAddressResult(result: Boolean) {

    }

    override fun onSetDefaultResult(result: ShipAddress, t: Boolean) {
//        loadData()
        if (t) {
            mAdapter.dataList.forEach {
                if (it.id != result.id) {
                    it.shipIsDefault = 1
                } else {
                    it.shipIsDefault = 0
                }
            }
            mAdapter.notifyDataSetChanged()
        }
    }

    override fun onDeleteAddressResult(addressID: Int, result: Boolean) {
        if (result) {
            val data = mAdapter.dataList.filter { it.id != addressID } as MutableList
            mAdapter.setData(data)
        }

        if (mAdapter.dataList.size == 0) {
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }

    }


}
