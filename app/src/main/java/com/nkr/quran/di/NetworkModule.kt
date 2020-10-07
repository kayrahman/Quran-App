package com.nkr.quran.di

import com.codingwithmitch.daggerhiltplayground.business.data.network.INetworkDataSource
import com.codingwithmitch.daggerhiltplayground.business.data.network.NetworkDataSourceImpl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nkr.quran.business.common.EntityMapper
import com.nkr.quran.business.domain.models.Chapter
import com.nkr.quran.framework.datasource.network.IQuranApiService
import com.nkr.quran.framework.datasource.network.QuranNetworkServiceImpl
import com.nkr.quran.framework.datasource.network.mappers.NetworkMapper
import com.nkr.quran.framework.datasource.network.mappers.QuranVersesMapper
import com.nkr.quran.framework.datasource.network.model.ChapterNetworkEntity
import com.nkr.quran.framework.datasource.network.retrofit.QuranRetrofitService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {


    @Singleton
    @Provides
    fun provideNetworkMapper(): EntityMapper<ChapterNetworkEntity, Chapter> {
        return NetworkMapper()
    }

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson:  Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl("http://api.quran.com:3000/api/v3/")
            .addConverterFactory(GsonConverterFactory.create(gson))
    }


    @Singleton
    @Provides
    fun provideQuranApiService(retrofit: Retrofit.Builder): QuranRetrofitService {
        return retrofit
            .build()
            .create(QuranRetrofitService::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofitService(
        quranRetrofitService: QuranRetrofitService
    ): IQuranApiService{
        return QuranNetworkServiceImpl(quranRetrofitService)
    }


    @Singleton
    @Provides
    fun provideNetworkDataSource(
        quranRetrofitService: QuranRetrofitService,
        networkMapper: NetworkMapper,
        versesMapper:QuranVersesMapper
    ): INetworkDataSource =
         NetworkDataSourceImpl(quranRetrofitService, networkMapper,versesMapper)


}