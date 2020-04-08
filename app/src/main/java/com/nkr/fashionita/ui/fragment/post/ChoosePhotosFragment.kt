package com.nkr.fashionita.ui.fragment.post

import android.Manifest
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

import com.nkr.fashionita.R
import com.nkr.fashionita.base.BaseFragment
import com.nkr.fashionita.databinding.PostDesignFragmentBinding
import com.nkr.fashionita.ui.adapter.GalleryGridImageAdapter
import org.jetbrains.anko.textColor

class ChoosePhotosFragment : BaseFragment() {

    private lateinit var viewModel: ChoosePhotosViewModel
    private lateinit var binding: PostDesignFragmentBinding


    companion object {
        // fun newInstance() = ChoosePhotosFragment()

        @JvmStatic
        fun newInstance() =
            ChoosePhotosFragment().apply {

            }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = DataBindingUtil.inflate(
            inflater, R.layout.post_design_fragment, container, false
        )

        initViewModel()

        return binding.root

    }


   private  fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(ChoosePhotosViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        checkPermission()

        setupListener()

    }


    override fun setupListener() {


        //next step of post creation
        binding.toolbar?.tvNext?.setOnClickListener {

            /**
             * take user to category fragment
             **/
             startCategoryFragment()


        }


        binding.toolbar?.ivBack?.setOnClickListener {

            // activity?.finish()
            // fragmentCallBack.goBack()
            fragmentHelper.popFragment(childFragmentManager)

        }

        /*

         //change to camera mode
         binding.toolbar?.ivGallery?.setOnClickListener {
             */
        /**
         * gallery button will take user to preview activity
         ***//*

            startCameraActivity()
//
//            if (ContextCompat.checkSelfPermission(activity!!, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                requestCameraPermission()
//            } else {
//                startCameraActivity()
//            }
        }

        */

    }

    private fun startCategoryFragment() {

        var fragment = ChooseProductCategoryFragment.newInstance(viewModel.adapter.imgSelected)
        fragment.fragmentCallBack = object : ChooseProductCategoryFragment.FragmentCallBack {
            override fun goTo(fragment: Fragment) {

                fragmentCallBack.goTo(fragment)
            }

            override fun goBack() {

                fragmentCallBack.goBack()
            }

            override fun onSuccessfulListing() {

                fragmentCallBack.onSuccessfulListing()
            }

        }

        fragmentCallBack.goTo(fragment)
    }


    /**
     * check permission of read storage
     */
    private fun checkPermission() {
        Dexter.withActivity(requireActivity())
            .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) {/* ... */


                    viewModel.getAllShownImagesPath(requireActivity())

                    viewModel.adapter.listener = object : GalleryGridImageAdapter.AdapterListener {
                        override fun errorToast(msg: String) {
                            // msgHelper.toastShort(msg)
                        }

                        //post image limit
                        override fun onItemClick(size: Int) {

                            //change the next button colour here

                            //  binding.toolbar?.ivGallery?.visibility = if (size >= 1) View.GONE else View.GONE//View.VISIBLE
                            // binding.toolbar?.tvNext?.visibility = if (size >= 1) View.VISIBLE else View.VISIBLE//View.GONE
                            binding.toolbar?.tvNext?.textColor =
                                if (size >= 1) context!!.resources.getColor(R.color.colorAccent) else R.color.colorTextGray


                        }
                    }
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {/* ... */
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            }).check()
    }


    /**
     * listener related
     */
    lateinit var fragmentCallBack: FragmentCallBack

    interface FragmentCallBack {
        fun goTo(fragment: Fragment)

        fun goBack()

        fun onSuccessfulListing()


    }


}
