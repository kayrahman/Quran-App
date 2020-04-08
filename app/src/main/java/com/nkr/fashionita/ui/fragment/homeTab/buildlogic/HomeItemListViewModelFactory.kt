package com.nkr.fashionita.note.notelist.buildlogic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nkr.fashionita.repository.IProductRepository
import com.nkr.fashionita.repository.IWishListRepository
import com.nkr.fashionita.ui.fragment.homeTab.HomeViewModel
import kotlinx.coroutines.Dispatchers

class HomeItemListViewModelFactory(
    private val productRepo: IProductRepository,
    private val wishRepo : IWishListRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return HomeViewModel(productRepo,wishRepo,Dispatchers.Main) as T
    }

}