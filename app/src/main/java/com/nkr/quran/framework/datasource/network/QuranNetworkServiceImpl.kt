package com.nkr.quran.framework.datasource.network

import android.util.Log
import com.nkr.quran.framework.datasource.network.model.ChapterNetworkEntity
import com.nkr.quran.framework.datasource.network.model.ChaptersNetworkEntity
import com.nkr.quran.framework.datasource.network.model.VersesNetworkEntity
import com.nkr.quran.framework.datasource.network.retrofit.QuranRetrofitService
import timber.log.Timber

class QuranNetworkServiceImpl
constructor(
    private val quranApiSerice: QuranRetrofitService
): IQuranApiService {

    override suspend fun getChapters(): ChaptersNetworkEntity {
        return quranApiSerice.getChapters()
    }

    override suspend fun getVersesByChapterNumber(chapter_num:String): VersesNetworkEntity {
        return quranApiSerice.getVersesByChapterId(chapter_num)
    }


}