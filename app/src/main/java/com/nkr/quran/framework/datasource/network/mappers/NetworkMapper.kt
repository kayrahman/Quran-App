
package com.nkr.quran.framework.datasource.network.mappers

import com.nkr.quran.business.common.EntityMapper
import com.nkr.quran.business.domain.models.Chapter
import com.nkr.quran.business.domain.models.Chapters
import com.nkr.quran.framework.datasource.network.model.ChapterNetworkEntity
import com.nkr.quran.framework.datasource.network.model.ChaptersNetworkEntity
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





















