package com.kotlin.base.rx

import android.util.Log
import com.kotlin.base.presenter.view.BaseView
import rx.Subscriber

open class BaseSubscriber<T>(val baseView: BaseView) : Subscriber<T>() {
    override fun onNext(t: T) {

    }

    override fun onCompleted() {
        baseView.hideLoading()
    }

    override fun onError(e: Throwable?) {
        baseView.hideLoading()
        if (e is BaseException) {
            Log.e("OkHttp", "---------------ERROR------------------")
            e.printStackTrace()
            Log.e("OkHttp", "---------------ERROR------------------")
            baseView.onError(e.msg)
        }
    }
}