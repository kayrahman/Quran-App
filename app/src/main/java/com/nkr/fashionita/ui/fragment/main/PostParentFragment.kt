package com.nkr.fashionita.ui.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nkr.fashionita.R
import com.nkr.fashionita.base.BaseFragment
import com.nkr.fashionita.ui.fragment.post.ChoosePhotosFragment


class PostParentFragment : BaseFragment() {


    //fragment related
    private var postDesignFragment: ChoosePhotosFragment? = null

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment PostParentFragment.
         */
        @JvmStatic
        fun newInstance() =
                PostParentFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }
    /*

      override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        if (isVisibleToUser && isAdded) {

            postCreationFragment?.checkPermission()
            postCreationFragment?.userVisibleHint = true
            Log.d("IsVisible", (this as Any).javaClass.simpleName + " is on screen")


        }
        super.setUserVisibleHint(isVisibleToUser)
    }
*/
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = getRootView(inflater, container!!, R.layout.fragment_main_post_parent)

        //initial first fragment
        initChildFragment()

        return view
    }



    override fun bindComponents(view: View) {

    }

    override fun setupListener() {



    }


    /**
     * initial first child fragment
     */
    private fun initChildFragment() {


     //   postCreationFragment = PostCreationFragment.newInstance()
        //fragmentHelper.initFragment(childFragmentManager, postCreationFragment!!, R.id.fly_camera_parent)

        postDesignFragment = ChoosePhotosFragment.newInstance()
        postDesignFragment!!.fragmentCallBack = object : ChoosePhotosFragment.FragmentCallBack {
            override fun goTo(fragment: Fragment) {
                fragmentHelper.loadFragment(childFragmentManager, fragment, "PostParent", R.id.fly_camera_parent)
            }

            override fun goBack() {
                fragmentHelper.popFragment(childFragmentManager)
            }

            override fun onSuccessfulListing() {
                fragmentCallback.onSuccessfulListing()
            }


        }
        fragmentHelper.initFragment(childFragmentManager, postDesignFragment!!, R.id.fly_camera_parent)


    }


    lateinit var fragmentCallback :FragmentCallback

    interface FragmentCallback{
        fun onSuccessfulListing()
    }

}
