package com.nkr.quran.business.domain.models

import android.os.Parcelable
import com.nkr.quran.framework.datasource.network.model.ChapterNetworkEntity
import kotlinx.android.parcel.Parcelize


data class Chapters(val chapters: ArrayList<ChapterNetworkEntity>)


@Parcelize
data class Chapter(val id:Int,
                   val chapter_number:Int,
                   val bismillah_pre:Boolean,
                   val revelation_order:Int,
                   val revelation_place:String,
                   val name_complex:String,
                   val name_arabic:String,
                   val name_simple:String,
                   val verses_count:Int
                   ):Parcelable{


    var surahNum : String
    get() = id.toString()
    set(value){}

    var surahVerseNplace : String
        get() = "$revelation_place - $verses_count verses"
        set(value) {}



}