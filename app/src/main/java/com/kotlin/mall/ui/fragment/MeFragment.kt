package com.kotlin.mall.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.base.ext.loadUrl
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.fragment.BaseFragment
import com.kotlin.base.utils.AppPrefsUtils
import com.kotlin.mall.R
import com.kotlin.mall.ui.activity.SettingActivity
import com.kotlin.order.common.OrderConstant
import com.kotlin.order.common.OrderStatus
import com.kotlin.order.ui.activity.OrderActivity
import com.kotlin.order.ui.activity.ShipAddressActivity
import com.kotlin.provider.common.ProviderConstant
import com.kotlin.provider.common.afterLogin
import com.kotlin.provider.common.isLogined
import com.kotlin.usercenter.ui.activity.LoginActivity
import com.kotlin.usercenter.ui.activity.UserInfoActivity
import kotlinx.android.synthetic.main.fragment_me.*


class MeFragment : BaseFragment(), View.OnClickListener {
    override fun onClick(p0: View?) {
        when (p0?.id) {
            mUserIconIv.id, mUserNameTv.id -> {
                if (isLogined()) {
                    startActivity(Intent(activity, UserInfoActivity::class.java))
                } else {
                    startActivity(Intent(activity, LoginActivity::class.java))
                }
            }

            mSettingTv.id -> {
                startActivity(Intent(activity, SettingActivity::class.java))
            }

            mAddressTv.id -> {
                startActivity(Intent(activity, ShipAddressActivity::class.java))
            }
            mAllOrderTv.id -> {
                afterLogin {
                    startActivity(Intent(activity, OrderActivity::class.java))
                }
            }

            mWaitConfirmOrderTv.id -> {
                val intent = Intent(activity, OrderActivity::class.java)
                intent.putExtra(OrderConstant.KEY_ORDER_STATUS, OrderStatus.ORDER_WAIT_CONFIRM)
                startActivity(intent)
            }
            mWaitPayOrderTv.id -> {
                val intent = Intent(activity, OrderActivity::class.java)
                intent.putExtra(OrderConstant.KEY_ORDER_STATUS, OrderStatus.ORDER_WAIT_PAY)
                startActivity(intent)
            }
            mCompleteOrderTv.id -> {
                val intent = Intent(activity, OrderActivity::class.java)
                intent.putExtra(OrderConstant.KEY_ORDER_STATUS, OrderStatus.ORDER_COMPLETED)
                startActivity(intent)
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_me, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mUserNameTv.onClick(this)
        mUserIconIv.onClick(this)
        mSettingTv.onClick(this)
        mAddressTv.onClick(this)
        mAllOrderTv.onClick(this)
        mWaitPayOrderTv.onClick(this)
        mWaitConfirmOrderTv.onClick(this)
        mCompleteOrderTv.onClick(this)
    }

    /**
     * 在onStart中判断用户是否已经登陆
     */
    override fun onStart() {
        super.onStart()
        loadData()
    }

    private fun loadData() {
        if (!isLogined()) {
            //非登录
            mUserIconIv.setImageResource(R.drawable.icon_default_user)
            mUserNameTv.setText(R.string.un_login_text)
        } else {
            //已登录
            val userIcon = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_ICON)
            if (userIcon.isNotEmpty()) {
                mUserIconIv.loadUrl(userIcon)
            }
            mUserNameTv.text = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_NAME)

        }
    }


}