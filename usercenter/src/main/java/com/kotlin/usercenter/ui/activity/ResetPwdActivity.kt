package com.kotlin.usercenter.ui.activity

import android.os.Bundle
import android.view.View
import com.kotlin.base.ext.enable
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.usercenter.R
import com.kotlin.usercenter.injection.component.DaggerUserComponent
import com.kotlin.usercenter.injection.module.UserModule
import com.kotlin.usercenter.presenter.ResetPwdPresenter
import com.kotlin.usercenter.presenter.view.ResetPwdView
import kotlinx.android.synthetic.main.activity_reset_pwd.*
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.singleTop
import org.jetbrains.anko.toast

class ResetPwdActivity : BaseMvpActivity<ResetPwdPresenter>(), ResetPwdView,
    View.OnClickListener {


    override fun onResetPwdResult(result: String) {
        toast("忘记密码 业务接口验证成功")
        startActivity(intentFor<LoginActivity>().singleTop().clearTop())
    }

    override fun initComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent).userModule(UserModule())
            .build().inject(this)
        mPresenter.mView = this
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_pwd)

        initView()
    }

    private fun initView() {
        mConfirmBtn.enable(mPwdEt, { isBtnEnable() })
        mConfirmBtn.enable(mPwdConfirmEt, { isBtnEnable() })
        mConfirmBtn.onClick(this)


    }

    private fun isBtnEnable(): Boolean {
        return mPwdEt.text.isNullOrEmpty().not() &&
                mPwdConfirmEt.text.isNullOrEmpty().not()
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            mConfirmBtn.id -> {

                if (mPwdConfirmEt.text.toString() != mPwdEt.text.toString()) {
                    toast("输入的密码不一致")
                    return
                }
                val mobile = intent.getStringExtra("mobile") ?: ""
                if (mobile.isEmpty()) {
                    toast("手机号不能为空")
                    return
                }
                mPresenter.resetPwd(mobile, mPwdEt.text.toString())
            }

        }
    }
}
