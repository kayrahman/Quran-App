package com.nkr.fashionita.ui.fragment.homeTab

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nkr.fashionita.common.BaseViewModel
import com.nkr.fashionita.common.Result
import com.nkr.fashionita.model.App
import com.nkr.fashionita.model.BannerItem
import com.nkr.fashionita.model.CategoryItem
import com.nkr.fashionita.model.Product
import com.nkr.fashionita.repository.CartRepoImpl
import com.nkr.fashionita.repository.IProductRepository
import com.nkr.fashionita.repository.IWishListRepository
import com.nkr.fashionita.ui.Item.notelist.NoteListEvent
import com.nkr.fashionita.ui.adapter.homeTab.CategoryImageListAdapter
import com.nkr.fashionita.ui.adapter.homeTab.FreshFindsGridAdapter
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class HomeViewModel(
    val productRepo: IProductRepository,
    val wishRepo: IWishListRepository,
    uiContext: CoroutineContext
) : BaseViewModel<NoteListEvent>(uiContext) {

    val freshFindsGridAdapter = FreshFindsGridAdapter()
    val categoryItemAdapter = CategoryImageListAdapter()

    private val bannerListState = MutableLiveData<List<BannerItem>>()
    val bannerList: LiveData<List<BannerItem>> get() = bannerListState

    private var productList = arrayListOf<Product>()

    private val mutableCategoryList = MutableLiveData<List<CategoryItem>>()
    val categoryList: LiveData<List<CategoryItem>> get() = mutableCategoryList

    val cartCount = MutableLiveData<Int>()
    val wishListCount = MutableLiveData<Int>()





    override fun handleEvent(event: NoteListEvent) {

        when (event) {
            is NoteListEvent.OnStart -> {
                getWishKeyFromDb()
                getbanners()
                addCategoryItemList()
                showCartNotificationCount()
                showWishListItemCount()
                Log.d("item_home", "here")
            }

            is NoteListEvent.OnWishListItemClick -> updateWishListDb(event.prod_uid,event.isFavourite,event.adapter_position)

        }
    }

    private fun showWishListItemCount() =launch {

        val cartRepo = CartRepoImpl()
        val wishListCountResponse = cartRepo.getWishListItemCount()

        when(wishListCountResponse){
            is Result.Value ->{
                wishListCount.value = wishListCountResponse.value
                App.wishCount = wishListCount.value

                Log.d("notifiction", wishListCountResponse.value.toString())
            }
            is Result.Error ->{
                Log.d("notifiction", "error")
            }
        }

    }

    private fun showCartNotificationCount() = launch {
        val cartRepo = CartRepoImpl()

        val repoCount = cartRepo.getCartItemCount()

        when(repoCount){
            is Result.Value ->{
                cartCount.value = repoCount.value

                App.cartCount = cartCount.value

                Log.d("notifiction", cartCount.value.toString())
            }
            is Result.Error ->{
                Log.d("notifiction", repoCount.error.toString())
            }
        }
    }

    private fun getWishKeyFromDb() = launch {
        val wishkey = wishRepo.getWishKeys()
        when(wishkey){
            is Result.Value ->{

                 populateHomeItemList(wishkey.value)

                 Log.d("wishlist_keys", wishkey.value.toString())

            }
            is Result.Error -> Log.d("wishlist_keys","error")
        }

    }

    private fun updateWishListDb(prod_uid: String, isFavourite:Boolean,adapter_position : Int) = launch {

        Log.d("isFav",isFavourite.toString())

        if(!isFavourite) {

            val postListWish = wishRepo.addItemToWishList(prod_uid)

            when (postListWish) {
                is Result.Value -> {

                    freshFindsGridAdapter.notifyItemChanged(adapter_position,true)

                    showWishListItemCount()

                }
                is Result.Error -> errorState.value
            }
        }else{

            var deleteResponse = wishRepo.deleteItemFromWishList(prod_uid)

            when (deleteResponse) {
                is Result.Value -> {

                    freshFindsGridAdapter.notifyItemChanged(adapter_position,false)

                    showWishListItemCount()

                    Log.d("delete_response", productList.size.toString())
                }
                is Result.Error -> errorState.value
            }

        }


    }




    fun populateHomeItemList(wishListKeys: List<String>) = launch {

        val productListResponse = productRepo.getAllProducts(wishListKeys)

        when (productListResponse) {
            // is Result.Value -> noteListState.value = notesResult.value
            is Result.Value -> {

                productList = productListResponse.value as ArrayList<Product>

                freshFindsGridAdapter.updateHomeItem(productListResponse.value)


                Log.d("fresh_find_response", "success"+productListResponse.value.toString())
            }
            is Result.Error ->  Log.d("fresh_find_response", "error")
        }
    }


    private fun getbanners() = launch {

        val banner_items = productRepo.getBannerItems()
        Log.d("call_banner", "true")

        when (banner_items) {
            is Result.Value -> {
                bannerListState.value = banner_items.value
                // listOfBannerItem = banner_items.value
            }
            is Result.Error -> errorState.value = "Banner Error"
        }

    }


    private fun addCategoryItemList() = launch {

        val categoryItemList = productRepo.getCategories()

        Log.d("category_item_value",categoryItemList.toString())

        when (categoryItemList) {
            is Result.Value -> {
                // mutableCategoryList.value = categoryItemList.value
                categoryItemAdapter.updateGalleryList(categoryItemList.value)
                App.categoryItems = categoryItemList.value

              //  Log.d("category_item_value",categoryItemList.toString())

            }
            is Result.Error -> errorState.value = "Category Error"
        }

    }

}
