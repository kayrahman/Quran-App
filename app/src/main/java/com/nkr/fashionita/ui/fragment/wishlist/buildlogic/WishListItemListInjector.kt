package com.nkr.fashionita.note.notelist.buildlogic

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.FirebaseApp
import com.nkr.fashionita.repository.IWishListRepository
import com.nkr.fashionita.repository.WishRepoImpl


class WishListItemListInjector(application:Application): AndroidViewModel(application) {

    private fun getWishListRepository(): IWishListRepository {
        FirebaseApp.initializeApp(getApplication())
        return WishRepoImpl(

        )
    }

    fun provideWishListItemViewModelFactory(): WishListModelFactory =
        WishListModelFactory(
            getWishListRepository()
        )
}