package com.nkr.fashionita.ui.fragment.productListing

import com.nkr.fashionita.model.Product

sealed class ProductListingEvent {
    object OnStart : ProductListingEvent()
    data class OnPostProductBtnClick(
        val prod: Product,
       val byteList: ArrayList<ByteArray>
    ) : ProductListingEvent()


}