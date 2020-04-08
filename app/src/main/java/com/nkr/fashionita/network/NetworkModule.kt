package com.nkr.fashionita.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {
    private var retrofit: Retrofit? = null

    /**
     * Generate global static Retrofit
     *
     * @return
     */
    private val apiClient: Retrofit?
        get() {
                retrofit = Retrofit.Builder()
                       // .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                        .build()
            return retrofit
        }

    /**
     * Get the interface class for API calling
     *
     * @return
     */
    val networkApi: NetworkApi
        get() {
            val r = apiClient
            return r?.create(NetworkApi::class.java)!!
        }



}
