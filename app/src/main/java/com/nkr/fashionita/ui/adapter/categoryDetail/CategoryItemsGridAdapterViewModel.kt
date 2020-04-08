package com.nkr.fashionita.ui.adapter.categoryDetail


import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.nkr.fashionita.model.Product


class CategoryItemsGridAdapterViewModel {
    private val note = MutableLiveData<Product>()

    fun bind(note: Product) {
        this.note.value = note

        Log.d("Item_vmodel",this.note.value.toString())

    }

    fun getImgUrl(): String? {

        return note.value?.imageUrls!![0]
    }

    fun getTitle(): String? {
        return note.value?.title
    }
    fun getPrice():String?{
        return note.value?.price + "Tk"
    }

    fun setPostImage(id: String, name: String){
    }
}