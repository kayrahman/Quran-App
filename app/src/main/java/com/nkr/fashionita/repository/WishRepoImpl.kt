package com.nkr.fashionita.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.nkr.fashionita.common.*
import com.nkr.fashionita.common.awaitTaskResult
import com.nkr.fashionita.common.toUser
import com.nkr.fashionita.model.*
import com.nkr.fashionita.util.COLLECTION_PRODUCT
import com.nkr.fashionita.util.COLLECTION_USERDATA
import com.nkr.fashionita.util.COLLECTION_USERS
import com.nkr.fashionita.util.COLLECTION_WISHLIST



class WishRepoImpl(
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance(),
    val remote: FirebaseFirestore = FirebaseFirestore.getInstance()
) : IWishListRepository {


    /**
     * if currentUser != null, return true
     */
    private fun getActiveUser(): User? {
        return firebaseAuth.currentUser?.toUser
    }



    override suspend fun getWishKeys(): Result<Exception, List<String>> {
        val user = getActiveUser()

        return try {
            val task = awaitTaskResult(
                remote.collection(COLLECTION_USERS)
                    .document(user?.uid.toString())
                    .collection(COLLECTION_USERDATA)
                    .document(COLLECTION_WISHLIST)
                    .get()
            )

            resultToWishListKeys(task)

        } catch (exception: Exception) {
            Result.build { throw exception }
        }
    }


    private fun resultToWishListKeys(result: DocumentSnapshot): Result<Exception, List<String>> {

        val wishListKeys = mutableListOf<String>()

        result.data?.forEach {
            wishListKeys.add(it.key)
           Log.d("wish_keys", it.key)
        }



        return Result.build {
            wishListKeys
        }

    }



    override suspend fun getAllProductsFromWishlist(listKeys: List<String>): Result<Exception, List<Product>> {
        return try {
            val task = awaitTaskResult(
                remote.collection(COLLECTION_PRODUCT)
                    .get()
            )

            resultToProductsFromWishList(task,listKeys)

        } catch (exception: Exception) {
            Result.build { throw exception }
        }

    }

    private fun resultToProductsFromWishList(task: QuerySnapshot?, listKeys: List<String>): Result<Exception, List<Product>> {

        val productList = mutableListOf<Product>()

        task?.forEach { documentSnapshot ->
            var prod_uid = documentSnapshot.id

            if(listKeys.contains(prod_uid)){
                val product = documentSnapshot.toObject(FirebaseProduct::class.java).toProduct
                product.isWishList = true
                productList.add(product)
            }

        }

        return Result.build {
            productList
        }



    }


    override suspend fun addItemToWishList(product_uid: String): Result<Exception, Unit> {

        val user = getActiveUser()


        val wishListMap = mutableMapOf<String, Any>()
        wishListMap[product_uid] = true
        wishListMap["list_size"] = FieldValue.increment(1)

        return try {
            awaitTaskCompletable(
                remote.collection(COLLECTION_USERS)
                    .document(user?.uid.toString())
                    .collection(COLLECTION_USERDATA)
                    .document(COLLECTION_WISHLIST)
                    .update(wishListMap)

            )

            Result.build { Unit }

        } catch (exception: Exception) {
            Log.e("Error_wishlist", exception.toString())
            Result.build { throw exception }
        }

    }

    override suspend fun deleteItemFromWishList(product_uid: String): Result<Exception, Unit> {
        val user = getActiveUser()

        val fieldUpdate = hashMapOf<String,Any>()
        fieldUpdate[product_uid] = FieldValue.delete()
        fieldUpdate["list_size"] = FieldValue.increment(-1)

        return try {
            awaitTaskCompletable(
                remote.collection(COLLECTION_USERS)
                    .document(user?.uid.toString())
                    .collection(COLLECTION_USERDATA)
                    .document(COLLECTION_WISHLIST)
                    .update(fieldUpdate)

            )

            Result.build { Unit }

        } catch (exception: Exception) {
            Result.build { throw exception }
        }


    }

}