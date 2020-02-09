package com.kotlin.usercenter.injection.module

import com.kotlin.usercenter.service.UploadService
import com.kotlin.usercenter.service.impl.UploadServiceImpl
import dagger.Module
import dagger.Provides

@Module
class UploadModule {

    @Provides
    fun provideUploadService(service: UploadServiceImpl): UploadService {
        return service
    }

}