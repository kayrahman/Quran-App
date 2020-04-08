package com.nkr.fashionita.ui.fragment.homeTab

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.FirebaseApp
import com.nkr.fashionita.model.RoomNoteDatabase
import com.nkr.fashionita.note.notelist.buildlogic.HomeItemListViewModelFactory
import com.nkr.fashionita.repository.IProductRepository
import com.nkr.fashionita.repository.IWishListRepository
import com.nkr.fashionita.repository.ProductRepoImpl
import com.nkr.fashionita.repository.WishRepoImpl


class HomeItemListInjector(application:Application): AndroidViewModel(application) {

    private fun getNoteRepository(): IProductRepository {
        FirebaseApp.initializeApp(getApplication())
        return ProductRepoImpl(
            local = RoomNoteDatabase.getInstance(getApplication()).roomNoteDao()
        )
    }


    private fun getWishRepository(): IWishListRepository {
        FirebaseApp.initializeApp(getApplication())
        return WishRepoImpl(
        )
    }



    fun provideNoteListViewModelFactory(): HomeItemListViewModelFactory =
        HomeItemListViewModelFactory(
            getNoteRepository(),
            getWishRepository()
        )
}