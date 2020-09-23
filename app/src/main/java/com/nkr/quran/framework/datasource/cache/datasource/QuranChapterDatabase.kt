package com.nkr.quran.framework.datasource.cache.datasource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nkr.quran.framework.datasource.cache.model.ChapterCacheEntity


@Database(entities = [ChapterCacheEntity::class ], version = 1)
abstract class QuranChapterDatabase: RoomDatabase() {

    abstract fun chapterDao(): QuranChapterDao

    companion object {
        val DATABASE_NAME: String = "quran_db"


      /*  // For Singleton instantiation
        @Volatile
        private var instance: QuranChapterDatabase? = null

        fun getInstance(context: Context): QuranChapterDatabase {
            return instance ?: synchronized(this) {
                instance
                    ?: buildDatabase(
                        context
                    ).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): QuranChapterDatabase {
            return Room.databaseBuilder(context, QuranChapterDatabase::class.java, DATABASE_NAME).build()
        }
*/
    }
}


