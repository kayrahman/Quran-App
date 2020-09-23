package com.nkr.quran.business.interactors

import com.codingwithmitch.daggerhiltplayground.business.data.network.INetworkDataSource
import com.codingwithmitch.daggerhiltplayground.business.domain.state.DataState
import com.nkr.quran.business.data.cache.IChapterCacheDataSource
import com.nkr.quran.business.domain.models.Chapter
import com.nkr.quran.business.domain.models.Chapters
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class QuranInfo
    constructor(private val networkDataSource: INetworkDataSource,
                private val chapterCacheDataSource: IChapterCacheDataSource
                ) {

    suspend fun execute(): Flow<DataState<List<Chapter>>> = flow {
        emit(DataState.Loading)
        val chapters = networkDataSource.getQuranChapters()
        chapterCacheDataSource.insertAllChapters(chapters)
        val cachedChapter = chapterCacheDataSource.getAllChapters()
        emit(DataState.Success(cachedChapter))

    }
}