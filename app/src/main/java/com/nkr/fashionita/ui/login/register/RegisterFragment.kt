package com.nkr.fashionita.ui.login.register


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth

import com.nkr.fashionita.R
import com.nkr.fashionita.base.BaseFragment
import com.nkr.fashionita.model.User
import com.nkr.fashionita.ui.MainActivity
import com.nkr.fashionita.ui.fragment.account.FirestoreUtil
import com.nkr.fashionita.ui.login.LoginEvent
import com.nkr.fashionita.ui.login.LoginView
import com.nkr.fashionita.ui.login.UserViewModel
import com.wiseassblog.jetpacknotesmvvmkotlin.login.buildlogic.LoginInjector
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.btn_fm_register


/**
 * A simple [Fragment] subclass.
 */
class RegisterFragment : BaseFragment() {

    companion object{
        @JvmStatic
        fun newInstance() =
            RegisterFragment().apply {
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onStart() {
        super.onStart()

        initViewModel()

        setupListener()

        observeViewModel()

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
    }

    private fun initViewModel() {

        viewModel = ViewModelProvider(
            this,
            LoginInjector(requireActivity().application).provideUserViewModelFactory()
        ).get(UserViewModel::class.java)
    }

    override fun setupListener() {

        btn_fm_register.setOnClickListener {
            registerUser()
        }

        label_signin.setOnClickListener {
            fragmentCallBack.goBack()
        }
    }

    private fun registerUser() {

        var username = et_fm_register_username.text.toString()
        var email = et_fm_register_email.text.toString()
        var password = et_fm_register_pw.text.toString()


        if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {

            //show dialog here

            viewModel.showLoading.value = true

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(requireActivity(), OnCompleteListener { task ->
                if (task.isSuccessful) {
                    val current_user = mAuth.currentUser
                    val uid = current_user!!.uid

                    val user = User(uid,username,email,"","")

                    FirestoreUtil.initCurrentUserIfFirstTimeEmailPassword(user){


                        startActivity(Intent(requireContext(), MainActivity::class.java))

                        viewModel.showLoading.value = false

                    }

                }else {

                    // and finish dialog here
                    viewModel.showLoading.value = false

                    Toast.makeText(requireContext(), "Error registering, try again later :(", Toast.LENGTH_LONG).show()
                }
            })
        }else {
            Toast.makeText(requireContext(),"Please fill up the Credentials :|", Toast.LENGTH_LONG).show()
        }
    }


    /**
     * listener related
     */
    lateinit var fragmentCallBack: RegisterFragmentCallBack

    interface RegisterFragmentCallBack {
        fun goTo(fragment: Fragment)

        fun goBack()


    }


}
