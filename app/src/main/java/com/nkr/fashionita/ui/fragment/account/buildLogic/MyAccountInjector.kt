package com.nkr.fashionita.note.notelist.buildlogic

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.FirebaseApp
import com.nkr.fashionita.model.RoomNoteDatabase
import com.nkr.fashionita.repository.*
import com.wiseassblog.jetpacknotesmvvmkotlin.note.notelist.buildlogic.MyAccountViewModelFactory
import com.wiseassblog.jetpacknotesmvvmkotlin.note.notelist.buildlogic.ProductDetailViewModelFactory


class MyAccountInjector(application:Application): AndroidViewModel(application) {

    private fun getMyAccountRepository(): IMyAccountRepository {
        FirebaseApp.initializeApp(getApplication())
        return MyAccountRepoImpl()
    }



    fun provideMyAccountViewModelFactory(): MyAccountViewModelFactory =
        MyAccountViewModelFactory(
            getMyAccountRepository()
        )


}