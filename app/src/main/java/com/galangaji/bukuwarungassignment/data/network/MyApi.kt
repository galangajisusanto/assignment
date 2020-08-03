package com.galangaji.bukuwarungassignment.data.network

import com.galangaji.bukuwarungassignment.data.network.response.UserResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface MyApi {


    @GET("users")
    suspend fun getUsers(): Response<UserResponse>

    companion object {
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ): MyApi {

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()

            return Retrofit.Builder().client(okHttpClient)
                .baseUrl("https://reqres.in/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(MyApi::class.java)
        }
    }
}