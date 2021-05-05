package com.example.a4tests.instances

import com.example.a4tests.data.ConstData
import com.example.a4tests.model.TestModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

object RetrofitInstance {
    val apiMethods: ApiMethods = Retrofit.Builder()
        .baseUrl(ConstData.apiUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiMethods::class.java)
}

interface ApiMethods {
    @GET("tests")
    fun getTestsList(): Call<List<TestModel>>
}