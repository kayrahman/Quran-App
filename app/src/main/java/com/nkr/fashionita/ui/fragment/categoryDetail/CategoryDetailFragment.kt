package com.nkr.fashionita.ui.fragment.categoryDetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider

import com.nkr.fashionita.R
import com.nkr.fashionita.databinding.CategoryDetailFragmentBinding
import com.nkr.fashionita.ui.fragment.productDetail.ProductDetailFragment
import com.nkr.fashionita.util.KEY_CATEGORY_NAME_CATEGORY_DETAIL
import com.wiseassblog.jetpacknotesmvvmkotlin.note.notelist.buildlogic.CategoryDetailListInjector

class CategoryDetailFragment : Fragment() {

    companion object {
        fun newInstance(category_uid: String) =
            CategoryDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_CATEGORY_NAME_CATEGORY_DETAIL, category_uid)
                }
            }

    }



    private lateinit var viewModel: CategoryDetailViewModel
    private lateinit var binding: CategoryDetailFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.category_detail_fragment, container, false
        )


        return binding.root


    }

    private fun initDetails() {

        val category_name = arguments?.getString(KEY_CATEGORY_NAME_CATEGORY_DETAIL)
      //  Log.d("category_name",category_name.toString())

        if(!category_name.isNullOrEmpty()){

            viewModel.handleEvent(CategoryListEvent.OnStart(category_name))

            binding.layoutToolbar.etSearch.hint = category_name

        }else{

            Log.d("category_name","not found")
        }

        binding.layoutToolbar.ivBackCategoryDetail.setOnClickListener {
           fragmentCallBack.goBack()
        }




    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            CategoryDetailListInjector(requireActivity().application).provideCategoryViewModelFactory()
        ).get(
            CategoryDetailViewModel::class.java
        )


        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        initDetails()


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
