package com.kotlin.base.data.net

import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Response

abstract class BaseInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return onIntercept(chain)
    }

    abstract fun onIntercept(chain: Interceptor.Chain): Response

    fun getUrlParameters(chain: Interceptor.Chain): LinkedHashMap<String, String> {
        val url = chain.request().url()
        val size = url.querySize()
        val params = LinkedHashMap<String, String>()
        if (size > 1) {
            for (i in 0 until size) {
                //不包括size
                params[url.queryParameterName(i)] = url.queryParameterValue(i)
            }
        }
        println(params.toString())
        return params
    }

    fun getUrlParameters(chain: Interceptor.Chain, key: String): String? {
        val url = chain.request().url()
        return url.queryParameter(key)
    }

    fun getBodyParameters(chain: Interceptor.Chain): LinkedHashMap<String, String> {
        val formBody = chain.request().body() as FormBody
        val params = LinkedHashMap<String, String>()
        val size = formBody.size()
        if (size > 1) {
            for (i in 0 until size) {
                //不包括size
                params[formBody.name(i)] = formBody.value(i)
            }
        }
        println(params.toString())
        return params
    }

    fun getBodyParameters(chain: Interceptor.Chain, key: String): String? {
        return getBodyParameters(chain)[key]
    }
}