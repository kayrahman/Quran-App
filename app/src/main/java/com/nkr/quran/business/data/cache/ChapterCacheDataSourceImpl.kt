package com.nkr.quran.business.data.cache

import com.nkr.quran.business.domain.models.Chapter
import com.nkr.quran.framework.datasource.cache.datasource.QuranChapterDao
import com.nkr.quran.framework.datasource.cache.mappers.CacheMapper


class ChapterCacheDataSourceImpl
    constructor(private val quranChapterDao: QuranChapterDao,
                private val cacheMapper: CacheMapper
                ) : IChapterCacheDataSource {

    override suspend fun insertAllChapters(chapters: List<Chapter>) {
        return quranChapterDao.insertAllQuranChapters( chapters.map {
            cacheMapper.mapToEntity(it)
        })
    }

    override suspend fun getAllChapters(): List<Chapter> {
     return cacheMapper.mapFromEntityList(quranChapterDao.getAllChapters())
    }
}