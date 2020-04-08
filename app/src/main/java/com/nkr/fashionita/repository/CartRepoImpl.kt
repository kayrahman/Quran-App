package com.nkr.fashionita.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.nkr.fashionita.common.*
import com.nkr.fashionita.common.toUser
import com.nkr.fashionita.model.*
import com.nkr.fashionita.util.*


class CartRepoImpl(
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance(),
    val remote: FirebaseFirestore = FirebaseFirestore.getInstance()
) : ICartRepository {


    /**
     * if currentUser != null, return true
     */
    private fun getActiveUser(): User? {
        return firebaseAuth.currentUser?.toUser
    }

    override suspend fun getCartKeys(): Result<Exception, List<String>> {

        val user = getActiveUser()

        return try {
            val task = awaitTaskResult(
                remote.collection(COLLECTION_USERS)
                    .document(user?.uid.toString())
                    .collection(COLLECTION_USERDATA)
                    .document(COLLECTION_CARTLIST)
                    .get()
            )

            resultToCartListKeys(task)

        } catch (exception: Exception) {
            Result.build { throw exception }
        }
    }

    override suspend fun getAllProductsFromCartlist(list_keys: List<String>): Result<Exception, List<Product>> {

        return try {
            val task = awaitTaskResult(
                remote.collection(COLLECTION_PRODUCT)
                    .get()
            )

            resultToProductsFromCartList(task,list_keys)

        } catch (exception: Exception) {
            Result.build { throw exception }
        }
    }

    private fun resultToProductsFromCartList(task: QuerySnapshot?, listKeys: List<String>): Result<Exception, List<Product>> {

        val productList = mutableListOf<Product>()

        task?.forEach { documentSnapshot ->
            var prod_uid = documentSnapshot.id

            if(listKeys.contains(prod_uid)){
                val product = documentSnapshot.toObject(FirebaseProduct::class.java).toProduct
               // product.isWishList = true
                productList.add(product)
            }

        }

        return Result.build {
            productList
        }


    }

    private fun resultToCartListKeys(task: DocumentSnapshot?): Result<Exception, List<String>> {

        val cartListKeys = mutableListOf<String>()

        task?.data?.forEach {
            cartListKeys.add(it.key)

        }
        return Result.build {
            cartListKeys
        }

    }


    override suspend fun updateCartRepo(prodUid: String): Result<Exception, Unit> {

        val user = getActiveUser()

        val cartMap = mutableMapOf<String,Any>()
        cartMap.put(prodUid,FieldValue.increment(1))

        return try {
            awaitTaskCompletable(
                remote.collection(COLLECTION_USERS)
                    .document(user?.uid.toString())
                    .collection(COLLECTION_USERDATA)
                    .document(COLLECTION_CARTLIST)
                    .update(cartMap)



            )

            Result.build { Unit }

        } catch (exception: Exception) {
            Log.e("Error_cartlist", exception.toString())
            Result.build { throw exception }
        }


    }

    override suspend fun removeCartItemsFromRemote(prod_uid: String) : Result<Exception, Unit>{

        val fieldUpdate = hashMapOf<String,Any>()
        fieldUpdate[prod_uid] = FieldValue.delete()

           return try {
               awaitTaskCompletable(
                   remote.collection(COLLECTION_USERS)
                       .document(firebaseAuth?.uid.toString())
                       .collection(COLLECTION_USERDATA)
                       .document(COLLECTION_CARTLIST)
                       .update(fieldUpdate)

               )

               Result.build { Unit }

           } catch (exception: Exception) {
               Log.e("Error_cartlist", exception.toString())
               Result.build { throw exception }
           }


       }

    suspend fun getCartItemCount() : Result<Exception,Int?>{

        val user = getActiveUser()

        return try {
           val task = awaitTaskResult(
                remote.collection(COLLECTION_USERS)
                    .document(user?.uid.toString())
                    .collection(COLLECTION_USERDATA)
                    .document(COLLECTION_CARTLIST)
                    .get()

            )

            getCountFromResult(task)


        } catch (exception: Exception) {
            Log.e("Error_cartlist", exception.toString())
            Result.build { throw exception }
        }

    }

    private fun getCountFromResult(task: DocumentSnapshot?): Result<Exception, Int?> {

       // val count = task?.data?.count()

        var size : Long = 0

        task?.data?.forEach {
             size = size.plus(it.value as Long)

        }


        Log.d("cart_size",size.toString())
       return Result.build { size.toInt()}

    }



    suspend fun getWishListItemCount() : Result<Exception,Int?>{

        return try {
            val task = awaitTaskResult(
                remote.collection(COLLECTION_USERS)
                    .document(firebaseAuth?.uid.toString())
                    .collection(COLLECTION_USERDATA)
                    .document(COLLECTION_WISHLIST)
                    .get()

            )

            getWishCountFromResult(task)


        } catch (exception: Exception) {
            Log.e("Error_cartlist", exception.toString())
            Result.build { throw exception }
        }

    }



    private fun getWishCountFromResult(task: DocumentSnapshot?): Result<Exception, Int?> {
/*
        var size : Long = 0
        task?.data?.forEach {
            size = size.plus(it.value as Long)

        }
        */

      val list_size =  task?.data?.get("list_size").toString()

        return Result.build { list_size.toInt()}

    }

}