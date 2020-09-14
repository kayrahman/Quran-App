package com.nkr.quran.framework.datasource.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class ChaptersNetworkEntity(
    @SerializedName("chapters")
    @Expose
    val chapters: ArrayList<ChapterNetworkEntity>
)


class ChapterNetworkEntity(
    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("chapter_number")
    @Expose
    val chapter_number: Int,

    @SerializedName("bismillah_pre")
    @Expose
    val bismillah_pre: Boolean,

    @SerializedName("revelation_order")
    @Expose
    val revelation_order: Int,

    @SerializedName("revelation_place")
    @Expose
    val revelation_place: String,


    @SerializedName("name_complex")
    @Expose
    val name_complex: String,


    @SerializedName("name_arabic")
    @Expose
    val name_arabic: String,


    @SerializedName("name_simple")
    @Expose
    val name_simple: String,


    @SerializedName("verses_count")
    @Expose
    val verses_count: Int


)