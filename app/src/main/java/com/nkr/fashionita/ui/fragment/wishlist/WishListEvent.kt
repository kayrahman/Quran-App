package com.nkr.fashionita.ui.fragment.wishlist

sealed class WishListEvent {
    data class OnNoteItemClick(val position: Int) : WishListEvent()
    data class OnWishIconClick(val uid:String,val isAlreadySaved:Boolean,val position:Int) : WishListEvent()
    object OnNewNoteClick : WishListEvent()
    object OnStart : WishListEvent()
    object OnBannerItemStart:WishListEvent()
}