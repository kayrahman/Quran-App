package com.nkr.fashionita.ui.adapter.categoryDetail


import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.nkr.fashionita.model.CategoryItem
import com.nkr.fashionita.model.Product
import com.nkr.fashionita.model.SubCategoryItem


class SubCategoryAdapterViewModel {
    private val note = MutableLiveData<SubCategoryItem>()

    fun bind(note: SubCategoryItem) {
        this.note.value = note

        Log.d("Item_vmodel",this.note.value.toString())

    }

    fun getImgUrl(): String? {

        Log.d("image_url",this.note.value?.img_url.toString())

        return note.value?.img_url
    }

    fun getTitle(): String? {
        return note.value?.name
    }

}