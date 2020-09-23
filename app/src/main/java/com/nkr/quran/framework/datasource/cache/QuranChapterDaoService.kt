package com.nkr.quran.framework.datasource.cache

import com.nkr.quran.business.domain.models.Chapters
import com.nkr.quran.framework.datasource.cache.model.ChapterCacheEntity


interface QuranChapterDaoService {

    suspend fun insertAllChapters(chapters:List<ChapterCacheEntity>)
    suspend fun getAllChapters():List<ChapterCacheEntity>
}