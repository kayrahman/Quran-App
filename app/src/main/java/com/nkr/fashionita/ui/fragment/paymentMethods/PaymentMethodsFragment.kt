package com.nkr.fashionita.ui.fragment.paymentMethods

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.nkr.fashionita.R
import com.nkr.fashionita.base.BaseFragment
import com.nkr.fashionita.databinding.PaymentFragmentBinding
import com.nkr.fashionita.ui.placeOrder.PlaceOrderFragment

//redirected from check_out_fragment

class PaymentMethodsFragment : BaseFragment() {

    companion object {
        fun newInstance() = PaymentMethodsFragment()
    }

    private lateinit var viewModel: PaymentViewModel
    private lateinit var binding: PaymentFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.payment_fragment, container, false
        )
        return binding.root

    //    return inflater.inflate(R.layout.payment_fragment, container, false)


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PaymentViewModel::class.java)
      setupListener()
    }


    override fun setupListener() {


        binding.btnProceedToPay.setOnClickListener {
            val place_order_fragment = PlaceOrderFragment.newInstance()
            fragmentCallBack.goTo(place_order_fragment)

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
