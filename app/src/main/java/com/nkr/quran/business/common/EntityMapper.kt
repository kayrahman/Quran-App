package com.nkr.quran.business.common

interface EntityMapper <Entity, DomainModel>{

    fun mapFromEntity(entity: Entity): DomainModel

    fun mapToEntity(domainModel: DomainModel): Entity
}