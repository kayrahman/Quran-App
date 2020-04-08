package com.nkr.fashionita.ui.fragment.cart.buildlogic

import com.nkr.fashionita.model.Product

sealed class CartListEvent {
    data class OnNoteItemClick(val position: Int) : CartListEvent()
    object OnStart : CartListEvent()
    data class OnCheckBoxItemClick(val isCheck:Boolean,val price : Int,val prod_uid:String) : CartListEvent()
    object OnDeleteCheckedItemClick : CartListEvent()
}