package com.kotlin.usercenter.ui.activity

import android.os.Bundle
import android.view.View
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.usercenter.R
import com.kotlin.usercenter.injection.component.DaggerUserComponent
import com.kotlin.usercenter.injection.module.UserModule
import com.kotlin.usercenter.presenter.RegisterPresenter
import com.kotlin.usercenter.presenter.view.RegisterView
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.toast

class RegisterActivity : BaseMvpActivity<RegisterPresenter>(), RegisterView {
    override fun initComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent).userModule(UserModule())
            .build().inject(this)
        mPresenter.mView = this
    }

    override fun onRegisterResult(result: String) {
        toast(result)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        mPresenter = RegisterPresenter()


        mRegisterBtn.onClick {
            mPresenter.register(
                mMobileEt.toString().trim(),
                mVerifyCodeEt.toString().trim(),
                mPwdEt.toString().trim()
            )
        }


        mRegisterBtn.onClick(View.OnClickListener {
            mPresenter.register(
                mMobileEt.toString().trim(),
                mVerifyCodeEt.toString().trim(),
                mPwdEt.toString().trim()
            )
        })

        mRegisterBtn.setOnClickListener {
            mPresenter.register(
                mMobileEt.toString().trim(),
                mVerifyCodeEt.toString().trim(),
                mPwdEt.toString().trim()
            )

        }
    }


}
