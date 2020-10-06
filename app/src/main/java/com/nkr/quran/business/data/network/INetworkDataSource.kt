package com.codingwithmitch.daggerhiltplayground.business.data.network

import com.nkr.quran.business.domain.models.Chapter
import com.nkr.quran.business.domain.models.Chapters
import com.nkr.quran.framework.datasource.network.model.ChapterNetworkEntity
import com.nkr.quran.framework.datasource.network.model.VersesNetworkEntity

interface INetworkDataSource {

    suspend fun getQuranChapters():List<Chapter>
    suspend fun getVersesByChapterNumber(chapter_num : String) : VersesNetworkEntity
}