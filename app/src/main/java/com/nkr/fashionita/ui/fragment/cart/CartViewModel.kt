package com.nkr.fashionita.ui.fragment.cart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nkr.fashionita.common.BaseViewModel
import com.nkr.fashionita.common.Result
import com.nkr.fashionita.model.Product
import com.nkr.fashionita.repository.ICartRepository
import com.nkr.fashionita.ui.adapter.cart.CartListGridAdapter
import com.nkr.fashionita.ui.fragment.cart.buildlogic.CartListEvent
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class CartViewModel(
  val cartRepo: ICartRepository,
    uiContext: CoroutineContext
) : BaseViewModel<CartListEvent>(uiContext) {


    val cartGridAdapter = CartListGridAdapter()


    private val cartListItemsState = MutableLiveData<List<Product>>()
    val cartListItems : LiveData<List<Product>> get() = cartListItemsState

    val totalPrice = MutableLiveData<Int>()

    val listItemsToBeRemoved = arrayListOf<String>()


    override fun handleEvent(event: CartListEvent) {
        when (event){

            is CartListEvent.OnStart -> populateCartItems()
            is CartListEvent.OnCheckBoxItemClick -> addOrDeductFromTotalPrice(event.isCheck,event.price,event.prod_uid)
            is CartListEvent.OnDeleteCheckedItemClick -> removeItemsFromAdapter()

        }


    }

    private fun removeItemsFromAdapter() = launch {

        listItemsToBeRemoved.forEach {
           when(cartRepo.removeCartItemsFromRemote(it)){
               is Result.Value -> {
                   //notify adapter about the delete operation
                 //  cartGridAdapter.notifyItemChanged(position,true)

               }
           }

        }


        populateCartItems()



    }

    private fun addOrDeductFromTotalPrice(
        isCheck: Boolean,
        price: Int,
        prodUid: String
    ) {

        Log.d("total_price",totalPrice.value.toString())

        if(!isCheck){
            totalPrice.value = totalPrice.value?.minus( price)

            listItemsToBeRemoved.remove(prodUid)

        }else{
            totalPrice.value = totalPrice.value?.plus(price)

            listItemsToBeRemoved.add(prodUid)
        }


    }

    private fun populateCartItems() =launch {
       var cartResponse = cartRepo.getCartKeys()

        when(cartResponse){
            is Result.Value ->{

                getAllProductsWithCartList(cartResponse.value)

            }
            is Result.Error -> Log.d("wishlist_keys","error")
        }



    }

    private fun getAllProductsWithCartList(list_keys: List<String>) = launch {

        val response = cartRepo.getAllProductsFromCartlist(list_keys)

        when(response){
            is Result.Value ->{

                cartListItemsState.value = response.value
                totalPrice.value = showTotalPrice()


                cartListItemsState.value!!.forEach {
                    listItemsToBeRemoved.add(it.uid)
                }

            }
            is Result.Error -> Log.d("cart_keys","error")
        }


    }

    private fun showTotalPrice():Int{

        var total_price = 0
        cartListItemsState.value?.forEach {
            total_price += it.price.toInt()

        }


        return total_price
    }


}
