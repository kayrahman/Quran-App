package com.nkr.quran.framework.datasource.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "quran_chapter")
class ChapterCacheEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id:Int,

    @ColumnInfo(name = "chapter_number")
    var chapter_number:Int,

    @ColumnInfo(name = "bismillah_pre")
    var bismillah_pre:Boolean,

    @ColumnInfo(name = "revelation_order")
    var revelation_order:Int,

    @ColumnInfo(name = "revelation_place")
    var revelation_place:String,

    @ColumnInfo(name = "name_complex")
    var name_complex:String,

    @ColumnInfo(name = "name_arabic")
    var name_arabic:String,

    @ColumnInfo(name = "name_simple")
    var name_simple:String,

    @ColumnInfo(name = "verses_count")
    var verses_count:Int

    )

