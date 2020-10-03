package com.nkr.quran.business.domain.models

import com.nkr.quran.framework.datasource.network.model.ChapterNetworkEntity


data class Chapters(val chapters: ArrayList<ChapterNetworkEntity>)


data class Chapter(val id:Int,
                   val chapter_number:Int,
                   val bismillah_pre:Boolean,
                   val revelation_order:Int,
                   val revelation_place:String,
                   val name_complex:String,
                   val name_arabic:String,
                   val name_simple:String,
                   val verses_count:Int
                   ){


    public fun revelationNverseCount():String{
        return "$revelation_place - $verses_count"
    }

}