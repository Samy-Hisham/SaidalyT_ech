package com.android.saidalytech.di

import com.android.saidalytech.network.WebServices
import com.android.saidalytech.utils.Const
import com.android.saidalytech.uitls.MySharedPreference
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyManagementException
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@dagger.Module
@InstallIn(SingletonComponent::class)
object Module {

    @Singleton
    @Provides
    fun provideRetrofit(): WebServices {
        val client = OkHttpClient.Builder()
            .apply {
                try {
                    val tlsSocketFactory = TLSSocketFactory()
                    if (tlsSocketFactory.getTrustManager() != null) {
                        sslSocketFactory(tlsSocketFactory, tlsSocketFactory.getTrustManager()!!)
                    }
                } catch (e: KeyManagementException) {
                    e.printStackTrace()
                } catch (e: NoSuchAlgorithmException) {
                    e.printStackTrace()
                } catch (e: KeyStoreException) {
                    e.printStackTrace()
                }
            }
            .connectTimeout(50, TimeUnit.SECONDS)
            .writeTimeout(150, TimeUnit.SECONDS)
            .readTimeout(50, TimeUnit.SECONDS)
            .callTimeout(50, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(Interceptor { chain ->
                val originalRequest = chain.request()
                val originalUrl = originalRequest.url
                val url = originalUrl.newBuilder().build()
                val requestBuilder = originalRequest.newBuilder().url(url)
                    .addHeader("Accept", "application/json")
                    .addHeader("Authorization", "Bearer ${MySharedPreference.getUserToken()}"
                    )
                val request = requestBuilder.build()
                val response = chain.proceed(request)
                response.code//status code
                response
            }).build()

        return Retrofit.Builder()
            .client(client)
            .baseUrl(Const.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build().create(WebServices::class.java)
    }
}