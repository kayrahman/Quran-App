package com.nkr.quran.framework.datasource.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class ChaptersNetworkEntity(
    @SerializedName("chapters") @Expose val chapters: ArrayList<ChapterNetworkEntity>
)


class TranslationEntity(
   // @SerializedName("id") @Expose val id: Int,
    @SerializedName("language_name") @Expose val language_name: String,
    @SerializedName("text") @Expose val text: String
   // @SerializedName("resource_name") @Expose val resource_name: String,
   // @SerializedName("resource_id") @Expose val resource_id: Int
)


class VersesNetworkEntity(
    @SerializedName("verses") @Expose val verses : ArrayList<VerseEntity>

)

data class VerseEntity(
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("verse_number") @Expose val verse_number: Int,
    @SerializedName("chapter_id") @Expose val chapter_id: Int,
    @SerializedName("juz_number") @Expose val juz_number: Int,
    @SerializedName("verses_count") @Expose val verses_count: Int,
    @SerializedName("verse_key") @Expose val verse_key: String,
    @SerializedName("text_madani") @Expose val text_madani: String,
    @SerializedName("text_simple") @Expose val text_simple: String,
    @SerializedName("page_number") @Expose val page_number: Int,
    @SerializedName("translations") @Expose val translations : ArrayList<TranslationEntity>
)


class ChapterNetworkEntity(
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("chapter_number") @Expose val chapter_number: Int,
    @SerializedName("bismillah_pre") @Expose val bismillah_pre: Boolean,
    @SerializedName("revelation_order") @Expose val revelation_order: Int,
    @SerializedName("revelation_place") @Expose val revelation_place: String,
    @SerializedName("name_complex") @Expose val name_complex: String,
    @SerializedName("name_arabic") @Expose val name_arabic: String,
    @SerializedName("name_simple") @Expose val name_simple: String,
    @SerializedName("verses_count") @Expose val verses_count: Int


)