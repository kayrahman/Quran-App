package com.nkr.fashionita.repository

import com.nkr.fashionita.common.Result
import com.nkr.fashionita.model.Product
import com.nkr.fashionita.model.User

interface IMyAccountRepository {

    suspend fun fetchUserInfoFromRemote() : Result<Exception,User?>
    suspend fun getUserListing() : Result<Exception,List< Product>>
}