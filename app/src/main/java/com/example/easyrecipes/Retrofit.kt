package com.example.easyrecipes

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL: String = "https://api.spoonacular.com/"

object Retrofit {

    private val httpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val token = BuildConfig.API_KEY
                val original = chain.request()
                val request = original.newBuilder()
                    .header("x-api-key", token)
                    .build()
                chain.proceed(request = request)
            }
            .build()
    }

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}