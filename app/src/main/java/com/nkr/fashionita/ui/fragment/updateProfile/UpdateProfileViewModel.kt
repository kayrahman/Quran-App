package com.nkr.fashionita.ui.fragment.updateProfile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.nkr.fashionita.common.BaseViewModel
import com.nkr.fashionita.common.Result
import com.nkr.fashionita.model.App
import com.nkr.fashionita.model.User
import com.nkr.fashionita.repository.IUserRepository
import kotlinx.coroutines.launch
import java.io.File
import kotlin.coroutines.CoroutineContext

class UpdateProfileViewModel(val userRepo: IUserRepository,uiContext:CoroutineContext) : BaseViewModel<EditProfileEvent>(uiContext) {

    val username = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val userImg = MutableLiveData<String>()
    val firstName = MutableLiveData<String>()
    val lastName = MutableLiveData<String>()
    val city = MutableLiveData<String>()


    var fileAvatar = MutableLiveData<File>()


    override fun handleEvent(event: EditProfileEvent) {

        when(event){
            is EditProfileEvent.populateUserData -> {

                username.value = App.userInfo?.name
                userImg.value = App.userInfo?.thumb_image
                firstName.value = App.userInfo?.first_name.toString()
                lastName.value = App.userInfo?.last_name.toString()
                city.value = App.userInfo?.city.toString()

                Log.d("user_info", App.userInfo.toString())

            }

            is EditProfileEvent.updateUserInfo -> updateUserInfoInRemote()



        }

    }

    private fun updateUserInfoInRemote() = launch {

        apiCallStart()

        val user = User(username = username.value.toString(),first_name = firstName.value.toString(),
        last_name = lastName.value.toString(),city = city.value.toString()
            )

        val updateUserInfo = userRepo.updateUserInfo(user)
        when(updateUserInfo){
            is Result.Value -> {
                apiCallFinish()
                Log.d("user_info_update","Success")
            }
            is Result.Error -> {

                apiCallFinish()
                Log.d("user_info_update",updateUserInfo.error.toString())

            }
        }
    }


}
