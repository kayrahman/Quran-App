package com.nkr.fashionita.ui

//TODO 1. buy now fragment
//TODO 2. hide bottom navigation bar on detail screen
//TODO 3. share listing 
//TODO 4. broadcast receiver for wifi
//TODO 5. detail images fragment
//TODO 6. profile fragment

import android.animation.ValueAnimator
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.google.firebase.auth.FirebaseAuth
import com.nkr.fashionita.R
import com.nkr.fashionita.base.BaseActivity
import com.nkr.fashionita.repository.FirebaseUserRepoImpl
import com.nkr.fashionita.ui.fragment.account.MyAccountFragment
import com.nkr.fashionita.ui.fragment.main.PostParentFragment
import kotlinx.android.synthetic.main.activity_home.*
import com.nkr.fashionita.common.*
import com.nkr.fashionita.model.App
import com.nkr.fashionita.network.ConnectivityBroadcastReciever
import com.nkr.fashionita.network.OnConnectivityChangeListener
import com.nkr.fashionita.ui.fragment.account.FirestoreUtil.getCurrentUser
import com.nkr.fashionita.ui.fragment.main.HomeParentFragment
import com.nkr.fashionita.ui.fragment.notification.NotificationFragment
import com.nkr.fashionita.ui.fragment.productListing.ProductListingFragment
import com.nkr.fashionita.ui.fragment.search.SearchResultFragment
import com.nkr.fashionita.ui.fragment.updateProfile.EditProfileEvent
import com.nkr.fashionita.ui.login.LoginActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch


class MainActivity : BaseActivity(),OnConnectivityChangeListener {


    lateinit var mAuth: FirebaseAuth
    lateinit var mListener: FirebaseAuth.AuthStateListener

    internal var mConnectivityBroadcastReciever = ConnectivityBroadcastReciever()
   // internal var mNetworkErrorLottieAnimation: LottieAnimationView

    companion object {
        private const val ITEM_HOME =1
        private const val ITEM_POST =2
        private const val ITEM_NOTIFICATION =3
        private const val ITEM_PROFILE =4

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        mAuth = FirebaseAuth.getInstance()
        mListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            if (firebaseAuth.currentUser == null) {
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }
        }

        Log.d("username",mAuth.currentUser?.displayName+""+
                mAuth.currentUser?.email
        )



       getCurrentUser{
           App.userInfo = it
       }
      //  uiHelper.setStatusBarTransparent()

        initBottomNavigation()

        setupListener()


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

        mAuth.addAuthStateListener(mListener)
        
    }

    override fun onStop() {
        super.onStop()

        unregisterReceiver(mConnectivityBroadcastReciever)

    }



    private fun initBottomNavigation() {
      //  val bottomNavigation = findView(R.id.navigationView)
        navigationView.add(MeowBottomNavigation.Model(ITEM_HOME, R.drawable.bottom_home_unselected))
        navigationView.add(MeowBottomNavigation.Model(ITEM_POST, R.drawable.ic_add_circle_orange))
        navigationView.add(MeowBottomNavigation.Model(ITEM_NOTIFICATION, R.drawable.ic_notifications_unselected))
        navigationView.add(MeowBottomNavigation.Model(ITEM_PROFILE, R.drawable.ic_profile_unselected))


        navigationView.setCount(ITEM_NOTIFICATION,"3")
        navigationView.show(ITEM_HOME)

        navigationView.setOnShowListener {

            when (it.id) {
                ITEM_HOME ->replaceFragment(HomeParentFragment())

               // ITEM_NOTIFICATION -> replaceFragment(PostParentFragment())

               // ITEM_PROFILE -> replaceFragment(MyAccountFragment())

                else -> ""
            }

        }

        navigationView.setOnClickMenuListener {

            when (it.id) {
                ITEM_HOME -> {

                    //replaceFragment(HomeParentFragment())
                    val homeParentFm = HomeParentFragment.newInstance()
                    fragmentHelper.initFragment(supportFragmentManager,homeParentFm,R.id.fragment_nav)



                }

                ITEM_POST ->
                {

                    val postParentFragment = PostParentFragment.newInstance()
                    postParentFragment.fragmentCallback = object :PostParentFragment.FragmentCallback{
                        override fun onSuccessfulListing() {
                            //go to profile tab
                            navigationView.show(ITEM_PROFILE)
                            replaceFragment(MyAccountFragment())

                            Log.d("ListingState","Success")
                        }

                    }
                    fragmentHelper.initFragment(supportFragmentManager,postParentFragment,R.id.fragment_nav)

                }



                ITEM_NOTIFICATION ->
                {

                    val notificationFragment = NotificationFragment.newInstance()
                    fragmentHelper.loadFragment(supportFragmentManager,notificationFragment,"parent",R.id.fragment_nav)

                }



                ITEM_PROFILE ->
                {

                    val myAccFm = MyAccountFragment.newInstance()

                    myAccFm.myAccfragmentCallBack = object : MyAccountFragment.MyAccFragmentCallBack{
                        override fun goTo(fragment: Fragment) {
                            fragmentHelper.loadFragment(supportFragmentManager,fragment,"parent",R.id.fragment_nav)
                        }

                        override fun goBack() {
                            fragmentHelper.popFragment(supportFragmentManager)

                        }

                    }
                    fragmentHelper.loadFragment(supportFragmentManager,myAccFm,"parent",R.id.fragment_nav)

                }



                else -> ""
            }

        }


      /*  navigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.item_home -> {
                    replaceFragment(HomeParentFragment())
                    true
                }
                R.id.item_profile -> {
                    replaceFragment(MyAccountFragment())
                    true
                }
                R.id.item_upload_design -> {
                    replaceFragment(PostParentFragment())


                    true
                }

                else -> false
            }
        }

        navigationView.selectedItemId = R.id.item_home
*/

    }



    private fun checkCurrentUser()= CoroutineScope(IO).launch{
        val repo = FirebaseUserRepoImpl()
      val userResult =  repo.getCurrentUser()

        when(userResult){
            is Result.Value -> {
                if(userResult.value == null){
                    goToLoginActivity()
                }else{
                    Log.d("User_result",userResult.toString())
                }
            }
            is Result.Error -> {
               Log.d("User_result","Error")
            }

        }

    }

    private fun goToLoginActivity() {

        startActivity(
            Intent(
                this@MainActivity,
                LoginActivity::class.java
            )
        )

        Log.d("User_result","Let's go to login activity")

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
