package com.kotlin.messege.presenter


import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber

import com.kotlin.messege.data.protocol.Message
import com.kotlin.messege.presenter.view.MessageView
import com.kotlin.messege.service.MessageService
import javax.inject.Inject

/*
    消息列表 Presenter
 */
class MessagePresenter @Inject constructor() : BasePresenter<MessageView>() {

    @Inject
    lateinit var messageService: MessageService

    /*
        获取消息列表
     */
    fun getMessageList() {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        messageService.getMessageList()
            .execute(object : BaseSubscriber<MutableList<Message>?>(mView) {
                override fun onNext(t: MutableList<Message>?) {
                    mView.onGetMessageResult(t)
                }
            }, lifecycleProvider)

    }


}
