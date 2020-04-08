package com.nkr.fashionita.ui.adapter.wishlist


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nkr.fashionita.model.Product


class WishListAdapterViewModel {
    private val wishListItem = MutableLiveData<Product>()

    fun bind(product: Product) {
        this.wishListItem.value = product

        Log.d("Item_vmodel",this.wishListItem.value.toString())

    }

    fun getImgUrl(): String? {

        Log.d("image_url",this.wishListItem.value?.thumbImageUrl.toString())

      //  return note.value?.thumbImageUrl

        return wishListItem.value?.imageUrls!![0]
    }

    fun getTitle(): String? {
        return wishListItem.value?.title
    }
    fun getPrice():String?{
        return wishListItem.value?.price + "Tk"
    }



    fun getWishListItemColor() : Boolean?{

        Log.d("wishlist_item_color",wishListItem.value?.isWishList.toString())

      return wishListItem.value?.isWishList

    }

}