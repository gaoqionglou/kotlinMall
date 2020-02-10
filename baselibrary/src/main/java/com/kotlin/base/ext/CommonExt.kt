package com.kotlin.base.ext

import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.kotlin.base.data.protocol.BaseResp
import com.kotlin.base.rx.BaseFunc
import com.kotlin.base.rx.BaseFuncBoolean
import com.kotlin.base.utils.GlideUtils
import com.kotlin.base.widget.DefaultTextWatcher
import com.trello.rxlifecycle.LifecycleProvider
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * 存放各种扩展方法的文件
 */

fun <T> Observable<T>.execute(subscriber: Subscriber<T>, lifecycleProvider: LifecycleProvider<*>) {
    this.observeOn(AndroidSchedulers.mainThread())
        .compose(lifecycleProvider.bindToLifecycle())
        .subscribeOn(Schedulers.io())
        .subscribe(subscriber)

}

fun <T> Observable<BaseResp<T>>.convert(): Observable<T> {
    return this.flatMap(BaseFunc())
}


fun <T> Observable<BaseResp<T>>.convertBoolean(): Observable<Boolean> {
    return this.flatMap(BaseFuncBoolean())
}

fun View.onClick(listener: View.OnClickListener) {
    this.setOnClickListener(listener)
}

fun View.onClick(method: () -> Unit) {
    this.setOnClickListener { method() }
}

fun Button.enable(et: EditText, method: () -> Boolean) {
    val btn = this //拿到自身的btn
    et.addTextChangedListener(object : DefaultTextWatcher() {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            btn.isEnabled = method()
        }
    })
}

/**
 *
ImageView加载网络图片
 */
fun ImageView.loadUrl(url: String) {
    GlideUtils.loadUrlImage(context, url, this)
}
