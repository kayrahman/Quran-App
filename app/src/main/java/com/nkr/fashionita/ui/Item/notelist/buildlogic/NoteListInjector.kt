package com.wiseassblog.jetpacknotesmvvmkotlin.note.notelist.buildlogic

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.FirebaseApp
import com.nkr.fashionita.model.RoomNoteDatabase
import com.nkr.fashionita.repository.CartRepoImpl
import com.nkr.fashionita.repository.ICartRepository
import com.nkr.fashionita.repository.IProductRepository
import com.nkr.fashionita.repository.ProductRepoImpl


class NoteListInjector(application:Application): AndroidViewModel(application) {

    private fun getCartRepository(): ICartRepository {
        FirebaseApp.initializeApp(getApplication())
        return CartRepoImpl(
           // local = RoomNoteDatabase.getInstance(getApplication()).roomNoteDao()
        )
    }

    private fun getNoteRepository(): IProductRepository {
        FirebaseApp.initializeApp(getApplication())
        return ProductRepoImpl(
             local = RoomNoteDatabase.getInstance(getApplication()).roomNoteDao()
        )
    }


    fun provideNoteListViewModelFactory(): ProductDetailViewModelFactory =
        ProductDetailViewModelFactory(
            getCartRepository(),
            getNoteRepository()

        )
}