package com.nkr.fashionita.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "notes",
    indices = [Index("creation_date")]
)
data class RoomNote(
    @PrimaryKey
    @ColumnInfo(name = "creation_date")
    val creationDate: String,

    val uid:String,

    @ColumnInfo(name = "contents")
    val contents: String,

    @ColumnInfo(name = "up_votes")
    val upVotes: Int,

    @ColumnInfo(name = "image_url")
    val imageUrl: String,

    @ColumnInfo(name = "creator_id")
    val creatorId: String,

    @ColumnInfo(name = "product_name")
val productName: String,



    @ColumnInfo(name = "product_price")
val productPrice: String

)
