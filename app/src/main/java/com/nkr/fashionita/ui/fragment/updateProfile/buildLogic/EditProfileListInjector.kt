package com.nkr.fashionita.ui.fragment.updateProfile.buildLogic

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.FirebaseApp
import com.nkr.fashionita.repository.*
import com.nkr.fashionita.ui.fragment.updateProfile.EditProfileViewModelFactory


class EditProfileListInjector(application:Application): AndroidViewModel(application) {



    private fun getUserRepository(): IUserRepository {
        FirebaseApp.initializeApp(getApplication())
        return FirebaseUserRepoImpl(

        )
    }

    fun provideEditProfileViewModelFactory(): EditProfileViewModelFactory =
        EditProfileViewModelFactory(
            getUserRepository()

        )
}