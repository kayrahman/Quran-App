package com.nkr.fashionita.ui.fragment.main


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.nkr.fashionita.R
import com.nkr.fashionita.base.BaseFragment
import com.nkr.fashionita.databinding.FragmentMainHomeParentBinding
import com.nkr.fashionita.ui.fragment.homeTab.HomeFragment


class HomeParentFragment : BaseFragment() {

    //view model and binding
    private lateinit var binding: FragmentMainHomeParentBinding

    //fragment related
    lateinit var homeFragment: HomeFragment

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment HomeParentFragment.
         */
        @JvmStatic
        fun newInstance() =
                HomeParentFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_main_home_parent, container, false)

      //  initViewModel()

        //initial first fragment
        initChildFragment()

        return binding.root
    }

    override fun setupListener() {
    }

    /**
     * initial first child fragment
     */
    private fun initChildFragment() {

        homeFragment = HomeFragment.newInstance()
        //set listener
        homeFragment.fragmentCallBack = object : HomeFragment.FragmentCallBack {


            override fun goTo(fragment: Fragment) {
                fragmentHelper.loadFragment(childFragmentManager, fragment, "HOMEParent", R.id.fly_home_parent)
            }

            override fun goBack() {
                fragmentHelper.popFragment(childFragmentManager)
            }
        }


        fragmentHelper.initFragment(childFragmentManager, homeFragment, R.id.fly_home_parent)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        if (isVisibleToUser && isAdded) {

            homeFragment?.userVisibleHint = true
            Log.d("IsVisible", (this as Any).javaClass.simpleName + " is on screen")
        }
        super.setUserVisibleHint(isVisibleToUser)
    }




    interface FragmentCallback{

    }

}
