package com.nkr.quran.business.data.cache

import com.nkr.quran.business.domain.models.Chapter
import com.nkr.quran.framework.datasource.cache.model.ChapterCacheEntity


interface IChapterCacheDataSource {

    suspend fun insertAllChapters(chapters:List<Chapter>)
    suspend fun getAllChapters():List<Chapter>
}