package com.nkr.fashionita.ui.fragment.account

import androidx.fragment.app.FragmentManager

sealed class MyAccountEvent{

    object OnStart : MyAccountEvent()
    data class InitPager(val fm:FragmentManager) : MyAccountEvent()
    object OnPopulateUserListing : MyAccountEvent()

}