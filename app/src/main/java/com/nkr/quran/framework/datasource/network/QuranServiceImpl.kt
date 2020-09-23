package com.nkr.quran.framework.datasource.network

import android.util.Log
import com.nkr.quran.framework.datasource.network.model.ChapterNetworkEntity
import com.nkr.quran.framework.datasource.network.model.ChaptersNetworkEntity
import com.nkr.quran.framework.datasource.network.retrofit.QuranRetrofitService
import timber.log.Timber

class QuranServiceImpl
constructor(
    private val quranApiSerice: QuranRetrofitService
): IQuranApiService {
    override suspend fun getChapters(): ChaptersNetworkEntity {

        val chapters = quranApiSerice.getChapters()
        Log.d("chapters",chapters.toString())

        return quranApiSerice.getChapters()
    }




}