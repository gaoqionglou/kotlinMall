package com.kotlin.order.presenter

import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.order.data.protocol.ShipAddress
import com.kotlin.order.presenter.view.ShipAddressView
import com.kotlin.order.service.ShipAddressService
import javax.inject.Inject

class ShipAddressPresenter @Inject constructor() : BasePresenter<ShipAddressView>() {

    @Inject
    lateinit var service: ShipAddressService


    fun getShipAddressList() {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        service.getShipAddressList()
            .execute(object : BaseSubscriber<MutableList<ShipAddress>?>(mView) {
                override fun onNext(t: MutableList<ShipAddress>?) {
                    mView.onGetShipAddressListResult(t)
                }
            }, lifecycleProvider)

    }


    fun setDefaultAddress(address: ShipAddress) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        service.editShipAddress(address).execute(object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {
                mView.onSetDefaultResult(address, t)
            }
        }, lifecycleProvider)
    }

    fun deleteAddress(addressId: Int) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        service.deleteShipAddress(addressId).execute(object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {
                mView.onDeleteAddressResult(addressId, t)
            }
        }, lifecycleProvider)
    }

}