package com.wiseassblog.jetpacknotesmvvmkotlin.note.notelist.buildlogic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nkr.fashionita.repository.IProductRepository
import com.nkr.fashionita.ui.fragment.categoryDetail.CategoryDetailViewModel
import kotlinx.coroutines.Dispatchers

class CategoryDetailViewModelFactory(
    private val productRepo: IProductRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return CategoryDetailViewModel(productRepo, Dispatchers.Main) as T
    }

}