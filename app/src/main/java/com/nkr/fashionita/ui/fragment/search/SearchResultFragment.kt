package com.nkr.fashionita.ui.fragment.search

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider

import com.nkr.fashionita.R
import com.nkr.fashionita.base.BaseFragment
import com.nkr.fashionita.databinding.SearchResultFragmentBinding
import com.wiseassblog.jetpacknotesmvvmkotlin.note.notelist.buildlogic.SearchItemListInjector
import android.text.Editable
import android.text.TextWatcher




class SearchResultFragment : BaseFragment() {

    companion object {
        fun newInstance() = SearchResultFragment()
    }

    private lateinit var viewModel: SearchResultViewModel
    private lateinit var binding: SearchResultFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = DataBindingUtil.inflate(
            inflater, R.layout.search_result_fragment, container, false)

        initViewModel()


        return binding.root
    }


    private fun initViewModel() {

        viewModel = ViewModelProvider(
            this,
            SearchItemListInjector(requireActivity().application).provideSearchViewModelFactory()
        ).get(SearchResultViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setupListener()
    }

    override fun setupListener() {


        binding.layoutToolbar?.etSearch?.requestFocus()
        binding.layoutToolbar?.etSearch?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (charSequence.toString().length == 0) {

                } // This is used as if user erases the characters in the search field.
                else {

                   // viewModel.populateSearchItemList(charSequence.toString())

                }

            }

            override fun afterTextChanged(editable: Editable) {
                viewModel.populateSearchItemList(editable.toString())

            }
        })


        //search
        binding.layoutToolbar?.etSearch?.setOnEditorActionListener() {
                v, actionId, event ->
            Log.d("SearchPostsMAinSearch", "searching....")
            if (actionId == EditorInfo.IME_ACTION_SEARCH
                || actionId == EditorInfo.IME_ACTION_DONE
                || event.action == KeyEvent.ACTION_DOWN
                && event.keyCode == KeyEvent.KEYCODE_ENTER) {

                //save searches into room database

                //show search result into recyclerview

              //  populateSearchResult()

              //  startSearchResultFragment(binding.layoutToolbar?.etSearch?.text.toString())
                true
            }
            // Return true if you have consumed the action, else false.
            false
        }


        binding.layoutToolbar.ivBackSearchResult.setOnClickListener {
            fragmentCallBack.goBack()
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
