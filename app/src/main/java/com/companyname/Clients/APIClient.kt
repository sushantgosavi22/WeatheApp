package com.companyname.Clients

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIClient {

    companion object {
        val appId: String = "3d3c6bade786b1e6849df25c5b7427b2"
        val baseURL: String = "https://api.openweathermap.org/data/2.5/"
        var retofit: Retrofit? = null
        val client: Retrofit
            get() {
                if (retofit == null) {
                    val interceptor = HttpLoggingInterceptor()
                    interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }
                    val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
                    retofit = Retrofit.Builder()
                            .baseUrl(baseURL)
                            .client(client)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                }
                return retofit!!
            }
    }
}