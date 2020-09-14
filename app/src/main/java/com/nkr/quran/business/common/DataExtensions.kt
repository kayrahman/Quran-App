package com.nkr.quran.business.common

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

internal suspend fun <T> awaitTaskResult(task: Task<T>): T = suspendCoroutine { continuation ->
    task.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            continuation.resume(task.result!!)

            Log.d("task",task.result.toString())

        } else {
            continuation.resumeWithException(task.exception!!)
//            Log.d("task",task.result.toString())
        }
    }
}

//snapshot listener
internal suspend fun awaitTaskListener(task: CollectionReference) = suspendCancellableCoroutine<QuerySnapshot> { continuation ->
    task.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
        if (firebaseFirestoreException != null) {
            //  continuation.resume(return@addSnapshotListener)
              continuation.resume(querySnapshot!!)
        } else {
            firebaseFirestoreException?.let { continuation.resumeWithException(it) }
        }
    }
}


//Wraps Firebase/GMS calls
internal suspend fun <T> awaitTaskCompletable(task: Task<T>): Unit = suspendCoroutine { continuation ->
    task.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            continuation.resume(Unit)
            Log.d("task_prod",task.result.toString())
        } else {
            continuation.resumeWithException(task.exception!!)
            Log.d("task_prod",task.result.toString())
        }
    }
}

/*


internal val FirebaseProduct.toProduct: Product
    get() = Product(
        this.uid?:"",
        this.categoryTitle ?: "",
        this.subCategoryTitle ?: "",
        this.creationDate ?: "",
        this.thumbImageUrl?:"",
        this.imageUrls?:null,
        this.creator ?: "",
        this.title?:"",
        this.price?:"",
        this.brand?:"",
        this.condition?:"",
        this.dealMethod?: null,
        this.description?:"",
           false,
        this.timestamp?: Date(0),
        this.tags?:null

    )

internal val FirebaseCategoryItem.toCategory: CategoryItem
    get() = CategoryItem(
       this.uid?:"",
        this.category_name?:"",
        this.img_url?:"",
        this.sub_categories?:null

    )


internal val FirebaseSubCategoryItem.toSubCategory: SubCategoryItem
    get() = SubCategoryItem(
        this.uid?:"",
        this.name?:"",
        this.img_url?:""

    )
*/
