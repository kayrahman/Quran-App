package com.nkr.fashionita.model

data class FirebaseCategoryItem(
    val uid: String? = "",
    val category_name: String? = "",
    val img_url: String? = "",
    val sub_categories: ArrayList<String>? = arrayListOf()
)