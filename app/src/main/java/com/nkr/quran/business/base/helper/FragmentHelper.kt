package com.nkr.quran.business.base.helper

import android.app.Activity
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.nkr.quran.R


class FragmentHelper(activity: Activity) {
    val activity: Activity = activity

    /**
     * change fragment of Tab
     *
     * @param fragment
     * @param parentLayout
     * @param frameLayout
     */
    fun loadFragment(fragmentManager: FragmentManager, fragment: Fragment, parentLayout: String, frameLayout: Int) {
        val currentFragment = fragmentManager.findFragmentById(frameLayout)

        // create a FragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.enter,R.anim.exit,R.anim.pop_enter,R.anim.pop_exit)

        // replace the FrameLayout with new Fragment
        transaction.replace(frameLayout, fragment)
      /*
        if (fragment !is LoveByFragment && fragment !is CheerByFragment && fragment !is CommentsFragment
                && fragment !is PostDetailsFragment && fragment !is ProfileDetailsFragment) {
            currentFragment?.let { transaction.hide(it) }
        }
        */

        currentFragment?.let { transaction.hide(it) }

        transaction.addToBackStack(parentLayout)
        transaction.commit() // save the changes
    }

    /**
     * Pop fragment from back stack function
     */
    fun popFragment(fragmentManager: FragmentManager): Boolean {
        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStackImmediate()
            return true
        }
        return false
    }

    /**
     * Pop All fragment from back stack function
     */
    fun popAllFragment(fragmentManager: FragmentManager) {
        var total = fragmentManager.backStackEntryCount
        for (i in 0..total) {
            fragmentManager.popBackStackImmediate()
        }
    }

    /**
     * Initial fragment for each tab
     *
     * @param fragmentManager
     * @param fragment
     * @param frameLayout
     */
    fun initFragment(fragmentManager: FragmentManager, fragment: Fragment, frameLayout: Int) {
        // create a FragmentManager
        val transaction = fragmentManager.beginTransaction()
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        // add the FrameLayout with new Fragment
        transaction.add(frameLayout, fragment)
        transaction.commit() // save the changes
    }

    fun reloadFragment(fragmentManager: FragmentManager, fragment: Fragment){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            fragmentManager?.beginTransaction()?.detach(fragment)?.commitNow();
            fragmentManager?.beginTransaction()?.attach(fragment)?.commitNow();
        } else {
            fragmentManager?.beginTransaction()?.detach(fragment)?.attach(fragment)?.commit();
        }
    }
    /**
     * Show dialog function
     *
     * @param fragmentManager
     * @param dialogName
     * @param fragment
     */
    fun showDialogFragment(fragmentManager: FragmentManager, dialogName: String, fragment: DialogFragment) {
        val ft = fragmentManager.beginTransaction()
        ft.add(fragment, dialogName)
        ft.commit()
    }


    fun showDialogFragmentWithBundle(fragmentManager: FragmentManager, dialogName: String, BUNDLE_KEY:String, msg:Int, fragment: DialogFragment) {
        val ft = fragmentManager.beginTransaction()
        var bundle = Bundle()
        bundle.putString(BUNDLE_KEY,activity.getString(msg))
        fragment.arguments = bundle
        ft.add(fragment, dialogName)
        ft.commit()
    }
}