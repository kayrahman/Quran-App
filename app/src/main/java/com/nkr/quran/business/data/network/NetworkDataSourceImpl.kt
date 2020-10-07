package com.codingwithmitch.daggerhiltplayground.business.data.network


import android.util.Log
import com.nkr.quran.business.domain.models.Chapter
import com.nkr.quran.business.domain.models.Chapters
import com.nkr.quran.business.domain.models.Verse
import com.nkr.quran.framework.datasource.network.mappers.NetworkMapper
import com.nkr.quran.framework.datasource.network.mappers.QuranVersesMapper
import com.nkr.quran.framework.datasource.network.model.ChapterNetworkEntity
import com.nkr.quran.framework.datasource.network.model.VersesNetworkEntity
import com.nkr.quran.framework.datasource.network.retrofit.QuranRetrofitService


class NetworkDataSourceImpl
constructor(
    private val quranRetrofitService: QuranRetrofitService,
    private val networkMapper: NetworkMapper,
    private val versesMapper: QuranVersesMapper
): INetworkDataSource{
    override suspend fun getQuranChapters(): List<Chapter> {
        return networkMapper.mapChapterListFromChapters( quranRetrofitService.getChapters())

    }

    override suspend fun getVersesByChapterNumber(chapter_num: String,translation:String,lang:String): List<Verse> {
        val verseNetworkEntity = quranRetrofitService.getVersesByChapterId(chapter_num,translation,lang)

        return verseNetworkEntity.verses.map {
            versesMapper.mapFromEntity(it)
        }

    }


}