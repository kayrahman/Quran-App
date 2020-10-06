package com.nkr.quran.framework.datasource.network

import com.nkr.quran.framework.datasource.network.model.*


interface IQuranApiService {

   // suspend fun getChapters(): List<ChapterNetworkEntity>
    suspend fun getChapters(): ChaptersNetworkEntity
    suspend fun getVersesByChapterNumber(chapter_num : String) : VersesNetworkEntity


}