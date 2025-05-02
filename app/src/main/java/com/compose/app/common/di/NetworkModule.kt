package com.compose.app.common.di
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.compose.app.common.network.ResponseInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofitInstance(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://lynxapp.com/localalerts.php/")
            .client(okHttpClient)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()

    }




    @Provides
    @Singleton
    fun provideOKHTTPInstance(networkHelper: NetworkHelper): OkHttpClient {
        val httpClient = OkHttpClient().newBuilder()
            .addInterceptor(ResponseInterceptor(networkHelper))
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)

//        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(loggingInterceptor).build()
//        }

        return httpClient.build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()



}