package com.nkr.fashionita.ui.fragment.wishlist

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.nkr.fashionita.R
import com.nkr.fashionita.databinding.WishlistFragmentBinding
import com.nkr.fashionita.model.Product
import com.nkr.fashionita.note.notelist.buildlogic.WishListItemListInjector
import com.nkr.fashionita.ui.Item.notelist.NoteListEvent
import com.nkr.fashionita.ui.adapter.homeTab.FreshFindsGridAdapter
import com.nkr.fashionita.ui.adapter.wishlist.WishListGridAdapter
import com.nkr.fashionita.ui.fragment.homeTab.HomeViewModel


class WishlistFragment : Fragment() {

    companion object {
        fun newInstance() = WishlistFragment()
    }

    private lateinit var viewModel: WishlistViewModel
    private lateinit var binding: WishlistFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.wishlist_fragment, container, false
        )
        return binding.root
    }


    override fun onStart() {
        super.onStart()

        viewModel.handleEvent(
            WishListEvent.OnStart
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(
            this,
           WishListItemListInjector(requireActivity().application).provideWishListItemViewModelFactory()
        ).get(
            WishlistViewModel::class.java
        )

        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        setupListener()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.wishListItems.observe(
            this,
            Observer {it->
                viewModel.wishListAdapter.submitList(it)

            }
        )
    }

    private fun setupListener() {

        binding.ivBackFmWishlist.setOnClickListener {
            wishListfragmentCallBack.goBack()
        }


        viewModel.wishListAdapter.listener = object : WishListGridAdapter.AdapterListener{

            override fun onItemClick(product : Product) {
             //   startProductDetailFragment(product)
            }

            override fun onWishListIconClick(uid: String, isAlreadySaved: Boolean,item_position:Int) {
                viewModel.handleEvent(WishListEvent.OnWishIconClick(uid,isAlreadySaved,item_position))

            }


        }


    }


    /**
     * listener related
     */
    lateinit var wishListfragmentCallBack: FragmentCallBack

    interface FragmentCallBack {
        fun goTo(fragment: Fragment)

        fun goBack()


    }



}
