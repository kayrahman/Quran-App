package com.nkr.fashionita.ui.login

import com.nkr.fashionita.model.LoginResult

sealed class LoginEvent<out T> {
    object OnAuthButtonClick : LoginEvent<Nothing>()
    object OnStart : LoginEvent<Nothing>()
    object OnSetUserDataFirstTime : LoginEvent<LoginResult>()


    data class OnGoogleSignInResult<out LoginResult>(val result: LoginResult) : LoginEvent<LoginResult>()
}