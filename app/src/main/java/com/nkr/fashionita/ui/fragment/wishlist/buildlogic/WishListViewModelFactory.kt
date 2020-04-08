package com.nkr.fashionita.note.notelist.buildlogic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nkr.fashionita.repository.IWishListRepository
import com.nkr.fashionita.ui.fragment.wishlist.WishlistViewModel
import kotlinx.coroutines.Dispatchers

class WishListModelFactory(
    private val wishRepo: IWishListRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return WishlistViewModel(wishRepo, Dispatchers.Main) as T
    }

}