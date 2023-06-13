package com.maha.NewsAppMvvm.api

import com.maha.NewsAppMvvm.util.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//singleton class enable to make request anywhere in code
class RetroffitInstance {
    companion object{
        //lazy -- only inlitaize once
        private val retrofit by lazy {
            //log of request and response of retrofit
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder().addInterceptor(logging).build()

            //pass the client to retrofit instance
            //converter factory determine response to interpert and convert it
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).
            client(client).build()

        }
        val api by lazy {
            retrofit.create(NewsApi::class.java)
        }
    }

}