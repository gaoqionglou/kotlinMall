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


        val debugMap =
            mapOf(
                login_url to login_data, register_url to register_data,
                forgetPwd_url to forgetPwd_data, resetPwd_url to resetPwd_data,
                editUser_url to editUser_data, getTopCategory_url to getTopCategory_data,
                getSecondaryCategory_url to getSecondaryCategory_data,
                getGoodsList_url to getGoodsList_data,
                getGoodsListByKeyword_url to getGoodsListByKeyword_data
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