package com.kotlin.base.data.net

import com.kotlin.base.common.Constant
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


//工厂单例
class RetrofitFactory private constructor() {
    /**
     * 单例实现
     */
    companion object {
        val instance: RetrofitFactory by lazy { RetrofitFactory() }
    }

    private val retrofit: Retrofit
    private val interceptor: Interceptor

    init {

        //通用拦截
        interceptor = Interceptor {

                chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("Content_Type", "application/json")
                .addHeader("charset", "UTF-8")
//                .addHeader("token", AppPrefsUtils.getString(BaseConstant.KEY_SP_TOKEN))
                .build()

            chain.proceed(request)
        }

        /* interceptor = object :Interceptor{
             override fun intercept(chain: Interceptor.Chain): Response {
                 val request = chain.request()
                     .newBuilder()
                     .addHeader("Content_Type", "application/json")
                     .addHeader("charset", "UTF-8")
 //                .addHeader("token", AppPrefsUtils.getString(BaseConstant.KEY_SP_TOKEN))
                     .build()

                return  chain.proceed(request)
             }

    }*/
        //Retrofit实例化
        retrofit = Retrofit.Builder()
            .baseUrl(Constant.SERVER_ADDRESS)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .client(initClient())
            .build()
    }

    /**
     * okhttp创建
     */
    private fun initClient(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(initLogInterceptor())
            .addInterceptor(interceptor)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    /**
     * 日志拦截器
     */
    private fun initLogInterceptor(): Interceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    /**
     * 具体服务实例化
     */
    fun <T> create(service: Class<T>): T {
        return retrofit.create(service)
    }
}