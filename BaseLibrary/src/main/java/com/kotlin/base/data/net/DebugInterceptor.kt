package com.kotlin.base.data.net


import androidx.annotation.RawRes
import com.kotlin.base.R
import com.kotlin.base.common.BaseApplication
import com.kotlin.base.common.BaseConstant
import com.kotlin.freak_core.util.file.FileUtil

import okhttp3.*


class DebugInterceptor(
    private val DEBUG_URL: String = "",
    private val DEBUG_RAW_ID: Int = 0
) : BaseInterceptor() {


    companion object {

        var apphost: String? = BaseConstant.SERVER_ADDRESS
        var login_url: String = apphost + "userCenter/login"
        var login_data: Int = R.raw.login_data

        var register_url: String = apphost + "userCenter/register"
        val register_data: Int = R.raw.register_data

        var forgetPwd_url: String = apphost + "userCenter/forgetPwd"
        val forgetPwd_data: Int = R.raw.forgetpwd_data

        var resetPwd_url: String = apphost + "userCenter/resetPwd"
        val resetPwd_data: Int = R.raw.resetpwd_data


        var editUser_url: String = apphost + "userCenter/editUser"
        val editUser_data: Int = R.raw.edituser_data

        var getTopCategory_url: String = apphost + "category/getTopCategory"
        val getTopCategory_data: Int = R.raw.gettopcategory_data

        var getSecondaryCategory_url: String = apphost + "category/getSecondaryCategory"
        val getSecondaryCategory_data: Int = R.raw.getsescondarycategory_data

        var getGoodsList_url: String = apphost + "goods/getGoodsList"
        val getGoodsList_data: Int = R.raw.getgoodslist_data

        var getGoodsListByKeyword_url: String = apphost + "goods/getGoodsListByKeyword"
        val getGoodsListByKeyword_data: Int = R.raw.getgoodslistbykeyword_data

        var getGoodsDetail_url: String = apphost + "goods/getGoodsDetail"
        val getGoodsDetail_data: Int = R.raw.getgoodsdetail_data

        var cartAdd_url: String = apphost + "cart/add"
        val cartAdd_data: Int = R.raw.cartadd_data


        var cartgetList_url: String = apphost + "cart/getList"
        val cartgetList_data: Int = R.raw.cartgetlist_data


        var cartdelete_url: String = apphost + "cart/delete"
        val cartdelete_data: Int = R.raw.cartdelete_data


        var cartsubmit_url: String = apphost + "cart/submit"
        val cartsubmit_data: Int = R.raw.cartsubmit_data

        var shipaddressadd_url: String = apphost + "shipAddress/add"
        val shipaddressadd_data: Int = R.raw.shipaddressadd_data

        var shipaddressdelete_url: String = apphost + "shipAddress/delete"
        val shipaddressdelete_data: Int = R.raw.shipaddressdelete_data

        var shipaddressmodify_url: String = apphost + "shipAddress/modify"
        val shipaddressmodify_data: Int = R.raw.shipaddressmodify_data

        var ordergetorderbyid_url: String = apphost + "order/getOrderById"
        val ordergetorderbyid_data: Int = R.raw.getorderbyid_data

        var shipaddressgetlist_url: String = apphost + "shipAddress/getList"
        val shipaddressgetlist_data: Int = R.raw.shipaddressgetlist_data


        var ordersubmitorder_url: String = apphost + "order/submitOrder"
        val ordersubmitorder_data: Int = R.raw.ordersubmitorder_data


        var ordergetorderlist_url: String = apphost + "order/getOrderList"
        val ordergetorderlist_data: Int = R.raw.ordergetorderlist_data

        var ordercancel_url: String = apphost + "order/cancel"
        val ordercancel_data: Int = R.raw.ordercancel_data

        var orderconfirm_url: String = apphost + "order/confirm"
        val orderconfirm_data: Int = R.raw.orderconfirm_data

        var getpaysign_url: String = apphost + "pay/getPaySign"
        val getpaysign_data: Int = R.raw.getpaysign_data

        var pay_url: String = apphost + "order/pay"
        val pay_data: Int = R.raw.pay_data


        val debugMap =
            mapOf(
                login_url to login_data,
                register_url to register_data,
                forgetPwd_url to forgetPwd_data,
                resetPwd_url to resetPwd_data,
                editUser_url to editUser_data,
                getTopCategory_url to getTopCategory_data,
                getSecondaryCategory_url to getSecondaryCategory_data,
                getGoodsList_url to getGoodsList_data,
                getGoodsListByKeyword_url to getGoodsListByKeyword_data,
                getGoodsDetail_url to getGoodsDetail_data,
                cartAdd_url to cartAdd_data,
                cartgetList_url to cartgetList_data,
                cartdelete_url to cartdelete_data,
                cartsubmit_url to cartsubmit_data, shipaddressadd_url to shipaddressadd_data,
                shipaddressdelete_url to shipaddressdelete_data,
                shipaddressmodify_url to shipaddressmodify_data,
                ordergetorderbyid_url to ordergetorderbyid_data,
                shipaddressgetlist_url to shipaddressgetlist_data,
                ordersubmitorder_url to ordersubmitorder_data,
                ordergetorderlist_url to ordergetorderlist_data,
                ordercancel_url to ordercancel_data,
                orderconfirm_url to orderconfirm_data,
                getpaysign_url to getpaysign_data,
                pay_url to pay_data

            )


    }


    override fun onIntercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url().toString()
        if (debugMap.containsKey(url)) {
            return debugResponse(chain, debugMap[url] ?: -1)
        }

        return chain.proceed(chain.request())
    }

    private fun getResponse(chain: Interceptor.Chain, json: String): Response {
        return Response.Builder()
            .code(200)
            .addHeader("Content-Type", "application/json")
            .body(ResponseBody.create(MediaType.parse("application/json"), json))
            .message("OK")
            .request(chain.request())
            .protocol(Protocol.HTTP_1_1)
            .build()
    }

    private fun debugResponse(chain: Interceptor.Chain, @RawRes idRes: Int): Response {
        val jsonRawStr = FileUtil.readFileFromRaw(BaseApplication.context, idRes)

        return Response.Builder()
            .code(200)
            .addHeader("Content-Type", "application/json")
            .body(ResponseBody.create(MediaType.parse("application/json"), jsonRawStr))
            .message("OK")
            .request(chain.request())
            .protocol(Protocol.HTTP_1_1)
            .build()
    }


}