package com.nkr.fashionita.ui.fragment.productDetail

import com.nkr.fashionita.model.Product
import com.nkr.fashionita.ui.Item.notedetail.NoteDetailEvent

sealed class ProductDetailEvent{

    data class OnAddToCartBtnClick(val prod_uid : String ) : ProductDetailEvent()
    object OnBuyNowBtnClick : ProductDetailEvent()
    object OnWishListBtnClick : ProductDetailEvent()
    object OnBackBtnClick : ProductDetailEvent()
   // data class OnStart(val noteId: String) : ProductDetailEvent()
    data class OnStart(val prod: Product) : ProductDetailEvent()



}