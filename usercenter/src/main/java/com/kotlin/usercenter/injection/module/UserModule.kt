package com.kotlin.usercenter.injection.module

import com.kotlin.usercenter.service.UserService
import com.kotlin.usercenter.service.impl.UserServiceImpl
import dagger.Module
import dagger.Provides

@Module
class UserModule {

    //注意注解的先后顺序 否则报错
//    @Named("service")
    @Provides
    fun provideUserService(service: UserServiceImpl): UserService {
        return service
    }


//    @Named("service2")
//    @Provides
//    fun provideUserService2(service: UserServiceImpl2): UserService {
//        return service
//    }
}