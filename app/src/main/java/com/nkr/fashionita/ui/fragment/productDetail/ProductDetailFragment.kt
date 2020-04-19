package com.nkr.fashionita.ui.fragment.productDetail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.nkr.fashionita.R
import com.nkr.fashionita.base.BaseFragment
import com.nkr.fashionita.databinding.ProductDetailFragmentBinding
import com.nkr.fashionita.ui.Item.notedetail.NoteDetailEvent
import com.nkr.fashionita.util.KEY_CURRENT_PRODUCT_POSITION


import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import com.nkr.fashionita.model.App
import com.nkr.fashionita.note.notelist.buildlogic.ProductDetailItemListInjector
import com.nkr.fashionita.ui.MainActivity
import com.nkr.fashionita.ui.adapter.ProductImagesAdapter
import com.nkr.fashionita.ui.fragment.checkoutFragment.CheckOutFragment
import kotlinx.android.synthetic.main.product_images_layout.*
import kotlinx.android.synthetic.main.product_images_layout.view.*
import org.jetbrains.anko.windowManager


class ProductDetailFragment : BaseFragment() {


    companion object {
        fun newInstance() =
            ProductDetailFragment().apply {
              /*  arguments = Bundle().apply {
                    putString(KEY_CURRENT_PRODUCT_POSITION, position)*/
                }


    }

    private lateinit var viewModel: ProductDetailViewModel
    private lateinit var binding: ProductDetailFragmentBinding

    var prod = App.product


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.product_detail_fragment, container, false
        )

        initViewModel()

        initDetails()

        observeViewModel()


        return binding.root


    }

    private fun observeViewModel() {

        viewModel.note.observe(
            viewLifecycleOwner,
            Observer { note ->
               // edt_note_detail_text.text = note.contents.toEditable()
              //  binding.contentProdDetail.tvProdTitle.text = note.title
            }
        )


        viewModel.cartCount.observe(
            viewLifecycleOwner,
            Observer {
                //binding.toolbar.tvCounter.text =
            }
        )
    }

    private fun initDetails() {

        val displayMetrics = DisplayMetrics()
        requireContext().windowManager.defaultDisplay.getMetrics(displayMetrics)

        var width = displayMetrics.widthPixels
        var height = displayMetrics.heightPixels


       var prod_uid = arguments?.getString(KEY_CURRENT_PRODUCT_POSITION)
        Log.d("position_prod_fm",prod_uid.toString())

        viewModel.handleEvent(
            ProductDetailEvent.OnStart(
                prod!!
               // prod_uid.toString()
            )
        )
    }



    private fun initViewModel() {

        viewModel = ViewModelProvider(
            this,
            ProductDetailItemListInjector(requireActivity().application).provideDetailItemViewModelFactory()
        ).get(
            ProductDetailViewModel::class.java
        )


        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)




        var imageList = arrayListOf<String>()
        for(imageurl in prod!!.imageUrls!!){
            imageList.add(imageurl)
        }


        var image_adapter = ProductImagesAdapter(imageList)
        binding.prodImageLayout.vpProductImages.adapter = image_adapter

        binding.prodImageLayout.vpIndicator.setupWithViewPager(binding.prodImageLayout.vpProductImages,true)

        setupListener()



    }

    override fun setupListener() {

        var prod_uid = App.product?.uid

        binding.btnAcProdDetailAddToCart.setOnClickListener {
            viewModel.handleEvent(ProductDetailEvent.OnAddToCartBtnClick(prod_uid.toString()))

        }

        binding.btnAcProdDetailBuyNow.setOnClickListener {

            // go to check out fragment

            val checkout_fagment = CheckOutFragment.newInstance()
            checkout_fagment.fragmentCallBack = object : CheckOutFragment.FragmentCallBack{
                override fun goTo(fragment: Fragment) {
                    fragmentCallBack.goTo(fragment)
                }

                override fun goBack() {
                    fragmentCallBack.goBack()
                }

            }

            fragmentCallBack.goTo(checkout_fagment)

        }

        //back btn

        binding.toolbar.btnBack.setOnClickListener {
            //go back to main activity

            requireActivity().startActivity(
                Intent(
                    activity,
                    MainActivity::class.java
                )
            )


        }


        //on wishlist item click

        binding.toolbar.btnWishlist.setOnClickListener {
            // update the database and change the btn into red

            viewModel.handleEvent(ProductDetailEvent.OnWishListBtnClick)

        }


    }


    /**
     * listener related
     */

    lateinit var fragmentCallBack: FragmentCallBack

    interface FragmentCallBack {
        fun goTo(fragment: Fragment)

        fun goBack()

    }


}
