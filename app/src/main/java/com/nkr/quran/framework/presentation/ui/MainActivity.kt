package com.nkr.quran.framework.presentation.ui

import android.animation.ValueAnimator
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.nkr.quran.R
import com.nkr.quran.business.base.BaseActivity
import com.nkr.quran.framework.presentation.ui.fragment.main.QuranChapterFragment

import kotlinx.android.synthetic.main.activity_home.*
import com.nkr.quran.network.ConnectivityBroadcastReciever
import com.nkr.quran.network.OnConnectivityChangeListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity(),OnConnectivityChangeListener {


   // lateinit var mAuth: FirebaseAuth
  //  lateinit var mListener: FirebaseAuth.AuthStateListener

    internal var mConnectivityBroadcastReciever = ConnectivityBroadcastReciever()
   // internal var mNetworkErrorLottieAnimation: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)



        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_nav, QuranChapterFragment::class.java, null)
            .commit()



      /*

        uiHelper.setStatusBarTransparent()

        initBottomNavigation()

        setupListener()*/


    }


    override fun onBackPressed() {
        super.onBackPressed()


        val count = supportFragmentManager.backStackEntryCount

        Log.d("backstack_count",count.toString())

        if (count == 0) {
            super.onBackPressed()
            //additional code
        } else {
           // supportFragmentManager.popBackStack()
            fragmentManager.popBackStackImmediate()
        }
    }

    override fun onStart() {
        super.onStart()

        val connectivityIntentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(mConnectivityBroadcastReciever, connectivityIntentFilter)

//        mAuth.addAuthStateListener(mListener)
        
    }

    override fun onStop() {
        super.onStop()

        unregisterReceiver(mConnectivityBroadcastReciever)

    }
    private fun initBottomNavigation() {

        navigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {

            }
            false
        }

        navigationView.selectedItemId = R.id.item_dashboard

    }




    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_nav, fragment)
            .addToBackStack("parent")
            .commit()
    }


    override fun initViewModel() {


    }

    override fun setupListener() {


    }


    override fun onConnectivityChangeListener(noConnectivity: Boolean) {

        if (noConnectivity) {

            if (fl_ac_main_no_connection.visibility == View.GONE) {

                fragment_nav.visibility = View.GONE
                fl_ac_main_no_connection.setVisibility(View.VISIBLE)

                startCheckAnimation()

            }
        } else  {

            if (fl_ac_main_no_connection.visibility == View.VISIBLE) {

                fragment_nav.visibility = View.VISIBLE
                fl_ac_main_no_connection.visibility = View.GONE

            }

        }
    }


    private fun startCheckAnimation() {

        val animator = ValueAnimator.ofFloat(0f, 1f).setDuration(8000)
        animator.addUpdateListener { valueAnimator ->
            lottie_network_error.setProgress(
                valueAnimator.animatedValue as Float
            )
        }

        if (lottie_network_error.getProgress() === 0f) {
            animator.start()
        } else {

            lottie_network_error.setProgress(0f)

        }
    }


}
