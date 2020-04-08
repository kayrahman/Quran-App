package com.wiseassblog.jetpacknotesmvvmkotlin.login.buildlogic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nkr.fashionita.ui.login.UserViewModel
import com.nkr.fashionita.repository.IUserRepository

import kotlinx.coroutines.Dispatchers

class UserViewModelFactory(
    private val userRepo: IUserRepository
): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return UserViewModel(userRepo, Dispatchers.Main) as T
    }

}