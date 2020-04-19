package com.nkr.fashionita.ui.fragment.checkoutFragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.nkr.fashionita.R
import com.nkr.fashionita.base.BaseFragment
import com.nkr.fashionita.databinding.CheckOutFragmentBinding
import com.nkr.fashionita.ui.fragment.paymentMethods.PaymentMethodsFragment

class CheckOutFragment : BaseFragment() {

    companion object {
        fun newInstance() = CheckOutFragment()
    }

    private lateinit var viewModel: CheckOutViewModel
    private lateinit var binding : CheckOutFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.check_out_fragment, container, false
        )


        return binding.root
        //return inflater.inflate(R.layout.check_out_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CheckOutViewModel::class.java)
        // TODO: Use the ViewModel


        setupListener()

    }



   override fun setupListener(){

       binding.ivBack.setOnClickListener {
           fragmentCallBack.goBack()
       }

       binding.btnProceedToPay.setOnClickListener {
           val payment_fragment = PaymentMethodsFragment.newInstance()
           payment_fragment.fragmentCallBack = object : PaymentMethodsFragment.FragmentCallBack{
               override fun goTo(fragment: Fragment) {
                   fragmentCallBack.goTo(fragment)
               }

               override fun goBack() {

               }
           }
           fragmentCallBack.goTo(payment_fragment)
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
