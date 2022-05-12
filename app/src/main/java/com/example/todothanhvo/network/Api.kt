package com.example.todothanhvo.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object Api {
    private const val TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjo3MDksImV4cCI6MTY4MjQyNTM3M30.VeoSanqLgUoL-X5G5durNaMw5ks668jem08JG_d1nsg"

    private val retrofit by lazy {
        // client HTTP
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor { chain ->
                // intercepteur qui ajoute le `header` d'authentification avec votre token:
                val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $TOKEN")
                    .build()
                chain.proceed(newRequest)


            }

            .build()


    // transforme le JSON en objets kotlin et inversement
    val jsonSerializer = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    // instance retrofit pour implémenter les webServices:
    Retrofit.Builder()
    .baseUrl("https://android-tasks-api.herokuapp.com/api/")
    .client(okHttpClient)
    .addConverterFactory(jsonSerializer.asConverterFactory("application/json".toMediaType()))
    .build()
}
    val userWebService : UserWebService by lazy {
        retrofit.create(UserWebService::class.java)
    }

    val userTaskWebService : TaskWebService by lazy {
        retrofit.create(TaskWebService::class.java)
    }
}