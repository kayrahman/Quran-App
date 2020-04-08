package com.nkr.fashionita.ui.fragment.updateProfile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nkr.fashionita.repository.IUserRepository
import kotlinx.coroutines.Dispatchers

class EditProfileViewModelFactory(
    private val userRepo: IUserRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return UpdateProfileViewModel(userRepo,Dispatchers.Main) as T
    }

}