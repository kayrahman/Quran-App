package com.nkr.fashionita.note.notelist.buildlogic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nkr.fashionita.repository.ICartRepository
import com.nkr.fashionita.ui.fragment.cart.CartViewModel
import kotlinx.coroutines.Dispatchers

class CartListViewModelFactory(
    private val cartRepo: ICartRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return CartViewModel(cartRepo, Dispatchers.Main) as T
    }

}