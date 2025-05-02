package com.compose.app.common.network

import android.util.Log
import com.compose.app.common.di.NetworkHelper
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject
import kotlin.jvm.Throws

class ResponseInterceptor @Inject constructor(private val networkHelper: NetworkHelper) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

//        if (!networkHelper.isNetworkConnected()) throw  NoInternetConnection()

        Log.d("RequestHttp",chain.request().url.toString())
        val url = chain.request().url
            .newBuilder()
//            .addQueryParameter("api_key", BuildConfig.ACCESS_KEY)
            .build()

        val requestBuilder = chain.request()
            .newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("accept", "application/json")
            .addHeader("accept", "application/xml")
            .url(url)

        return chain.proceed(requestBuilder.build())
    }

    inner class NoInternetConnection() : IOException() {

        override val message: String
            get() = " تحقق من إتصال الانترنت "
    }
}
