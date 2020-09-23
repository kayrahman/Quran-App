package com.nkr.quran.di

import android.content.Context
import androidx.room.Room
import com.nkr.quran.business.common.EntityMapper
import com.nkr.quran.business.data.cache.ChapterCacheDataSourceImpl
import com.nkr.quran.business.data.cache.IChapterCacheDataSource
import com.nkr.quran.business.domain.models.Chapter
import com.nkr.quran.framework.datasource.cache.QuranChapterDaoService
import com.nkr.quran.framework.datasource.cache.QuranChapterDaoServiceImpl
import com.nkr.quran.framework.datasource.cache.datasource.QuranChapterDao
import com.nkr.quran.framework.datasource.cache.datasource.QuranChapterDatabase
import com.nkr.quran.framework.datasource.cache.mappers.CacheMapper
import com.nkr.quran.framework.datasource.cache.model.ChapterCacheEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object CacheModule {

    @Singleton
    @Provides
    fun provideCacheMapper(): EntityMapper<ChapterCacheEntity, Chapter> {
        return CacheMapper()
    }

    @Singleton
    @Provides
    fun provideQuranDb(@ApplicationContext context: Context): QuranChapterDatabase {
        return Room
            .databaseBuilder(
                context,
                QuranChapterDatabase::class.java,
                QuranChapterDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideChapterDAO(quranDatabase: QuranChapterDatabase): QuranChapterDao {
        return quranDatabase.chapterDao()
    }

    @Singleton
    @Provides
    fun provideChapterDaoService(
        chapterDao: QuranChapterDao
    ):QuranChapterDaoService{
        return QuranChapterDaoServiceImpl(chapterDao)
    }


    @Singleton
    @Provides
    fun provideCacheDataSource(
        chapterDaoService: QuranChapterDao,
        cacheMapper: CacheMapper
    ): IChapterCacheDataSource{
        return ChapterCacheDataSourceImpl(chapterDaoService, cacheMapper)
    }

}