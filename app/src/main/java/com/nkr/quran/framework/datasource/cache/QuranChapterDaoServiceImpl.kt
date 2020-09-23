package com.nkr.quran.framework.datasource.cache

import com.nkr.quran.framework.datasource.cache.datasource.QuranChapterDao
import com.nkr.quran.framework.datasource.cache.model.ChapterCacheEntity


class QuranChapterDaoServiceImpl(
    val chapterDao : QuranChapterDao):QuranChapterDaoService

{
    override suspend fun insertAllChapters(chapters: List<ChapterCacheEntity>) {
        return chapterDao.insertAllQuranChapters(chapters)
    }

    override suspend fun getAllChapters(): List<ChapterCacheEntity> {
            return chapterDao.getAllChapters()
    }

}