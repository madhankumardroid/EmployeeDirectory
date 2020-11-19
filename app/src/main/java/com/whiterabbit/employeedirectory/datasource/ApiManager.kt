package com.whiterabbit.employeedirectory.datasource

import com.google.gson.GsonBuilder
import com.whiterabbit.employeedirectory.datasource.models.Employee
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.io.IOException
import java.util.concurrent.TimeUnit

object ApiManager {
    private const val TIMEOUT: Long = 30

    val retrofitClient: EmployeeApiInterface by lazy {
        buildRetrofit(authorizedOkHttpClient).create(
                EmployeeApiInterface::class.java
        )
    }
    private val authorizedOkHttpClient by lazy { generateOkHttpClient() }

    private fun buildRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder().apply {
        baseUrl("http://www.mocky.io/")
        client(okHttpClient)
        addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
    }.build()

    private fun generateOkHttpClient(): OkHttpClient =
            OkHttpClient().newBuilder().apply {
                readTimeout(TIMEOUT, TimeUnit.SECONDS)
                connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                addInterceptor { chain ->
                    chain.proceed(
                            getRequest(
                                    chain.request()
                            )
                    )
                }
            }.build()

    @Throws(IOException::class)
    fun getRequest(request: Request): Request =
            request.newBuilder().build()

    interface EmployeeApiInterface {
        @GET("v2/5d565297300000680030a986")
        fun getEmployees(): retrofit2.Call<MutableList<Employee>>
    }
}