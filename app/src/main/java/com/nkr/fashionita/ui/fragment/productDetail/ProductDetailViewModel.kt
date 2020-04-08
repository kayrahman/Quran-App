package com.nkr.fashionita.ui.fragment.productDetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nkr.fashionita.common.BaseViewModel
import com.nkr.fashionita.common.Result
import com.nkr.fashionita.common.GET_NOTE_ERROR
import com.nkr.fashionita.model.App
import com.nkr.fashionita.model.Product
import com.nkr.fashionita.repository.ICartRepository
import com.nkr.fashionita.repository.IProductRepository
import com.nkr.fashionita.repository.WishRepoImpl
import com.nkr.fashionita.util.default
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.CoroutineContext

class ProductDetailViewModel(val cartRepo: ICartRepository,
                             val prodRepo:IProductRepository,
                             uiContext: CoroutineContext
)  : BaseViewModel<ProductDetailEvent>(uiContext) {


    private val noteState = MutableLiveData<Product>()
    val note: LiveData<Product> get() = noteState

    var product = MutableLiveData<Product>()

    var cartCount = MutableLiveData<Int>()

    var imgUrl  = MutableLiveData<String>()
    var prodName  = MutableLiveData<String>()
    var prodPrice  = MutableLiveData<String>()
    val isSavedInWishList = MutableLiveData<Boolean>().default(false)

    private val isAddedToWishList = MutableLiveData<Boolean>()
    val wishListBtnState : LiveData<Boolean> get() = isAddedToWishList




    override fun handleEvent(event: ProductDetailEvent) {
        when (event) {
            is ProductDetailEvent.OnStart ->
            {
                cartCount.value = App.cartCount

                product.value = event.prod

                prodName.value = product.value!!.title
                isSavedInWishList.value = product.value!!.isWishList

                Log.d("prod_title", product.value!!.title.toString())
                Log.d("isSavedToWishlist", product.value!!.isWishList.toString())

            }


            is ProductDetailEvent.OnAddToCartBtnClick -> updateCartDatabase(event.prod_uid)

            is ProductDetailEvent.OnWishListBtnClick -> updateWishList()
        }

    }

    private fun updateWishList() = launch {
        Log.d("Prod_Uid",product.value?.uid)

        val wishRepo = WishRepoImpl()

        if(!product.value?.uid.isNullOrEmpty()) {

            //check if the item is already saved

            if (isSavedInWishList.value!!){

                // and delete entry from database

                val wishDeleteResponse = wishRepo.deleteItemFromWishList(product.value?.uid.toString())
                when (wishDeleteResponse) {
                    is Result.Value -> {

                        isSavedInWishList.value = false
                        Log.d("wishListUpdate","item deleted successfully")

                    }
                    is Result.Error -> {
                        // show error msg
                        Log.d("wishListUpdate","item deleted error")
                    }
                }


            }else {

                val wishResponse = wishRepo.addItemToWishList(product.value?.uid.toString())

                when (wishResponse) {
                    is Result.Value -> {
                        // make the wishbtn turn into red
                     //   isAddedToWishList.value = true

                        isSavedInWishList.value = true

                        Log.d("wishListUpdate","item added successfully")


                    }
                    is Result.Error -> {
                        // show error msg

                        Log.d("wishListUpdate","item adding error")
                    }
                }

            }

        }





    }

    private fun updateCartDatabase(prodUid:String) = launch {

        Log.d("prod_uid",prodUid)
        if(!prodUid.isNullOrEmpty()){

        val updateCartResponse = cartRepo.updateCartRepo(prodUid)
        when(updateCartResponse){
            is Result.Value ->  {
                // add to cart icon
                cartCount.value = cartCount.value?.plus(1)

                Log.d("cart_update","success")
            }

            is Result.Error -> {
                Log.d("cart_update","error")
                errorState.value = "Error"

            }
        }

        }


    }



    private fun initialProductDetail(){

        imgUrl.value = noteState.value?.thumbImageUrl
        prodName.value = noteState.value?.title
        prodPrice.value = noteState.value?.price


    }


    private fun getCalendarTime(): String {
        val cal = Calendar.getInstance(TimeZone.getDefault())
        val format = SimpleDateFormat("d MMM yyyy HH:mm:ss Z")
        format.timeZone = cal.timeZone
        return format.format(cal.time)
    }

}
