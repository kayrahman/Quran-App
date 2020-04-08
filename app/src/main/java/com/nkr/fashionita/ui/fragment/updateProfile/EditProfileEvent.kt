package com.nkr.fashionita.ui.fragment.updateProfile

sealed class EditProfileEvent {

    object populateUserData : EditProfileEvent()
    object fetchUserInfo: EditProfileEvent()
    object updateUserInfo : EditProfileEvent()

}