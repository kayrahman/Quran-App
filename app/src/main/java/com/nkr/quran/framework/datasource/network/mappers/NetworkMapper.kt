
package com.nkr.quran.framework.datasource.network.mappers

import com.nkr.quran.business.common.EntityMapper
import com.nkr.quran.business.domain.models.Chapter
import com.nkr.quran.business.domain.models.Chapters
import com.nkr.quran.business.domain.models.Verse
import com.nkr.quran.framework.datasource.network.model.ChapterNetworkEntity
import com.nkr.quran.framework.datasource.network.model.ChaptersNetworkEntity
import com.nkr.quran.framework.datasource.network.model.VerseEntity
import javax.inject.Inject


class NetworkMapper
@Inject
constructor(): EntityMapper<ChapterNetworkEntity, Chapter> {
    override fun mapFromEntity(entity: ChapterNetworkEntity): Chapter {
        return Chapter(
            id = entity.id,
            chapter_number = entity.chapter_number,
            bismillah_pre = entity.bismillah_pre,
            revelation_order = entity.revelation_order,
            revelation_place = entity.revelation_place,
            name_complex = entity.name_complex,
            name_arabic = entity.name_arabic,
            name_simple = entity.name_simple,
            verses_count = entity.verses_count
        )
    }

    override fun mapToEntity(domainModel: Chapter): ChapterNetworkEntity {
        return ChapterNetworkEntity(
            id = domainModel.id,
            chapter_number = domainModel.chapter_number,
            bismillah_pre = domainModel.bismillah_pre,
            revelation_order = domainModel.revelation_order,
            revelation_place = domainModel.revelation_place,
            name_complex = domainModel.name_complex,
            name_arabic = domainModel.name_arabic,
            name_simple = domainModel.name_simple,
            verses_count = domainModel.verses_count
        )
    }



    fun mapChapterListFromChapters(entity: ChaptersNetworkEntity): List<Chapter>{
        return entity.chapters.map {
            mapFromEntity(it)
        }

    }

}



class QuranVersesMapper
    @Inject
    constructor()
    : EntityMapper<VerseEntity, Verse> {
    override fun mapFromEntity(entity: VerseEntity): Verse {
        return Verse(
            id = entity.id,
            verse_number = entity.verse_number,
            chapter_id = entity.chapter_id,
            juz_number = entity.juz_number,
            verses_count = entity.verses_count,
            verse_key = entity.verse_key,
            text_madani = entity.text_madani,
            text_simple = entity.text_simple,
            page_number = entity.page_number
        )
    }

    override fun mapToEntity(domainModel: Verse): VerseEntity {
        return VerseEntity(
            id = domainModel.id,
            verse_number = domainModel.verse_number,
            chapter_id = domainModel.chapter_id,
            juz_number = domainModel.juz_number,
            verses_count = domainModel.verses_count,
            verse_key = domainModel.verse_key,
            text_madani = domainModel.text_madani,
            text_simple = domainModel.text_simple,
            page_number = domainModel.page_number,
            translations = domainModel.
        )
    }
}



















