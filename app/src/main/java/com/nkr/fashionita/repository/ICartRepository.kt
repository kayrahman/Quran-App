package com.nkr.fashionita.repository

import com.nkr.fashionita.common.Result
import com.nkr.fashionita.model.Product


interface ICartRepository {

  //  suspend fun getAllCartItems():Result<Exception, List<String>>

    suspend fun getCartKeys() : Result<Exception,List<String>>
    suspend fun getAllProductsFromCartlist(list_keys:List<String>) : Result<Exception,List<Product>>
    suspend fun updateCartRepo(prodUid: String): Result<Exception, Unit>
    suspend fun removeCartItemsFromRemote(list_prod_uid : String) : Result<Exception,Unit>

}