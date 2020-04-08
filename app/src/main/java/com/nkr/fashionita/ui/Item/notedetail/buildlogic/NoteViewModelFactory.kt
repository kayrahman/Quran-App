package com.nkr.fashionita.ui.Item.notedetail.buildlogic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nkr.fashionita.repository.IProductRepository
import com.nkr.fashionita.ui.Item.NoteViewModel
import kotlinx.coroutines.Dispatchers

class NoteViewModelFactory(
    private val productRepo: IProductRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return NoteViewModel(productRepo, Dispatchers.Main) as T
    }

}