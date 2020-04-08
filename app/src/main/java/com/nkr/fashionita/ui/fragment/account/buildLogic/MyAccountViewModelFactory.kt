package com.wiseassblog.jetpacknotesmvvmkotlin.note.notelist.buildlogic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nkr.fashionita.repository.ICartRepository
import com.nkr.fashionita.repository.IMyAccountRepository
import com.nkr.fashionita.repository.IProductRepository
import com.nkr.fashionita.ui.fragment.account.MyAccountViewModel
import com.nkr.fashionita.ui.fragment.productDetail.ProductDetailViewModel
import kotlinx.coroutines.Dispatchers

class MyAccountViewModelFactory(
    private val myAccountRepo: IMyAccountRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return MyAccountViewModel(myAccountRepo,Dispatchers.Main) as T
    }

}