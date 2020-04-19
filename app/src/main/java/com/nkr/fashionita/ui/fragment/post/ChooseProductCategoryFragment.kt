package com.nkr.fashionita.ui.fragment.post

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.nkr.fashionita.R
import com.nkr.fashionita.base.BaseFragment
import com.nkr.fashionita.databinding.ChooseDesignCategoryFragmentBinding
import com.nkr.fashionita.model.CategoryItem
import com.nkr.fashionita.ui.adapter.CategoryPostAdapter
import com.nkr.fashionita.ui.adapter.ChooseDesignCategoryImageAdapter
import com.nkr.fashionita.ui.fragment.productListing.ProductListingFragment
import com.nkr.fashionita.ui.fragment.viewModelFactory.ChooseDesignCatgoryViewModelFactory
import com.nkr.fashionita.util.KEY_POST_IMAGE_LIST
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import java.io.File


//redirected from post design fragment

class ChooseProductCategoryFragment : BaseFragment() {

    private lateinit var viewModel: ChooseProductCategoryViewModel
    private lateinit var viewModelFactory: ChooseDesignCatgoryViewModelFactory
    private lateinit var binding: ChooseDesignCategoryFragmentBinding


    companion object {
        // fun newInstance() = ChooseDesignCategoryFragment()

        @JvmStatic
        fun newInstance(imageList: ArrayList<String>) =
            ChooseProductCategoryFragment().apply {

                arguments = Bundle().apply {
                    putStringArrayList(KEY_POST_IMAGE_LIST, imageList)

                }
            }

    }


    private var image_position = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.choose_design_category_fragment, container, false
        )

        initViewModel()

        return binding.root
    }


    private fun initViewModel() {

        var postedImageList = arguments?.getStringArrayList(KEY_POST_IMAGE_LIST) ?: arrayListOf()


        Log.d("image_list",postedImageList.size.toString())

        viewModelFactory = ChooseDesignCatgoryViewModelFactory(postedImageList)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ChooseProductCategoryViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        viewModel.handleEvent(PostProdEvent.OnStart)

        setupListener()



    }


    override fun setupListener() {

        viewModel.categoryListAdapter.listener = object: CategoryPostAdapter.AdapterListener{
            override fun errorToast(msg: String) {


            }

            override fun onItemClick(item : CategoryItem) {
              //  goToListingFragment()

                val image_paths = arguments?.getStringArrayList(KEY_POST_IMAGE_LIST) ?: arrayListOf()
                Log.d("image_paths",image_paths.toString())
                Log.d("sub_cat_women",item.sub_categories.toString() )

                val listingFragment = ProductListingFragment.newInstance(item.category_name,image_paths)

                listingFragment.fragmentCallBack = object : ProductListingFragment.FragmentCallBack{
                    override fun goTo(fragment: Fragment) {
                        //

                    }

                    override fun goBack() {
                        fragmentCallBack.goBack()
                    }

                    override fun onSuccessfulListing() {
                        fragmentCallBack.onSuccessfulListing()
                    }


                }

                fragmentCallBack.goTo(listingFragment)

            }

        }

        viewModel.adapter.listener = object : ChooseDesignCategoryImageAdapter.AdapterListener{
            override fun errorToast(msg: String) {


            }

            override fun onItemClick(position : Int,path : String) {
            // implement image cropping here

                image_position = position

                val uri = Uri.fromFile(File(path))
                Log.d("content_uri",uri.toString())

                openImageInGallery(uri)

            }

        }


    }

    private fun openImageInGallery(path: Uri) {

      // val path = Uri.parse("content://media/external/images/media/51717")

        Dexter.withActivity(requireActivity())
            .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) {/* ... */

                    CropImage.activity(path)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .setMinCropWindowSize(500, 500)
                        //.start(activity!!)
                        .start(requireContext(),this@ChooseProductCategoryFragment)


                   // startActivityForResult()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {/* ... */
                    msgHelper.toastShort(getString(R.string.msg_missing_read_storage_permission))
                }

                override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest, token: PermissionToken) {
                    token.continuePermissionRequest()
                }
            }).check()



    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            val result = CropImage.getActivityResult(data)

            if (resultCode == Activity.RESULT_OK) {
                val resultUri = result.uri

                viewModel.adapter.notifyItemChanged(image_position,resultUri.path.toString())


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
                Log.d("result_uri",error.toString())
            }

        }else{
            Log.d("result_uri","result not found")

        }


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
