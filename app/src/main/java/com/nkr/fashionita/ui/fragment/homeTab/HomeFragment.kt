package com.nkr.fashionita.ui.fragment.homeTab

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.nkr.fashionita.R
import com.nkr.fashionita.base.BaseFragment
import com.nkr.fashionita.databinding.HomeFragmentBinding
import com.nkr.fashionita.glide.GlideApp
import com.nkr.fashionita.model.App
import com.nkr.fashionita.model.BannerItem
import com.nkr.fashionita.model.Product
import com.nkr.fashionita.ui.Item.notelist.NoteListEvent
import com.nkr.fashionita.ui.fragment.productDetail.ProductDetailActivity
import com.nkr.fashionita.ui.adapter.homeTab.CategoryImageListAdapter
import com.nkr.fashionita.ui.adapter.homeTab.FreshFindsGridAdapter
import com.nkr.fashionita.ui.fragment.account.FirestoreUtil
import com.nkr.fashionita.ui.fragment.cart.CartFragment
import com.nkr.fashionita.ui.fragment.categoryDetail.CategoryDetailFragment
import com.nkr.fashionita.ui.fragment.search.SearchResultFragment
import com.nkr.fashionita.ui.fragment.wishlist.WishlistFragment
import technolifestyle.com.imageslider.FlipperView

class HomeFragment : BaseFragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: HomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.home_fragment, container, false)

        initViewModel()

        initNotificationList()

        return binding.root

    }

    private fun initNotificationList() {

        viewModel.handleEvent(
            NoteListEvent.OnStart
        )


        viewModel.bannerList.observe(
            this,
            Observer {
                showBannerItems(it)
            }
        )

        viewModel.loading.observe(
            this,
            Observer {
                Log.d("loading",it.toString())
            }

        )

        viewModel.error.observe(
            this,
            Observer {
                Log.d("error",it.toString())
            }

        )


        viewModel.cartCount.observe(
            this,
            Observer {
              //  binding.layoutToolbarHome.tvCounter.text = it.toString()

                Log.d("tv_counter",it.toString())
            }
        )

        viewModel.wishListCount.observe(
            this,
            Observer {
              //  binding.layoutToolbarHome.tvCounterWishList.text = it.toString()
            }
        )

    }


    private fun initViewModel() {
       // viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)


        viewModel = ViewModelProvider(
            this,
            HomeItemListInjector(requireActivity().application).provideNoteListViewModelFactory()
        ).get(
            HomeViewModel::class.java
        )


        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setupListener()
    }

    override fun setupListener() {

      /*  binding.layoutToolbarHome.etSearch.setOnFocusChangeListener{view, hasFocus ->
            if(hasFocus){
                startSearchResultFragment()
            }

        }*/


        binding.layoutToolbarHome.etSearchHome.setOnClickListener{
            startSearchResultFragment()

        }


        //fresh find fragment
        viewModel.freshFindsGridAdapter.listener = object : FreshFindsGridAdapter.AdapterListener{

            override fun onWishListItemClick(prod_uid: String,isFavourite:Boolean,adapter_position : Int) {
                //update the database

                Log.d("home_is_fav",isFavourite.toString())
                viewModel.handleEvent(NoteListEvent.OnWishListItemClick(prod_uid,isFavourite,adapter_position))

            }

            override fun onItemClick(product : Product) {
                startProductDetailFragment(product)
            }

        }

        viewModel.categoryItemAdapter.listener = object:CategoryImageListAdapter.AdapterListener{
            override fun onItemClick(category_uid: String) {
                startCategoryDetailFragment(category_uid)
            }

            override fun errorToast(msg: String) {

            }

        }

        //wishlist fragment

        binding.layoutToolbarHome.ivWishList.setOnClickListener {
            goToWishListFragment()
        }


        //cartlist fragment

        binding.layoutToolbarHome.ivCartList.setOnClickListener {
            goToCartListFragment()
        }


    }

    private fun goToCartListFragment() {
        val fm_cart_list = CartFragment.newInstance()
        fm_cart_list.cartListfragmentCallBack = object :CartFragment.FragmentCallBack{
            override fun goTo(fragment: Fragment) {

            }

            override fun goBack() {
                fragmentCallBack.goBack()
            }

        }
        fragmentCallBack.goTo(fm_cart_list)
    }

    private fun goToWishListFragment() {
        val fm_wish_list = WishlistFragment.newInstance()
        fm_wish_list.wishListfragmentCallBack = object : WishlistFragment.FragmentCallBack{
            override fun goTo(fragment: Fragment) {

            }

            override fun goBack() {

                fragmentCallBack.goBack()
            }

        }
        fragmentCallBack.goTo(fm_wish_list)
    }

    private fun startCategoryDetailFragment(category_uid:String) {
      //  val fm_category_detail = CategoryDetailFragment()
        val fm_category_detail = CategoryDetailFragment.newInstance(category_uid)
        fm_category_detail.fragmentCallBack = object :CategoryDetailFragment.FragmentCallBack{
            override fun goTo(fragment: Fragment) {
            }

            override fun goBack() {
                fragmentCallBack.goBack()
            }

        }
        fragmentCallBack.goTo(fm_category_detail)

    }

    private fun startSearchResultFragment() {

        val fm_search_result = SearchResultFragment.newInstance()
        fm_search_result.fragmentCallBack = object :SearchResultFragment.FragmentCallBack{
            override fun goTo(fragment: Fragment) {


            }

            override fun goBack() {
                fragmentCallBack.goBack()
            }

        }
        fragmentCallBack.goTo(fm_search_result)


    }

    private fun startProductDetailFragment(product: Product) {

        App.product = product

        requireActivity().startActivity(
            Intent(
                activity,
                ProductDetailActivity::class.java
            ))


    }


    private fun showBannerItems(it: List<BannerItem>) {

        for (item in it) {

            val view = FlipperView(context!!)

            view.setImageUrl(item.image_url) { imageView, image ->

                GlideApp.with(requireActivity())
                    .load(image)
                    .into(imageView)

            }

            view.setImageScaleType(ImageView.ScaleType.FIT_XY)

           binding.flipperLayout.addFlipperView(view)
           binding.flipperLayout.scrollTimeInSec = 3

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
