package com.nkr.fashionita.ui.dialog


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

import com.nkr.fashionita.R
import kotlinx.android.synthetic.main.fragment_remove_cart_item_alert_dialog.*

/**
 * A simple [Fragment] subclass.
 */
class RemoveCartItemAlertDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_remove_cart_item_alert_dialog, container, false)
    }


    override fun onStart() {
        super.onStart()

        btn_remove_cart_item_no.setOnClickListener {
            removeCartItemListener.onNoBtnClick()

        }

        btn_remove_cart_item_yes.setOnClickListener {
            removeCartItemListener.onYesBtnClick()
        }


    }


    lateinit var removeCartItemListener  : RemoveCartItemDialogListener

    interface RemoveCartItemDialogListener {

        fun onYesBtnClick()
        fun onNoBtnClick()
    }




}
