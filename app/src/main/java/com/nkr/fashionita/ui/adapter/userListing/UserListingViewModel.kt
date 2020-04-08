package com.nkr.fashionita.ui.adapter.userListing


import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.nkr.fashionita.model.App
import com.nkr.fashionita.model.Product
import java.util.*


class UserListingViewModel {
    private val note = MutableLiveData<Product>()


    fun bind(note: Product) {
        this.note.value = note

        Log.d("image_one", note.imageUrls?.get(0).toString())

    }

    fun getImgUrl(): String? {

        Log.d("image_url",this.note.value?.thumbImageUrl.toString())

     //  return StorageUtil.pathToReference(note.value?.imageUrl.toString())

       // return note.value?.thumbImageUrl
        return note.value?.imageUrls!![0]

    }

    fun getTitle(): String? {
        return note.value?.title
    }
    fun getPrice():String?{
        return note.value?.price
    }

    fun setPostImage(id: String, name: String){
    }

    fun getDate() : String {
        val posted_time : Long = note.value?.timestamp?.time!!
        val time = App.getTimeAgo(posted_time)

        return time.toString()
    }

    fun getWishListItemColor() : Boolean?{

        Log.d("wishlist_item_color",note.value?.isWishList.toString())

       return note.value?.isWishList


    }
}