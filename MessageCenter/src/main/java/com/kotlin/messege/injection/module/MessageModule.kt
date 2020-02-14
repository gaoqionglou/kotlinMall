package com.kotlin.messege.injection.module

import com.kotlin.messege.service.MessageService
import com.kotlin.messege.service.impl.MessageServiceImpl
import dagger.Module
import dagger.Provides

/*
    消息模块业务注入
 */
@Module
class MessageModule {

    @Provides
    fun provideMessageService(messageService: MessageServiceImpl): MessageService {
        return messageService
    }

}
