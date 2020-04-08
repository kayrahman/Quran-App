package com.wiseassblog.jetpacknotesmvvmkotlin.login.buildlogic

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.FirebaseApp
import com.nkr.fashionita.repository.FirebaseUserRepoImpl
import com.nkr.fashionita.repository.IUserRepository

class LoginInjector(application: Application): AndroidViewModel(application) {

    init {
        FirebaseApp.initializeApp(application)
    }

    private fun getUserRepository(): IUserRepository {
        return FirebaseUserRepoImpl()
    }

    fun provideUserViewModelFactory(): UserViewModelFactory =
        UserViewModelFactory(
            getUserRepository()
        )

}