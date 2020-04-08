package com.wiseassblog.jetpacknotesmvvmkotlin.note.notelist.buildlogic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nkr.fashionita.repository.IProductRepository
import com.nkr.fashionita.ui.fragment.productListing.ProductListingViewModel
import kotlinx.coroutines.Dispatchers

class ProductListingViewModelFactory(
    private val productRepo: IProductRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return ProductListingViewModel(productRepo, Dispatchers.Main) as T
    }

}