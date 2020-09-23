package com.nkr.quran.di

import com.codingwithmitch.daggerhiltplayground.business.data.network.INetworkDataSource
import com.nkr.quran.business.data.cache.IChapterCacheDataSource
import com.nkr.quran.business.interactors.QuranInfo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object InteractorsModule {

    @Singleton
    @Provides
    fun provideQuranInfo(
        networkDataSource: INetworkDataSource,
        cacheDataSource: IChapterCacheDataSource

    ): QuranInfo{
        return QuranInfo(networkDataSource,cacheDataSource)
    }
}














