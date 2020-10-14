package com.igorf.currency.util

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {

    companion object {
        private const val BASE_URL = "http://api.currencylayer.com/"
    }

    private var client = OkHttpClient.Builder().addInterceptor { chain ->
        var request = chain.request()
        val url = request.url()
            .newBuilder()
            .addQueryParameter("access_key", "e08f4c6440a27c5aa254f25764e98ddf")
            .build()

        request = request
            .newBuilder()
            .url(url)
            .build()

        chain.proceed(request)
    }.build()

    fun getRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}