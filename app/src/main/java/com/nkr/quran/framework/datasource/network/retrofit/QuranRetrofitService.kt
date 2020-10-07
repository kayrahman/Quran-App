package com.nkr.quran.framework.datasource.network.retrofit

import com.nkr.quran.framework.datasource.network.model.ChapterNetworkEntity
import com.nkr.quran.framework.datasource.network.model.ChaptersNetworkEntity
import com.nkr.quran.framework.datasource.network.model.VersesNetworkEntity
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface QuranRetrofitService {

    @GET("chapters")
    suspend fun getChapters(): ChaptersNetworkEntity

    @GET("chapters/{chapter_id}/verses")
    suspend fun getVersesByChapterId(
        @Path("chapter_id") chapter_id:String,
        @Query("translations")translations:String,
        @Query("language")lang:String
    ):VersesNetworkEntity

}