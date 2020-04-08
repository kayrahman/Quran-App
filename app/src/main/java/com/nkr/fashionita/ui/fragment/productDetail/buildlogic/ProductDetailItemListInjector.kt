package com.nkr.fashionita.note.notelist.buildlogic

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.FirebaseApp
import com.nkr.fashionita.model.RoomNoteDatabase
import com.nkr.fashionita.repository.CartRepoImpl
import com.nkr.fashionita.repository.ICartRepository
import com.nkr.fashionita.repository.IProductRepository
import com.nkr.fashionita.repository.ProductRepoImpl
import com.wiseassblog.jetpacknotesmvvmkotlin.note.notelist.buildlogic.ProductDetailViewModelFactory


class ProductDetailItemListInjector(application:Application): AndroidViewModel(application) {

    private fun getCartRepository(): ICartRepository {
        FirebaseApp.initializeApp(getApplication())
        return CartRepoImpl(
          //  local = RoomNoteDatabase.getInstance(getApplication()).roomNoteDao()
        )
    }


    private fun getNoteRepository(): IProductRepository {
        FirebaseApp.initializeApp(getApplication())
        return ProductRepoImpl(
            local = RoomNoteDatabase.getInstance(getApplication()).roomNoteDao()
        )
    }

    fun provideDetailItemViewModelFactory(): ProductDetailViewModelFactory =
        ProductDetailViewModelFactory(
            getCartRepository(),
            getNoteRepository()

        )
}