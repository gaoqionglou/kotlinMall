package com.kotlin.pay.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.base.utils.YuanFenConverter
import com.kotlin.pay.R
import com.kotlin.pay.injection.component.DaggerPayComponent
import com.kotlin.pay.injection.module.PayModule
import com.kotlin.pay.presenter.PayPresenter
import com.kotlin.pay.presenter.view.PayView
import com.kotlin.provider.common.ProviderConstant
import com.kotlin.provider.router.RouterPath
import kotlinx.android.synthetic.main.activity_cash_register.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

@Route(path = RouterPath.PaySDK.PATH_PAY)
class PayActivity : BaseMvpActivity<PayPresenter>(), PayView, View.OnClickListener {
    override fun onGetSignResult(result: String) {
        Toast.makeText(this, "支付签名成功：${result}", Toast.LENGTH_SHORT).show()
        //开启异步线程
        doAsync {
            //            val resultMap:Map<String,String> = PayTask(this@CashRegisterActivity).payV2(result,true)
            uiThread {
                mPresenter.payOrderResult(mOrderId)
            }

        }
    }

    override fun onPayOrderResult(result: Boolean) {
        if (result) {
            Toast.makeText(this, "支付成功：${result}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.mAlipayTypeTv -> {
                updatePayType(true, false, false)
            }
            R.id.mWeixinTypeTv -> {
                updatePayType(false, true, false)
            }
            R.id.mBankCardTypeTv -> {
                updatePayType(false, false, true)
            }
            mPayBtn.id -> {
                AlertView(
                    "支付",
                    "确定支付该订单？",
                    "取消",
                    null,
                    arrayOf("确定"),
                    this@PayActivity,
                    AlertView.Style.Alert,
                    OnItemClickListener { o, position ->
                        if (position == 0) {
                            mPresenter.payOrderResult(mOrderId)
                        }
                    }

                ).show()
            }
        }
    }


    //订单号
    var mOrderId: Int = 0
    //订单总价格
    var mTotalPrice: Long = 0

    override fun initComponent() {
        DaggerPayComponent.builder().activityComponent(mActivityComponent).payModule(PayModule())
            .build().inject(this)
        mPresenter.mView = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cash_register)
        ARouter.getInstance().inject(this)
        initView()
        initData()
    }

    /*
       初始化视图
    */
    private fun initView() {

        mAlipayTypeTv.isSelected = true
        mAlipayTypeTv.onClick(this)
        mWeixinTypeTv.onClick(this)
        mBankCardTypeTv.onClick(this)
        mPayBtn.onClick(this)
    }


    /*
        初始化数据
     */
    private fun initData() {
        mOrderId = intent.getIntExtra(ProviderConstant.KEY_ORDER_ID, -1)
        mTotalPrice = intent.getLongExtra(ProviderConstant.KEY_ORDER_PRICE, -1)

        mTotalPriceTv.text = YuanFenConverter.changeF2YWithUnit(mTotalPrice)
    }

    /*
        选择支付类型，UI变化
     */
    private fun updatePayType(isAliPay: Boolean, isWeixinPay: Boolean, isBankCardPay: Boolean) {
        mAlipayTypeTv.isSelected = isAliPay
        mWeixinTypeTv.isSelected = isWeixinPay
        mBankCardTypeTv.isSelected = isBankCardPay
    }

}
