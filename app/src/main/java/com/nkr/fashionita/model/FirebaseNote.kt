package com.nkr.fashionita.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp
import java.util.*
import kotlin.collections.ArrayList


data class FirebaseProduct(
        val uid: String?="",
        val categoryTitle:String?="",
        val creationDate: String?="",
        val thumbImageUrl:String?="",
        val imageUrls: ArrayList<String>?= arrayListOf(),
        val creator: String?="",
        val title: String?="",
        val price: String?="",
        val size:String?="",
        val brand:String?="",
        val condition:String?="",
        val dealMethod:String?="",
        val description:String?="",
        val timestamp: Date?= null

)