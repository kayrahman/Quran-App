package com.nkr.fashionita.ui.fragment.productListing

import android.util.Log
import com.nkr.fashionita.common.BaseViewModel
import com.nkr.fashionita.model.Product
import com.nkr.fashionita.common.Result
import com.nkr.fashionita.repository.IProductRepository
import com.nkr.fashionita.ui.fragment.account.StorageUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ProductListingViewModel(val repo:IProductRepository,
                              uiContext: CoroutineContext
                              ) : BaseViewModel<ProductListingEvent>(uiContext) {


    var image_urls = ArrayList<String>()


    override fun handleEvent(event: ProductListingEvent) {
        when(event){
           // is ProductListingEvent.OnPostProductBtnClick -> postProduct(event.prod,event.byteList)
            is ProductListingEvent.OnPostProductBtnClick -> postProductPhotos(event.byteList,event.prod)
        }
    }


    private fun postProductPhotos(
        bytes: ArrayList<ByteArray>,
        prod: Product
    ) = launch {

        apiCallStart()

        val postPhotoResponse =   StorageUtil.uploadProductPhotosRef(bytes)

        when(postPhotoResponse){
            is Result.Value -> {

                postProduct(prod.copy(imageUrls = postPhotoResponse.value))

                apiCallFinish()

                Log.d("upload_photo_response",postPhotoResponse.value.toString())
            }
            is Result.Error -> {
                apiCallFinish()

                Log.d("upload_photo_response",postPhotoResponse.error.toString())

            }

        }
    }

    private fun postProduct(
        prod: Product) = launch {

        val postProdResponse = repo.postProductInfo(prod)

        when(postProdResponse){
            is Result.Value -> {



                listener.onSuccessfulUpload()
            }
                is Result.Error -> apiCallFinish()

        }
    }


    lateinit var listener : FragmentCallback

    interface FragmentCallback{
       fun onSuccessfulUpload()
    }


}
