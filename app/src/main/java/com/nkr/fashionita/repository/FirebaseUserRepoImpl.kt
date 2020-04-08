package com.nkr.fashionita.repository

import android.animation.ObjectAnimator
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.nkr.fashionita.common.awaitTaskCompletable
import com.nkr.fashionita.model.User
import com.nkr.fashionita.common.Result
import com.nkr.fashionita.repository.IUserRepository
import com.nkr.fashionita.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class FirebaseUserRepoImpl(val auth: FirebaseAuth = FirebaseAuth.getInstance(),
                           val remote:FirebaseFirestore = FirebaseFirestore.getInstance()
                           ) : IUserRepository {

    override suspend fun signInGoogleUser(idToken: String):
            Result<Exception, Unit> = withContext(Dispatchers.IO) {
        try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            awaitTaskCompletable(auth.signInWithCredential(credential))

            Result.build { Unit }


        } catch (exception: Exception) {
            Result.build { throw exception }
        }

    }


    override suspend fun signOutCurrentUser(): Result<Exception, Unit> {
        return Result.build {
            auth.signOut()
        }
    }

    override suspend fun getCurrentUser(): Result<Exception, User?> {
        val firebaseUser = auth.currentUser

        return if (firebaseUser == null) {
            Result.build { null }
        } else {
            Result.build {
                User(
                    firebaseUser.uid,
                    firebaseUser.displayName ?: ""
                )
            }
        }
    }




    override suspend fun postUserDesign(): Result<Exception, Unit> {
        return Result.build {  }
    }



    override suspend fun updateUserInfo(user: User): Result<Exception, Unit> {

        val firebaseUser = auth.currentUser

        val user_info = HashMap<String,Any>()
        user_info["address"] = user.address
        user_info["first_name"] = user.first_name
        user_info["last_name"] = user.last_name
        user_info["username"] = user.username
        user_info["city"] = user.city

        return try {
            awaitTaskCompletable(
                remote.collection(COLLECTION_USERS)
                    .document(firebaseUser!!.uid)
                    .update(user_info)
            )

            Result.build { Unit }

        } catch (exception: Exception) {

            Result.build { throw exception }
        }
    }


}