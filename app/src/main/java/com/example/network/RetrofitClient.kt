package com.example.network

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://localhost/") // Base URL overridden by complete URLs in GET if needed, or we just put placeholder
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val apiServices: ApiServices = retrofit.create(ApiServices::class.java)
}
