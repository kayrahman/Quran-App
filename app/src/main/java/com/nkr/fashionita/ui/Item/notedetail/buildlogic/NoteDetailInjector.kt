package com.nkr.fashionita.ui.Item.notedetail.buildlogic

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.FirebaseApp
import com.nkr.fashionita.model.RoomNoteDatabase
import com.nkr.fashionita.repository.IProductRepository
import com.nkr.fashionita.repository.ProductRepoImpl

class NoteDetailInjector(application: Application): AndroidViewModel(application) {

    private fun getNoteRepository(): IProductRepository {
        FirebaseApp.initializeApp(getApplication())
        return ProductRepoImpl(
            local = RoomNoteDatabase.getInstance(getApplication()).roomNoteDao()
        )
    }

    fun provideNoteViewModelFactory(): NoteViewModelFactory =
        NoteViewModelFactory(
            getNoteRepository()
        )

}