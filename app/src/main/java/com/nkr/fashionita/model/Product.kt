package com.nkr.fashionita.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.parcel.Parcelize
import java.util.*
import kotlin.collections.ArrayList

@Parcelize
data class Product(
    val uid: String,
    val categoryTitle:String,
    val creationDate: String,
    val thumbImageUrl: String,
    val imageUrls: ArrayList<String>?,
    val creator: String,
    val title: String,
    val price: String,
    val size:String,
    val brand:String,
    val condition:String,
    val dealMethod:String,
    val description:String,
    var isWishList:Boolean,
    val timestamp: Date

) : Parcelable

