package com.nkr.fashionita.ui.login

import androidx.lifecycle.MutableLiveData
import com.nkr.fashionita.common.*
import com.nkr.fashionita.common.ANTENNA_EMPTY
import com.nkr.fashionita.common.LOGIN_ERROR
import com.nkr.fashionita.common.SIGN_IN
import com.nkr.fashionita.model.LoginResult
import com.nkr.fashionita.model.User
import com.nkr.fashionita.repository.IUserRepository
import com.nkr.fashionita.ui.fragment.account.FirestoreUtil.initCurrentUserIfFirstTime
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class UserViewModel(val repo: IUserRepository, uiContext: CoroutineContext) : BaseViewModel<LoginEvent<LoginResult>>(uiContext) {

    //The actual data model is kept private to avoid unwanted tampering
    private val userState = MutableLiveData<User>()

    //Control Logic
    internal val authAttempt = MutableLiveData<Unit>()
    internal val startAnimation = MutableLiveData<Unit>()

    //UI Binding
    internal val signInStatusText = MutableLiveData<String>()
    internal val authButtonText = MutableLiveData<String>()
    internal val satelliteDrawable = MutableLiveData<String>()

    private fun showErrorState() {
        signInStatusText.value = LOGIN_ERROR
        authButtonText.value = SIGN_IN
        satelliteDrawable.value = ANTENNA_EMPTY
    }

    private fun showLoadingState() {
        signInStatusText.value = LOADING
        satelliteDrawable.value = ANTENNA_LOOP
        startAnimation.value = Unit
    }

    private fun showSignedInState() {

        /*
        signInStatusText.value = SIGNED_IN
        authButtonText.value = SIGN_OUT
        satelliteDrawable.value = ANTENNA_FULL

        */

        //findNavController().navigate(com.nkr.fashionita.R.id.loginView)

        // if the userinfo is not available in database create one here


        viewModelCallback?.newGoogleUser()



    }

    private fun showSignedOutState() {
        signInStatusText.value = SIGNED_OUT
        authButtonText.value = SIGN_IN
        satelliteDrawable.value = ANTENNA_EMPTY
    }

    override fun handleEvent(event: LoginEvent<LoginResult>) {
        //Trigger loading screen first
        showLoadingState()
        when (event) {
            is LoginEvent.OnAuthButtonClick -> onAuthButtonClick()
            is LoginEvent.OnGoogleSignInResult -> onSignInResult(event.result)
        }
    }

    private fun getUser() = launch {
        val result = repo.getCurrentUser()
        when (result) {
            is Result.Value -> {
                userState.value = result.value
                if (result.value == null) showSignedOutState()
                else showSignedInState()
            }
            is Result.Error -> showErrorState()
        }
    }

    /**
     * If user is null, tell the View to begin the authAttempt. Else, attempt to sign the user out
     */
    private fun onAuthButtonClick() {
        if (userState.value == null) authAttempt.value = Unit
        else signOutUser()
    }

    private fun onSignInResult(result: LoginResult) = launch {
        if (result.requestCode == RC_SIGN_IN && result.userToken != null) {

            apiCallStart()
            val createGoogleUserResult = repo.signInGoogleUser(result.userToken)
            //Result.Value means it was successful
            if (createGoogleUserResult is Result.Value) {
               // getUser()
                initCurrentUserIfFirstTime{

                    viewModelCallback?.newGoogleUser()
                }
                apiCallFinish()
            }else{
                showErrorState()
                apiCallFinish()
            }
        } else {
            showErrorState()

        }
    }


    private fun signOutUser() = launch {
        val result = repo.signOutCurrentUser()

        when (result) {
            is Result.Value -> {
                userState.value = null
                showSignedOutState()
            }
            is Result.Error -> showErrorState()
        }
    }


    /**
     * listener related
     */
    var viewModelCallback: ViewModelCallback? = null

    interface ViewModelCallback{

        fun newGoogleUser()

    }



}