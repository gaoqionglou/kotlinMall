package com.kotlin.usercenter.ui.activity

import android.os.Bundle
import android.view.View
import com.kotlin.base.ext.enable
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.user.utils.UserPrefsUtils
import com.kotlin.usercenter.R
import com.kotlin.usercenter.data.protocol.UserInfo
import com.kotlin.usercenter.injection.component.DaggerUserComponent
import com.kotlin.usercenter.injection.module.UserModule
import com.kotlin.usercenter.presenter.LoginPresenter
import com.kotlin.usercenter.presenter.view.LoginView
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


class LoginActivity : BaseMvpActivity<LoginPresenter>(), LoginView, View.OnClickListener {


    override fun initComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent).userModule(UserModule())
            .build().inject(this)
        mPresenter.mView = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initView()
    }

    private fun initView() {
        mLoginBtn.enable(mMobileEt, { isBtnEnable() })
        mLoginBtn.enable(mPwdEt, { isBtnEnable() })

        mLoginBtn.onClick(this)
        mForgetPwdTv.onClick(this)
        mHeaderBar.getRightTextView()?.onClick(this)
    }


    private fun isBtnEnable(): Boolean {
        return mMobileEt.text.isNullOrEmpty().not() &&
                mPwdEt.text.isNullOrEmpty().not()
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            mLoginBtn.id -> {
                mPresenter.login(mMobileEt.text.toString(), mPwdEt.text.toString(), "")
            }
            mHeaderBar.getRightTextView()?.id -> {
                startActivity<RegisterActivity>()
                this.finish()
            }
            mForgetPwdTv.id -> {
                startActivity<ForgetPwdActivity>()
            }
        }
    }

    override fun onLoginResult(result: UserInfo) {
        toast("登录成功")
        UserPrefsUtils.putUserInfo(result)
        finish()
    }
}
