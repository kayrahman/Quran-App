package com.nkr.fashionita.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.nkr.fashionita.common.*
import com.nkr.fashionita.common.awaitTaskResult
import com.nkr.fashionita.common.toUser
import com.nkr.fashionita.model.*
import com.nkr.fashionita.model.firebase.FirebaseSubCategoryItem
import com.nkr.fashionita.util.*

/**
 * If this wasn't a demo project, I would apply more abstraction to this repository (i.e. local and remote would be
 * separate interfaces which this class would depend on). I wanted to keep it the back end simple since this app is
 * a demo on MVVM, which is a front end architecture pattern.
 */
class ProductRepoImpl(
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance(),
    val remote: FirebaseFirestore = FirebaseFirestore.getInstance(),
    val local: NoteDao
) : IProductRepository {

    /**
     *
     * sub category
     * ***/


    override suspend fun getSubCategories(category_uid: String): Result<Exception, List<SubCategoryItem>> {


        return try {
            val task = awaitTaskResult(
                remote.collection(COLLECTION_CATEGORIES).document(category_uid).collection(
                    COLLECTION_SUB_CATEGORIES
                ).get()
            )

            resultToSubCategoryList(task)


        } catch (e: Exception) {

            Log.e("sub_category_exception", e.toString())

            Result.build { throw e }
        }

    }

    private fun resultToSubCategoryList(task: QuerySnapshot?): Result<Exception, List<SubCategoryItem>> {
        val subCategoryList = mutableListOf<SubCategoryItem>()

        task?.forEach { documentSnapshot ->
            // subCategoryList.add(documentSnapshot.toObject(FirebaseCategoryItem::class.java).toCategory)
            subCategoryList.add(documentSnapshot.toObject(FirebaseSubCategoryItem::class.java).toSubCategory)

        }

        return Result.build {
            subCategoryList
        }


    }

    /***
     * search
     * ****/

    override suspend fun getProductItemBySearch(queryString: String): Result<Exception, List<Product>> {

        return try {
            val task = awaitTaskResult(
                remote.collection(COLLECTION_PRODUCT).orderBy("title").startAt(queryString.trim()).endAt(
                    queryString.trim() + "\uf8ff"
                )
                    .get()

            )

            Log.d("task_search_prod", task.toString())


            fetchSearchResult(task)

        } catch (e: Exception) {
            Result.build { throw e }
        }


    }

    private fun fetchSearchResult(task: QuerySnapshot?): Result<Exception, List<Product>> {
        val noteList = mutableListOf<Product>()

        task?.forEach { documentSnapshot ->
            noteList.add(documentSnapshot.toObject(FirebaseProduct::class.java).toProduct)

            Log.d("searched_product", documentSnapshot.toString())

        }

        return Result.build {
            noteList
        }

    }


    /**
     * get banner items
     */

    override suspend fun getBannerItems(): Result<Exception, List<BannerItem>> {

        return try {
            val task = awaitTaskResult(
                remote.collection(COLLECTION_BANNER)
                    .get()

            )
            resultToBannerList(task)

        } catch (e: Exception) {
            Result.build { throw e }
        }

    }

    private fun resultToBannerList(result: QuerySnapshot?): Result<Exception, List<BannerItem>> {
        val noteList = mutableListOf<BannerItem>()

        result?.documents?.forEach { documentSnapshot ->
            // noteList.add(documentSnapshot.toObject(FirebaseNote::class.java).toNote)
            noteList.add(documentSnapshot.toObject(BannerItem::class.java)!!)

            Log.d("Banner_item", documentSnapshot.toString())

        }

        return Result.build {
            noteList
        }
    }

    /***
     * category
     * **/

    override suspend fun getCategories(): Result<Exception, List<CategoryItem>> {

        return try {
            val task = awaitTaskResult(
                remote.collection(COLLECTION_CATEGORIES)
                    .get()

            )

            resultToCategoryList(task)

        } catch (e: Exception) {

            Log.e("category_exception", e.toString())

            Result.build { throw e }
        }

    }

    private fun resultToCategoryList(task: QuerySnapshot?): Result<Exception, List<CategoryItem>> {

        val categoryList = mutableListOf<CategoryItem>()

        task?.forEach {

            val categoryItem = it.toObject(FirebaseCategoryItem::class.java).toCategory
            categoryItem.uid = it.id
            //  categoryList.add(it.toObject(FirebaseCategoryItem::class.java).toCategory)
            categoryList.add(categoryItem)
            Log.d("category_item", categoryList.toString())
        }


        return Result.build {
            categoryList
        }


    }


    /**
     * get category items
     * **/


    override suspend fun getProductItemsByCategory(queryString: String): Result<Exception, List<Product>> {

        return try {
            val task = awaitTaskResult(
                remote.collection(COLLECTION_PRODUCT).orderBy("categoryTitle").startAt(queryString.trim()).endAt(
                    queryString.trim() + "\uf8ff"
                )
                    .get()

            )

            Log.d("task_search_prod", task.toString())


            fetchSearchResult(task)

        } catch (e: Exception) {
            Result.build { throw e }
        }


    }


    /**
     * if currentUser != null, return true
     */
    private fun getActiveUser(): User? {
        return firebaseAuth.currentUser?.toUser
    }




    /**
     * get all products
     *
     * ***/

    override suspend fun getAllProducts(wishlistKeys:List<String>): Result<Exception, List<Product>> {

        return try {
            val task = awaitTaskResult(
                remote.collection(COLLECTION_PRODUCT)
                    .get()
            )

            resultToProductList(task,wishlistKeys)

        } catch (exception: Exception) {

            Log.d("fresh_find_response", exception.toString())

            Result.build { throw exception }
        }

    }


    private fun resultToProductList(result: QuerySnapshot?,wishListKeys:List<String>): Result<Exception, List<Product>> {

        val noteList = mutableListOf<Product>()

        result?.forEach { documentSnapshot ->

            var prod_uid = documentSnapshot["uid"].toString()

            var product = documentSnapshot.toObject(FirebaseProduct::class.java).toProduct

            if(wishListKeys.contains(prod_uid)) {
                product.isWishList = true

            }

            noteList.add(product)

            // noteList.add(documentSnapshot.toObject(FirebaseProduct::class.java).toProduct)
        }

        return Result.build {

            Log.d("prod_list", noteList.size.toString())
            noteList
        }
    }



    override suspend fun getProductById(
        creationDate: String
    ): Result<Exception, Product> {

        return try {

            val user = getActiveUser()

            val task = awaitTaskResult(
                remote.collection(COLLECTION_PRODUCT)
                    .document(creationDate + user?.uid)
                    .get()
            )

            Result.build {
                //Task<DocumentSnapshot!>
                task.toObject(FirebaseProduct::class.java)?.toProduct ?: throw Exception()
            }
        } catch (exception: Exception) {

            Result.build { throw exception }
        }
    }


    private suspend fun getRemoteNote(
        creationDate: String,
        user: User
    ): Result<Exception, Product> {
        return try {
            val task = awaitTaskResult(
                remote.collection(COLLECTION_NAME)
                    .document(creationDate + user.uid)
                    .get()
            )

            Result.build {
                //Task<DocumentSnapshot!>
                task.toObject(FirebaseProduct::class.java)?.toProduct ?: throw Exception()
            }
        } catch (exception: Exception) {
            Result.build { throw exception }
        }
    }





    override suspend fun postProductInfo(prod: Product): Result<Exception, Unit> {

        var user = getActiveUser()
        // prod.copy(creator = user)

        return try {
            awaitTaskCompletable(
                remote.collection(COLLECTION_PRODUCT)
                    // .document(prod.creationDate + prod.creator!!.uid)
                    .document(prod.creationDate + user?.uid.toString())
                    .set(
                        prod.copy(
                            uid = prod.creationDate + user?.uid.toString(),
                            creator = user?.uid.toString()
                        )
                    )
            )

            Result.build { Unit }

        } catch (exception: Exception) {
            Result.build { throw exception }
        }

    }




    override suspend fun updateProductImages(prod: Product): Result<Exception, Unit> {

        var user = getActiveUser()

      //  var prod_info = HashMap<String,Any>()
       // prod_info["imageUrls"] = FieldValue.arrayUnion(imageUrl)


        return try {
            awaitTaskCompletable(
                remote.collection(COLLECTION_PRODUCT)
                   //  .document(prod.creationDate + prod.creator!!.uid)
                    .document(prod.creationDate+ user!!.uid)
                 //   .update("imageUrls",FieldValue.arrayUnion(imageUrl))
                    .set(prod)
            )

            Result.build { Unit }

        } catch (exception: Exception) {
            Result.build { throw exception }
        }

    }


}