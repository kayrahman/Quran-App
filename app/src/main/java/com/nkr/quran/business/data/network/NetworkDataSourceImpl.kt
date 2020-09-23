package com.codingwithmitch.daggerhiltplayground.business.data.network


import com.nkr.quran.business.domain.models.Chapter
import com.nkr.quran.business.domain.models.Chapters
import com.nkr.quran.framework.datasource.network.mappers.NetworkMapper
import com.nkr.quran.framework.datasource.network.model.ChapterNetworkEntity
import com.nkr.quran.framework.datasource.network.retrofit.QuranRetrofitService


class NetworkDataSourceImpl
constructor(
    private val quranRetrofitService: QuranRetrofitService,
    private val networkMapper: NetworkMapper
): INetworkDataSource{
    override suspend fun getQuranChapters(): List<Chapter> {

      //  return networkMapper.mapFromEntityList(quranRetrofitService.getChapters())
      //  return networkMapper.mapFromChapters( quranRetrofitService.getChapters())
        return networkMapper.mapChapterListFromChapters( quranRetrofitService.getChapters())

    }



}