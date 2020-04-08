package com.nkr.fashionita.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import java.util.*

/**
 * Created
 */

class CommonPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragmentList = ArrayList<Fragment>()
    private val titleList = ArrayList<String>()

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }



    fun addFragment(fragment: Fragment) {
        fragmentList.add(fragment)
    }

    fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        titleList.add(title)
    }

    fun addFragmentToIndex(index: Int, fragment: Fragment) {
        fragmentList.add(index, fragment)
    }


    fun addFragmentToIndex(index: Int, fragment: Fragment, title: String) {
        fragmentList.add(index, fragment)
        titleList.add(index, title)
    }

    fun removeFragmentAtIndex(index: Int) {
        fragmentList.removeAt(index)
        if (titleList.size > 0) {
            titleList.removeAt(index)
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (titleList.size > 0) {
            titleList[position]
        } else {
            ""
        }
    }

}
