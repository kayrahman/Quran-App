package com.nkr.fashionita.ui.fragment.account


import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.firebase.ui.auth.AuthUI
import com.google.android.material.appbar.AppBarLayout
import com.nkr.fashionita.R
import com.nkr.fashionita.base.BaseFragment
import com.nkr.fashionita.databinding.FragmentMyAccountBinding
import com.nkr.fashionita.note.notelist.buildlogic.MyAccountInjector
import com.nkr.fashionita.note.notelist.buildlogic.ProductDetailItemListInjector
import com.nkr.fashionita.ui.MainActivity
import com.nkr.fashionita.ui.fragment.main.PostParentFragment
import com.nkr.fashionita.ui.fragment.productDetail.ProductDetailViewModel
import com.nkr.fashionita.ui.fragment.settingsFragment.SettingsFragment
import com.nkr.fashionita.ui.login.LoginActivity

import kotlinx.android.synthetic.main.fragment_my_account.*
import kotlinx.android.synthetic.main.header_account.*
import kotlinx.android.synthetic.main.order_status_layout.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.newTask
import org.jetbrains.anko.support.v4.intentFor
import java.io.ByteArrayOutputStream


class MyAccountFragment : BaseFragment() {



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment PostParentFragment.
         */
        @JvmStatic
        fun newInstance() =
            MyAccountFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    private lateinit var binding: FragmentMyAccountBinding
    private lateinit var viewModel: MyAccountViewModel


    private val RC_SELECT_IMAGE = 2
    private lateinit var selectedImageBytes: ByteArray
    private var pictureJustChanged = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_my_account, container, false)


        initViewModel()

        return binding.root
    }

    private fun initPager() {

        viewModel.handleEvent(MyAccountEvent.InitPager(childFragmentManager))

        //viewModel.initPager(childFragmentManager)

        binding.listingLayout.vpSearchResult.adapter = viewModel.commonPagerAdapter
        binding.listingLayout.tlySearchResult.setupWithViewPager(binding.listingLayout.vpSearchResult)
      // binding.listingLayout.vpSearchResult.currentItem = page_num


    }

    override fun onStart() {
        super.onStart()

        viewModel.handleEvent(MyAccountEvent.OnStart)

        initPager()

        observeViewModel()

    }

    private fun observeViewModel() {

        viewModel.thumb_image.observe(
            this,
            Observer { imgUrl ->

                Glide.with(this)
                    .load(imgUrl)
                    .apply(
                        RequestOptions()
                            .fitCenter()
                            .placeholder(R.drawable.avatar_female)
                    )
                    .apply(RequestOptions.skipMemoryCacheOf(true))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))

                    .into(binding.header.ivDp)


            }
        )
    }


    private fun initViewModel() {

        viewModel = ViewModelProvider(
            this,
            MyAccountInjector(requireActivity().application).provideMyAccountViewModelFactory()
        ).get(
            MyAccountViewModel::class.java
        )


        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)


        setupListener()


    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_SELECT_IMAGE && resultCode == Activity.RESULT_OK &&
                data != null && data.data != null) {
            val selectedImagePath = data.data
            val selectedImageBmp = MediaStore.Images.Media
                    .getBitmap(activity?.contentResolver, selectedImagePath)

            val outputStream = ByteArrayOutputStream()
            selectedImageBmp.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
            selectedImageBytes = outputStream.toByteArray()

            Glide.with(this)
                    .load(selectedImageBytes)
                    .into(iv_dp)

            pictureJustChanged = true
        }
    }


    override fun setupListener() {
        binding.header.settingImage.setOnClickListener {
            startSettingFragment()
        }
    }

    private fun startSettingFragment() {

        val settingFragment = SettingsFragment.newInstance()
        settingFragment.settingfragmentCallBack = object: SettingsFragment.SettingsFragmentCallBack{
            override fun goTo(fragment: Fragment) {

                myAccfragmentCallBack.goTo(fragment)
            }

            override fun goBack() {
                myAccfragmentCallBack.goBack()
            }
        }
        myAccfragmentCallBack.goTo(settingFragment)

    }


    /**
     * listener related
     */
    lateinit var myAccfragmentCallBack: MyAccFragmentCallBack

    interface MyAccFragmentCallBack {
        fun goTo(fragment: Fragment)
        fun goBack()


    }


}
