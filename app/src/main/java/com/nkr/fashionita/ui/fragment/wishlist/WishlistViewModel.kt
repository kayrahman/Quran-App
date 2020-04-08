package com.nkr.fashionita.ui.fragment.wishlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nkr.fashionita.common.BaseViewModel
import com.nkr.fashionita.common.Result
import com.nkr.fashionita.model.Product
import com.nkr.fashionita.repository.IWishListRepository
import com.nkr.fashionita.ui.adapter.wishlist.WishListGridAdapter
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class WishlistViewModel(val wishRepo: IWishListRepository,
                        uiContext: CoroutineContext
) : BaseViewModel<WishListEvent>(uiContext){


    private val wishListItemsState = MutableLiveData<List<Product>>()
    val wishListItems : LiveData<List<Product>> get() = wishListItemsState

    val wishListAdapter = WishListGridAdapter()


    override fun handleEvent(event: WishListEvent) {
        when(event){
            is WishListEvent.OnStart -> populateWishListAdapter()
            is WishListEvent.OnWishIconClick -> handleWishIconClick(event.uid,event.isAlreadySaved,event.position)
        }

    }

    private fun handleWishIconClick(uid: String, isFavourite: Boolean,adapter_position:Int) = launch {

        Log.d("isfav",isFavourite.toString()+"uid:"+uid)

        if(!isFavourite) {

            val postListWish = wishRepo.addItemToWishList(uid)

            when (postListWish) {
                is Result.Value -> {

                    wishListAdapter.notifyItemChanged(adapter_position,true)

                }
                is Result.Error ->  {
                    Log.d("wishlistUpdate",errorState.value.toString())
                }
            }
        }else{

            var deleteResponse = wishRepo.deleteItemFromWishList(uid)

            when (deleteResponse) {
                is Result.Value -> {
                    wishListAdapter.notifyItemChanged(adapter_position,false)
                }
                is Result.Error -> {
                    Log.d("wishlistUpdate",errorState.value.toString())
                }
            }

        }


    }

    private fun populateWishListAdapter() = launch {

        //get all the keys from wishlist compare against productlist add in a list and update adapter
        val wishkeys = wishRepo.getWishKeys()
        when(wishkeys){
            is Result.Value ->{

                getAllProductsWithWishList(wishkeys.value)

                Log.d("wishlist_keys", wishkeys.value.toString())

            }
            is Result.Error -> Log.d("wishlist_keys","error")
        }

    }

    private fun getAllProductsWithWishList(list_keys: List<String>)=launch {
            val response = wishRepo.getAllProductsFromWishlist(list_keys)

        when(response){
            is Result.Value ->{

              //  wishListAdapter.updateWishLIstItem(response.value)
                wishListItemsState.value = response.value
            }
            is Result.Error -> Log.d("wishlist_keys","error")
        }

    }


}
