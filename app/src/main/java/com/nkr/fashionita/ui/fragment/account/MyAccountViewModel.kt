package com.nkr.fashionita.ui.fragment.account

import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import com.nkr.fashionita.R
import com.nkr.fashionita.common.BaseViewModel
import com.nkr.fashionita.common.Result
import com.nkr.fashionita.model.App.Companion.mResourceProvider
import com.nkr.fashionita.repository.IMyAccountRepository
import com.nkr.fashionita.ui.adapter.CommonPagerAdapter
import com.nkr.fashionita.ui.adapter.userListing.UserListingGridAdapter
import com.nkr.fashionita.ui.fragment.listing.ListingFragment
import com.nkr.fashionita.ui.reviewFragment.ReviewFragment
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MyAccountViewModel(val acc_repo: IMyAccountRepository,
                          uiContext: CoroutineContext
                         ):BaseViewModel<MyAccountEvent>(uiContext) {

    val userName = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val thumb_image = MutableLiveData<String>()
    val address = MutableLiveData<String>()
    val listing_item_count = MutableLiveData<String>()


    var commonPagerAdapter: CommonPagerAdapter? = null
    var userListingGridAdapter = UserListingGridAdapter()




    override fun handleEvent(event: MyAccountEvent) {
        when(event){
            is MyAccountEvent.OnStart -> fetchUserInfo()
            is MyAccountEvent.InitPager -> initPager(event.fm)
            is MyAccountEvent.OnPopulateUserListing -> populateUserListing()
        }


    }

    private fun populateUserListing() = launch {

        val userListingResponse = acc_repo.getUserListing()
        when (userListingResponse){
            is Result.Value -> {

                listing_item_count.value = userListingResponse.value.size.toString() +" "+"listing"

                userListingGridAdapter.updateUserListItem(userListingResponse.value)

                Log.d("user_listing_response",userListingResponse.value.toString() )
            }
            is Result.Error -> Log.d("user_listing_response",userListingResponse.error.toString() )
        }

    }


    /**
     *
     */
     fun initPager(fragmentManager: FragmentManager) {

        commonPagerAdapter = CommonPagerAdapter(fragmentManager)

        var listing_fragment = ListingFragment.newInstance()
        commonPagerAdapter!!.addFragment(listing_fragment,"Listing")

        var review_fragment = ReviewFragment.newInstance()
        commonPagerAdapter!!.addFragment(review_fragment,"Review")


    }


    private fun fetchUserInfo() = launch {

        val myAccResponse = acc_repo.fetchUserInfoFromRemote()
        when(myAccResponse){
            is Result.Value -> {

                userName.value = myAccResponse.value?.name
                email.value = myAccResponse.value?.email
                thumb_image.value = myAccResponse.value?.thumb_image
                address.value = myAccResponse.value?.address

                Log.d("user_response",myAccResponse.value.toString())


            }
            is Result.Error -> {
                Log.d("user_response","Error")
            }
        }

    }

}