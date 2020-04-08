package com.nkr.fashionita.ui.adapter.searchResult


import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.nkr.fashionita.model.Product


class SearchResultItemGridListViewModel {
    private val note = MutableLiveData<Product>()

    fun bind(note: Product) {
        this.note.value = note

        Log.d("Item_vmodel",this.note.value.toString())

    }

    fun getImgUrl(): String? {

        Log.d("image_url",this.note.value?.thumbImageUrl.toString())

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