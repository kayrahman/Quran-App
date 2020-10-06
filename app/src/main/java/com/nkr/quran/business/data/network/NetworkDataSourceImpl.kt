package com.codingwithmitch.daggerhiltplayground.business.data.network


import com.nkr.quran.business.domain.models.Chapter
import com.nkr.quran.business.domain.models.Chapters
import com.nkr.quran.framework.datasource.network.mappers.NetworkMapper
import com.nkr.quran.framework.datasource.network.model.ChapterNetworkEntity
import com.nkr.quran.framework.datasource.network.model.VersesNetworkEntity
import com.nkr.quran.framework.datasource.network.retrofit.QuranRetrofitService


class NetworkDataSourceImpl
constructor(
    private val quranRetrofitService: QuranRetrofitService,
    private val networkMapper: NetworkMapper
): INetworkDataSource{
    override suspend fun getQuranChapters(): List<Chapter> {
        return networkMapper.mapChapterListFromChapters( quranRetrofitService.getChapters())

    }

    override suspend fun getVersesByChapterNumber(chapter_num: String): VersesNetworkEntity {
       return quranRetrofitService.getVersesByChapterId(chapter_num)
    }


}