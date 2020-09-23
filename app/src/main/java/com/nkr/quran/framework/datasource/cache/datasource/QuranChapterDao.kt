package com.nkr.quran.framework.datasource.cache.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nkr.quran.business.domain.models.Chapter
import com.nkr.quran.framework.datasource.cache.model.ChapterCacheEntity


@Dao
interface QuranChapterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllQuranChapters(chapter_list:List<ChapterCacheEntity>)

    @Query("SELECT * FROM quran_chapter")
    suspend fun getAllChapters():List<ChapterCacheEntity>

}