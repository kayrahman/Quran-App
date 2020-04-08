package com.nkr.fashionita.ui.fragment.post

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment

import com.nkr.fashionita.R
import com.nkr.fashionita.base.BaseFragment
import com.nkr.fashionita.databinding.ChooseDesignCategoryFragmentBinding
import com.nkr.fashionita.model.CategoryItem
import com.nkr.fashionita.ui.adapter.CategoryPostAdapter
import com.nkr.fashionita.ui.fragment.productListing.ProductListingFragment
import com.nkr.fashionita.ui.fragment.viewModelFactory.ChooseDesignCatgoryViewModelFactory
import com.nkr.fashionita.util.KEY_POST_IMAGE_LIST


//redirected from post design fragment

class ChooseProductCategoryFragment : BaseFragment() {

    private lateinit var viewModel: ChooseDesignCategoryViewModel
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

        viewModelFactory = ChooseDesignCatgoryViewModelFactory(postedImageList)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ChooseDesignCategoryViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        setupListener()

    }


    override fun onStart() {
        super.onStart()

        viewModel.handleEvent(PostProdEvent.OnStart)
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
