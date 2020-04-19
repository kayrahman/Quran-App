package com.nkr.fashionita.ui.placeOrder

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.nkr.fashionita.R
import com.nkr.fashionita.base.BaseFragment
import com.nkr.fashionita.databinding.PlaceOrderFragmentBinding
import com.nkr.fashionita.model.App
import com.nkr.fashionita.ui.fragment.account.FirestoreUtil

class PlaceOrderFragment : BaseFragment() {

    companion object {
        fun newInstance() = PlaceOrderFragment()
    }

    private lateinit var viewModel: PlaceOrderViewModel
    private lateinit var binding : PlaceOrderFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.place_order_fragment, container, false
        )
        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PlaceOrderViewModel::class.java)

        setupListener()
    }


    override fun setupListener() {

        binding.btnPlaceOrder.setOnClickListener {
            //show dialog & update firestore database

            msgHelper.toastShort("Your order is being processed")

            var prod = App.product

            FirestoreUtil.updateOrderHistory(prod!!){

                msgHelper.toastShort("Your order has been placed")

            }

        }
    }

}
