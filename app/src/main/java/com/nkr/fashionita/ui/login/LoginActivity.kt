package com.nkr.fashionita.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.firebase.FirebaseApp
import com.nkr.fashionita.R
import com.nkr.fashionita.base.BaseActivity
import com.nkr.fashionita.base.helper.UIHelper
import kotlinx.android.synthetic.main.fragment_login_parent.*


private const val LOGIN_VIEW = "LOGIN_VIEW"

class LoginActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        FirebaseApp.initializeApp(applicationContext)

        uiHelper.setStatusBarTransparent()

        initLoginFragment()

    }

    private fun initLoginFragment() {

        val loginFragment = LoginView.newInstance()
        loginFragment.loginFragmentCallBack = object : LoginView.FragmentCallBack{
            override fun goTo(fragment: Fragment) {
                fragmentHelper.loadFragment(supportFragmentManager,fragment,"login_parent",R.id.root_activity_login)

            }

            override fun goBack() {

                fragmentHelper.popFragment(supportFragmentManager)

            }

        }
        fragmentHelper.initFragment(supportFragmentManager, loginFragment, R.id.root_activity_login)

    }

    override fun initViewModel() {

    }

    override fun setupListener() {

    }
}
