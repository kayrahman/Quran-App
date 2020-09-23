package com.codingwithmitch.daggerhiltplayground.business.data.network

import com.nkr.quran.business.domain.models.Chapter
import com.nkr.quran.business.domain.models.Chapters
import com.nkr.quran.framework.datasource.network.model.ChapterNetworkEntity

interface INetworkDataSource {

    suspend fun getQuranChapters():List<Chapter>
}