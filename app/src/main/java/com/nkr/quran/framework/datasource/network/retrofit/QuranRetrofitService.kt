package com.nkr.quran.framework.datasource.network.retrofit

import com.nkr.quran.framework.datasource.network.model.ChapterNetworkEntity
import com.nkr.quran.framework.datasource.network.model.ChaptersNetworkEntity
import retrofit2.http.GET

interface QuranRetrofitService {

    @GET("chapters")
    suspend fun getChapters(): ChaptersNetworkEntity


}