package com.nkr.fashionita.repository

import com.nkr.fashionita.model.Product
import com.nkr.fashionita.common.Result


interface IWishListRepository {

    /**
     * all abput wishlist
     * **/
    suspend fun getWishKeys():Result<Exception, List<String>>
    suspend fun getAllProductsFromWishlist(listKeys: List<String>): Result<Exception, List<Product>>
    suspend fun addItemToWishList(product_uid : String): Result<Exception, Unit>
    suspend fun deleteItemFromWishList(product_uid : String): Result<Exception, Unit>


}