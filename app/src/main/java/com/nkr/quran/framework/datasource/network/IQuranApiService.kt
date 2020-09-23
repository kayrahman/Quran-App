package com.nkr.quran.framework.datasource.network

import com.nkr.quran.framework.datasource.network.model.ChapterNetworkEntity
import com.nkr.quran.framework.datasource.network.model.ChaptersNetworkEntity


interface IQuranApiService {

   // suspend fun getChapters(): List<ChapterNetworkEntity>
    suspend fun getChapters(): ChaptersNetworkEntity



}