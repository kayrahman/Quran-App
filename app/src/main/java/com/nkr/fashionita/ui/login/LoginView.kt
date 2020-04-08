package com.nkr.fashionita.ui.login


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.nkr.fashionita.R
import com.nkr.fashionita.base.BaseFragment
import com.nkr.fashionita.common.ANTENNA_LOOP
import com.nkr.fashionita.common.RC_SIGN_IN
import com.nkr.fashionita.model.LoginResult
import com.nkr.fashionita.ui.MainActivity
import com.nkr.fashionita.ui.fragment.account.FirestoreUtil.initCurrentUserIfFirstTime
import com.nkr.fashionita.ui.fragment.main.HomeParentFragment
import com.nkr.fashionita.ui.login.register.RegisterFragment
import com.wiseassblog.jetpacknotesmvvmkotlin.login.buildlogic.LoginInjector
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * A simple [Fragment] subclass.
 */
class LoginView : BaseFragment() {

    companion object{
        @JvmStatic
        fun newInstance() =
            LoginView().apply {
                arguments = Bundle().apply {
                }
            }
    }

    private lateinit var viewModel: UserViewModel

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }



    //Create and bind to ViewModel
    override fun onStart() {
        super.onStart()

        viewModel = ViewModelProvider(
            this,
            LoginInjector(requireActivity().application).provideUserViewModelFactory()
        ).get(UserViewModel::class.java)

        //start background anim
      //  (root_fragment_login.background as AnimationDrawable).startWithFade()

        setupListener()

        observeViewModel()

        viewModel.handleEvent(LoginEvent.OnStart)
    }

    override fun setupListener() {

        btn_fm_login.setOnClickListener {

            signinUser()
        }

        btn_fm_register.setOnClickListener {

          //  registerUser()
            val registerFragment = RegisterFragment.newInstance()
            registerFragment.fragmentCallBack = object : RegisterFragment.RegisterFragmentCallBack{
                override fun goTo(fragment: Fragment) {


                }

                override fun goBack() {
                    loginFragmentCallBack.goBack()

                }

            }
            loginFragmentCallBack.goTo(registerFragment)

        }

        viewModel.viewModelCallback = object:UserViewModel.ViewModelCallback{
            override fun newGoogleUser() {
                startListActivity()
            }

        }


        btn_auth_attempt.setOnClickListener {
            viewModel.handleEvent(LoginEvent.OnAuthButtonClick)
        }


        requireActivity().onBackPressedDispatcher.addCallback(this) {
            startListActivity()
        }


    }

    private fun signinUser() {

        var email = et_fm_login_email.text.toString()
        var password = et_fm_pw.text.toString()


        if (email.isNotEmpty() && password.isNotEmpty()) {

            viewModel.showLoading.value = true

            //show dialog here
            viewModel.showLoading.value = true


            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener ( requireActivity(), OnCompleteListener<AuthResult> { task ->
                if (task.isSuccessful) {

                    viewModel.showLoading.value = false

                    startActivity(Intent(requireContext(), MainActivity::class.java))

                    Toast.makeText(requireContext(), "Successfully Logged in :)", Toast.LENGTH_LONG).show()
                } else {

                    viewModel.showLoading.value = false

                    Toast.makeText(requireContext(), "Error Logging in :(", Toast.LENGTH_SHORT).show()
                }
            })

        }else {
            Toast.makeText(requireContext(), "Please fill up the Credentials :|", Toast.LENGTH_SHORT).show()
        }


    }





    private fun observeViewModel() {

        viewModel.showLoading.observe(
            this,
            Observer {
                if(it){
                    showLoadingDialog(getString(R.string.text_loading), null)
                }else{
                    dismissLoadingDialog()
                }
            }
        )

        viewModel.signInStatusText.observe(
            viewLifecycleOwner,
            Observer {
                //"it" is the alue of the MutableLiveData object, which is inferred to be a String automatically
               // lbl_login_status_display.text = it

                Log.d("login_status", it.toString())

            }
        )

        viewModel.authButtonText.observe(
            viewLifecycleOwner,
            Observer {
              //  btn_auth_attempt.text = it
            }
        )

        viewModel.startAnimation.observe(
            viewLifecycleOwner,
            Observer {
                /*imv_antenna_animation.setImageResource(
                    resources.getIdentifier(ANTENNA_LOOP, "drawable", activity?.packageName)
                )*/
//                (imv_antenna_animation.drawable as AnimationDrawable).start()
            }
        )

        viewModel.authAttempt.observe(
            viewLifecycleOwner,
            Observer { startSignInFlow() }
        )

        viewModel.satelliteDrawable.observe(
            viewLifecycleOwner,
            Observer {
               /* imv_antenna_animation.setImageResource(
                    resources.getIdentifier(it, "drawable", activity?.packageName)
                )*/
            }
        )
    }

    private fun startListActivity() = requireActivity().startActivity(
        Intent(
            activity,
            MainActivity::class.java
        )
    )


    private fun startSignInFlow() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .requestProfile()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        var userToken: String? = null

        try {
            val account: GoogleSignInAccount? = task.getResult(ApiException::class.java)

            if (account != null) userToken = account.idToken


        } catch (exception: Exception) {
            Log.d("LOGIN", exception.toString())
        }

        viewModel.handleEvent(
            LoginEvent.OnGoogleSignInResult(
                LoginResult(
                    requestCode,
                    userToken
                )
            )
        )
    }




    /**
     * listener related
     */
    lateinit var loginFragmentCallBack: FragmentCallBack

    interface FragmentCallBack {
        fun goTo(fragment: Fragment)

        fun goBack()


    }






}
