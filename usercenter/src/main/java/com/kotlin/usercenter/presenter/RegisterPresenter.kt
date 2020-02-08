package com.kotlin.usercenter.presenter


import com.kotlin.base.ext.excute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.usercenter.presenter.view.RegisterView
import com.kotlin.usercenter.service.UserService
import javax.inject.Inject
import javax.inject.Named

class RegisterPresenter @Inject constructor() : BasePresenter<RegisterView>() {

    @Inject
//    @Named("service") //java中可行，kotlin要用下面的方式
    @field:[Named("service")]
    lateinit var userService: UserService

    @Inject
    @field:[Named("service2")]
    lateinit var userService2: UserService

    fun register(mobile: String, verifyCode: String, pwd: String) {
//        val userService = UserServiceImpl()
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userService.register(mobile, verifyCode, pwd)

            .excute(object : BaseSubscriber<Boolean>(mView) {
                override fun onNext(t: Boolean) {
                    mView.onRegisterResult(if (t) "注册成功" else "注册失败")
                }


            }, lifecycleProvider)


    }

    //多个接口实现通过@Named注解区分
    fun register2(mobile: String, verifyCode: String, pwd: String) {
        mView.showLoading()
        userService2.register(mobile, verifyCode, pwd)

            .excute(object : BaseSubscriber<Boolean>(mView) {
                override fun onNext(t: Boolean) {
                    mView.onRegisterResult(if (t) "注册成功" else "注册失败")
                }


            }, lifecycleProvider)


    }
}