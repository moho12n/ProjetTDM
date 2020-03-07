package com.medecin.project


import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    var gson = GsonBuilder().setLenient().create()
    val endpoint: Endpoint by lazy {

        Retrofit.Builder().baseUrl("https://b9f4cd1c.ngrok.io/")

            .addConverterFactory(GsonConverterFactory.create(gson)).build()
            .create(Endpoint::class.java)
    }
}