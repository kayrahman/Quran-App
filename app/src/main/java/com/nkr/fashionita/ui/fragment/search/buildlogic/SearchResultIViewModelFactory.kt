package com.wiseassblog.jetpacknotesmvvmkotlin.note.notelist.buildlogic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nkr.fashionita.repository.IProductRepository
import com.nkr.fashionita.ui.fragment.search.SearchResultViewModel
import kotlinx.coroutines.Dispatchers

class SearchResultIViewModelFactory(
    private val productRepo: IProductRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return SearchResultViewModel(
            productRepo,
            Dispatchers.Main
        ) as T
    }

}