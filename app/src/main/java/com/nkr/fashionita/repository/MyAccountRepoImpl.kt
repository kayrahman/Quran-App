package com.nkr.fashionita.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.nkr.fashionita.common.Result
import com.nkr.fashionita.common.awaitTaskResult
import com.nkr.fashionita.common.toProduct
import com.nkr.fashionita.common.toUser
import com.nkr.fashionita.model.FirebaseProduct
import com.nkr.fashionita.model.Product
import com.nkr.fashionita.model.User
import com.nkr.fashionita.util.COLLECTION_CATEGORIES
import com.nkr.fashionita.util.COLLECTION_PRODUCT
import com.nkr.fashionita.util.COLLECTION_SUB_CATEGORIES
import com.nkr.fashionita.util.COLLECTION_USERS

class MyAccountRepoImpl(val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance(),
                        val remote: FirebaseFirestore = FirebaseFirestore.getInstance())

    : IMyAccountRepository {

    override suspend fun fetchUserInfoFromRemote() : Result<Exception,User?> {

        val user = getActiveUser()

        return try {
            val task = awaitTaskResult(
                remote.collection(COLLECTION_USERS).document(user!!.uid)
                .get()
            )

            resultToUserInfo(task)


        } catch (e: Exception) {

            Log.e("sub_category_exception", e.toString())

            Result.build { throw e }
        }

    }

    private fun resultToUserInfo(task: DocumentSnapshot?): Result<Exception, User?> {
        val userInfo = task!!.toObject(User::class.java)

       return  Result.build { userInfo }

    }


    private fun getActiveUser(): User? {
        return firebaseAuth.currentUser?.toUser
    }


    /**
     * get all products
     *
     * ***/

     override suspend fun getUserListing(): Result<Exception, List<Product>> {

        val user = getActiveUser()

        Log.d("user_uid", user?.uid.toString())

        return try {
            val task = awaitTaskResult(
                remote.collection(COLLECTION_PRODUCT)
                    .whereEqualTo("creator",user?.uid)
                    .get()

            )

            resultToProductList(task)

        } catch (exception: Exception) {

            Log.d("user_listing_response", exception.toString())

            Result.build { throw exception }
        }

    }


    private fun resultToProductList(result: QuerySnapshot?): Result<Exception, List<Product>> {

        val noteList = mutableListOf<Product>()

        result?.forEach { documentSnapshot ->

            var product = documentSnapshot.toObject(FirebaseProduct::class.java).toProduct

            noteList.add(product)

            // noteList.add(documentSnapshot.toObject(FirebaseProduct::class.java).toProduct)
        }

        return Result.build {

            Log.d("user_listing_size", noteList.size.toString())
            noteList
        }
    }



}