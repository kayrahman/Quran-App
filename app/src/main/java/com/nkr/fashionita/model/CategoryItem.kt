package com.nkr.fashionita.model

import android.os.Parcelable

data class CategoryItem(
    var uid: String,
    val category_name: String,
    val img_url: String,
    val sub_categories:ArrayList<String>?
)