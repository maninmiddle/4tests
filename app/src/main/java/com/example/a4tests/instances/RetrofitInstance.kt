package com.example.a4tests.instances

import com.example.a4tests.data.ConstData
import com.example.a4tests.model.TaskModel
import com.example.a4tests.model.TestModel
import com.example.a4tests.model.VariantModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

object RetrofitInstance {
    val apiMethods: ApiMethods = Retrofit.Builder()
        .baseUrl(ConstData.apiUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiMethods::class.java)
}

interface ApiMethods {
    @GET("getTests")
    fun getTestsList(): Call<List<TestModel>>

    @GET("getTasks")
    fun getTasks(@Query("testId") testId: Int): Call<MutableList<TaskModel>>

    @GET("getVariants")
    fun getVariants(@Query("taskId") taskId: Int): Call<MutableList<VariantModel>>

    @POST("createTest")
    fun createTest(@Body test: TestModel): Call<String>

    @POST("createTask")
    fun createTask(@Body task: TaskModel): Call<String>

    @POST("createVariant")
    fun createVariant(@Body variant: VariantModel): Call<Any>
}