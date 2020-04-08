package com.nkr.fashionita.ui.fragment.productListing

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.Timestamp

import com.nkr.fashionita.R
import com.nkr.fashionita.base.BaseFragment
import com.nkr.fashionita.databinding.ProductListingFragmentBinding
import com.nkr.fashionita.model.Product
import com.nkr.fashionita.note.notelist.buildlogic.ProductListingListInjector
import com.nkr.fashionita.util.Helperutils.Companion.getCalendarTime
import com.nkr.fashionita.util.KEY_CATEGORY_NAME
import com.nkr.fashionita.util.KEY_POST_IMAGE_LIST
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

//redirected from ChooseDesignCategoryFragment

class ProductListingFragment : BaseFragment() {


    companion object {
        fun newInstance(category_name : String,imagePaths:ArrayList<String>) = ProductListingFragment().apply {
            arguments = Bundle().apply {
                putString(KEY_CATEGORY_NAME, category_name)
                putStringArrayList(KEY_POST_IMAGE_LIST,imagePaths)
            }
        }


        fun newInstance() = ProductListingFragment()

    }

    private lateinit var viewModel: ProductListingViewModel
    private lateinit var binding:ProductListingFragmentBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.product_listing_fragment, container, false
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            ProductListingListInjector(requireActivity().application).provideProductListingViewModelFactory()
        ).get(
            ProductListingViewModel::class.java
        )

        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        setupListener()

        observeViewModel()

    }

    private fun observeViewModel() {

        viewModel.showLoading.observe(
            this,
            Observer {
                if(it){
                    showLoadingDialog(getString(R.string.text_uploading), null)
                }else{
                    dismissLoadingDialog()
                }
            }
        )
    }

    override fun setupListener() {

        binding.ivBack.setOnClickListener {
            fragmentCallBack.goBack()
        }

        viewModel.listener = object : ProductListingViewModel.FragmentCallback {
            override fun onSuccessfulUpload() {
              //  goToHomeFragment()
                fragmentCallBack.onSuccessfulListing()
            }

        }



        val category_name = arguments?.getString(KEY_CATEGORY_NAME)

        val image_paths = arguments?.getStringArrayList(KEY_POST_IMAGE_LIST) ?: arrayListOf()
        Log.d("image_paths", image_paths.toString())
        Log.d("image_path", image_paths[0])



        binding.btnListingPost.setOnClickListener {

            var byteList = ArrayList<ByteArray>()
            var uploaded_image_paths = arrayListOf<String>()

            for (image in image_paths) {

                var image_path = Uri.fromFile(File(image))

                val selectedImageBmp = MediaStore.Images.Media
                    .getBitmap(activity?.contentResolver, image_path)

                val outputStream = ByteArrayOutputStream()
                selectedImageBmp.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
                var selectedImageBytes = outputStream.toByteArray()

                byteList.add(selectedImageBytes)


            }



            updateProduct(category_name.toString(),null,byteList)


        }
    }

    private fun updateProduct(
        category_name: String,
        uploaded_image_paths: ArrayList<String>?,
        byteList: ArrayList<ByteArray>
    ) {

        val listing_title = binding.etListingTitle.text.toString()
        val listing_size = binding.etListingSize.text.toString()
        val listing_brand = binding.etListingBrand.text.toString()
        val listing_condition = binding.etListingItemCondition.text.toString()
        val listing_deal_method = binding.etListingDealMethod.text.toString()
        val listing_price= binding.etListingPrice.text.toString()
        val listing_description= binding.etListingDescription.text.toString()


            if (!TextUtils.isEmpty(category_name) && !TextUtils.isEmpty(listing_title) &&
                !TextUtils.isEmpty(listing_price) && !TextUtils.isEmpty(listing_description)

            ) {

                // StorageUtil.uploadProductPhoto(selectedImageBytes){image_path->

                val prod = Product(
                    "",
                    category_name,
                    getCalendarTime(),
                    "",
                    uploaded_image_paths,
                    "",
                    listing_title,
                    listing_price,
                    listing_size,
                    listing_brand,
                    listing_condition,
                    listing_deal_method,
                    listing_description,
                    false,
                    Calendar.getInstance().time
                )

                viewModel.handleEvent(ProductListingEvent.OnPostProductBtnClick(prod,byteList))


            } else {
                Toast.makeText(activity, "Fill in the necessary fields", Toast.LENGTH_SHORT)
                    .show()
            }

        }



    lateinit var fragmentCallBack: FragmentCallBack

    interface FragmentCallBack {
        fun goTo(fragment: Fragment)

        fun goBack()

        fun onSuccessfulListing()


    }


}
