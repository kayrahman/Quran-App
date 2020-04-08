package com.nkr.fashionita.repository

import com.nkr.fashionita.model.User
import com.nkr.fashionita.common.Result


interface IUserRepository {
    suspend fun getCurrentUser(): Result<Exception, User?>

    suspend fun signOutCurrentUser(): Result<Exception, Unit>

    suspend fun signInGoogleUser(idToken: String): Result<Exception, Unit>

    suspend fun postUserDesign(): Result<Exception, Unit>

    suspend fun updateUserInfo(user: User): Result<Exception, Unit>




}