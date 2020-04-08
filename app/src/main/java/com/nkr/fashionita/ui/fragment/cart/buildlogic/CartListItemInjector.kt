package com.nkr.fashionita.note.notelist.buildlogic

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.FirebaseApp
import com.nkr.fashionita.model.RoomNoteDatabase
import com.nkr.fashionita.repository.*


class CartListItemInjector(application:Application): AndroidViewModel(application) {

    private fun getCartListRepository(): ICartRepository {
        FirebaseApp.initializeApp(getApplication())
        return CartRepoImpl(

        )
    }

    fun provideCartListItemViewModelFactory(): CartListViewModelFactory =
        CartListViewModelFactory(
            getCartListRepository()
        )
}