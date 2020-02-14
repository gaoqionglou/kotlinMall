package com.kotlin.messege.data.api

import com.kotlin.base.data.protocol.BaseResp
import com.kotlin.messege.data.protocol.Message
import retrofit2.http.POST
import rx.Observable

/*
    消息 接口
 */
interface MessageApi {

    /*e
        获取消息列表
     */
    @POST("msg/getList")
    fun getMessageList(): Observable<BaseResp<MutableList<Message>?>>

}
