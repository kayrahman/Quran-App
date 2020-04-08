package com.nkr.fashionita.common

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import com.nkr.fashionita.model.*
import com.nkr.fashionita.model.firebase.FirebaseSubCategoryItem
import java.util.*
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

internal val FirebaseUser.toUser: User
    get() = User(
        uid = this.uid,
        name = this.displayName ?: "",
        email = this.email?:"",
        thumb_image = this.photoUrl.toString()

    )

internal val FirebaseProduct.toProduct: Product
    get() = Product(
        this.uid?:"",
        this.categoryTitle ?: "",
        this.creationDate ?: "",
        this.thumbImageUrl?:"",
        this.imageUrls?:null,
        this.creator ?: "",
        this.title?:"",
        this.price?:"",
        this.size?:"",
        this.brand?:"",
        this.condition?:"",
        this.dealMethod?:"",
        this.description?:"",
           false,
        this.timestamp?: Date(0)

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

/*


internal val Product.toFirebaseProduct: FirebaseProduct
    get() = FirebaseProduct(
        this.uid,
        this.creationDate

    )
*/


//room shit

/*


internal val RoomNote.toNote: Product
    get() = Product(
        this.uid,
        this.creationDate,
        this.contents,
        this.imageUrl,
        this.productName,
        this.productPrice

    )



internal val Product.toRoomNote: RoomNote
    get() = RoomNote(
        this.uid,
        this.creationDate,
        this.contents,
        this.upVotes,
        this.imageUrl,
        this.safeGetUid,
    this.productName,
        this.productPrice
    )



internal fun List<RoomNote>.toNoteListFromRoomNote(): List<Product> = this.flatMap {
    listOf(it.toNote)
}



internal fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

internal val Product.safeGetUid: String
    get() = this.creator?.uid ?: ""

*/
