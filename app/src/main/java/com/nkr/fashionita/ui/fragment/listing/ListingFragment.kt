package com.nkr.fashionita.ui.fragment.listing

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.Timestamp
import com.nkr.fashionita.R
import com.nkr.fashionita.base.BaseFragment
import com.nkr.fashionita.databinding.ListingFragmentBinding
import com.nkr.fashionita.model.Product
import com.nkr.fashionita.note.notelist.buildlogic.MyAccountInjector
import com.nkr.fashionita.ui.adapter.userListing.UserListingGridAdapter
import com.nkr.fashionita.ui.fragment.account.MyAccountEvent
import com.nkr.fashionita.ui.fragment.account.MyAccountViewModel



class ListingFragment : BaseFragment() {

    companion object {
        fun newInstance() = ListingFragment()
    }

    private lateinit var viewModel: MyAccountViewModel
    private lateinit var binding: ListingFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.listing_fragment, container, false)

        return binding.root

        //return inflater.inflate(R.layout.listing_fragment, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

  //      viewModel = ViewModelProviders.of(this).get(ListingViewModel::class.java)


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

    override fun setupListener() {

       viewModel.userListingGridAdapter.listener = object : UserListingGridAdapter.AdapterListener{
            override fun onItemClick(product: Product) {


            }

            override fun onMoreIconClick(view : View,prod_uid: String) {
                
               //inflate a menu
                showListingMenuDialog(view,prod_uid)

            }


        }

    }

    private fun showListingMenuDialog(view: View, prodUid: String) {

        val popupMenu = PopupMenu(requireContext(), view)
        popupMenu.inflate(R.menu.menu_user_listing_item)

        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_delete ->
                    //delete product
                    true
                R.id.menu_share ->                         //handle menu2 click
                    true

                else -> false
            }
        })

        popupMenu.show()


/*

        val userListingMenuDialog = UserListingItemMenuDialog.newInstance()
        fragmentHelper.showDialogFragment(childFragmentManager,NAME_DIALOG_USER_LISTING_MENU,userListingMenuDialog)
*/

    }

    override fun onStart() {
        super.onStart()

        viewModel.handleEvent(MyAccountEvent.OnPopulateUserListing)

        
    }

}
