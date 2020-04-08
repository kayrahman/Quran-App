package com.nkr.fashionita.ui.fragment.cart

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
import com.nkr.fashionita.base.BaseFragment
import com.nkr.fashionita.databinding.CartFragmentBinding
import com.nkr.fashionita.model.Product
import com.nkr.fashionita.note.notelist.buildlogic.CartListItemInjector
import com.nkr.fashionita.note.notelist.buildlogic.WishListItemListInjector
import com.nkr.fashionita.ui.adapter.cart.CartListGridAdapter
import com.nkr.fashionita.ui.dialog.RemoveCartItemAlertDialog
import com.nkr.fashionita.ui.fragment.cart.buildlogic.CartListEvent
import com.nkr.fashionita.ui.fragment.wishlist.WishlistViewModel

class CartFragment : BaseFragment() {

    companion object {
        fun newInstance() = CartFragment()
    }

    private lateinit var viewModel: CartViewModel
    private lateinit var binding: CartFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.cart_fragment, container, false
        )
        return binding.root


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            CartListItemInjector(requireActivity().application).provideCartListItemViewModelFactory()
        ).get(
            CartViewModel::class.java
        )

        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        setupListener()

        observeViewModel()

    }

     override fun setupListener() {
        viewModel.cartGridAdapter.listener = object:CartListGridAdapter.AdapterListener{
            override fun onItemClick(product: Product) {

                //
            }

            override fun onCheckBoxClick(isCheck:Boolean,price: Int,prod_uid:String) {

                viewModel.handleEvent(CartListEvent.OnCheckBoxItemClick(isCheck,price,prod_uid))

            }

        }

        binding.ivBackFmCart.setOnClickListener {
            cartListfragmentCallBack.goBack()

        }

        binding.ivRemoveFmCart.setOnClickListener {
            showAlertDialog()
        }



    }

    private fun showAlertDialog() {
        val alertDialog = RemoveCartItemAlertDialog()

        alertDialog.removeCartItemListener = object : RemoveCartItemAlertDialog.RemoveCartItemDialogListener {
            override fun onYesBtnClick() {
                //remove selected items from recyclerview
                viewModel.handleEvent(CartListEvent.OnDeleteCheckedItemClick)

                alertDialog.dismiss()

            }

            override fun onNoBtnClick() {

                alertDialog.dismiss()

            }


        }


       fragmentHelper.showDialogFragment(childFragmentManager,"RemoveCartItemDialog",alertDialog)

    }


    private fun observeViewModel() {
            viewModel.cartListItems.observe(
                this,
                Observer {it->
                    viewModel.cartGridAdapter.submitList(it)

                }
            )
        }


    override fun onStart() {
        super.onStart()

        viewModel.handleEvent(CartListEvent.OnStart)
    }



    /**
     * listener related
     */
    lateinit var cartListfragmentCallBack: FragmentCallBack

    interface FragmentCallBack {
        fun goTo(fragment: Fragment)

        fun goBack()


    }




}
