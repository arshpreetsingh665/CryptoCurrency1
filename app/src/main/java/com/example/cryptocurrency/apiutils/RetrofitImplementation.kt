package com.example.cryptocurrency.apiutils

import com.example.cryptocurrency.interfacepackage.ApiInterface
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitImplementation {
    companion object {
        fun getInstance(): ApiInterface {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            val httpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor)
            return Retrofit.Builder().baseUrl("https://api.coinmarketcap.com/")
                .addConverterFactory(GsonConverterFactory.create()).client(httpClient.build())
                .build().create(
                    ApiInterface::class.java
                )
        }
    }
}