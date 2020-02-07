package com.kotlin.usercenter.ui.activity

import android.os.Bundle
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.usercenter.R
import com.kotlin.usercenter.injection.component.DaggerUserComponent
import com.kotlin.usercenter.injection.module.UserModule
import com.kotlin.usercenter.presenter.RegisterPresenter
import com.kotlin.usercenter.presenter.view.RegisterView
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.toast

class RegisterActivity : BaseMvpActivity<RegisterPresenter>(), RegisterView {
    override fun onRegisterResult(result: Boolean) {
        toast("注册 $result")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mPresenter = RegisterPresenter()
        initInjection()

        mRegisterBtn.setOnClickListener {
            mPresenter.register(
                mMobileEt.toString().trim(),
                mVerifyCodeEt.toString().trim(),
                mPwdEt.toString().trim()
            )

        }
    }

    private fun initInjection() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent).userModule(UserModule())
            .build().inject(this)
        mPresenter.mView = this
    }
}
