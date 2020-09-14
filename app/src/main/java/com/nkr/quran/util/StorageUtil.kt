package com.nkr.quran.framework.presentation.ui.fragment.account

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.nkr.quran.business.common.Result
import com.nkr.quran.business.common.awaitTaskResult
import java.util.*
import kotlin.collections.ArrayList


object StorageUtil {
    private val storageInstance: FirebaseStorage by lazy { FirebaseStorage.getInstance() }

    private val currentUserRef: StorageReference
        get() = storageInstance.reference
                .child(FirebaseAuth.getInstance().currentUser?.uid
                        ?: throw NullPointerException("UID is null."))

    private val storageReference: StorageReference
        get() = storageInstance.reference


    fun uploadProfilePhoto(imageBytes: ByteArray,
                           onSuccess: (imagePath: String) -> Unit) {
        val ref = currentUserRef.child("profilePictures/${UUID.nameUUIDFromBytes(imageBytes)}")
        ref.putBytes(imageBytes)
                .addOnSuccessListener {
                    onSuccess(ref.path)
                }
    }

    fun uploadProductPhoto(imageBytes: ByteArray,
                             onSuccess: (imagePath: String) -> Unit) {
        val ref = storageReference.child("productPictures/${UUID.nameUUIDFromBytes(imageBytes)}")
        ref.putBytes(imageBytes)
            .addOnSuccessListener {
                onSuccess(ref.path)
            }

    }



         suspend fun uploadIconImage(imageBytes: ByteArray) : Result<Exception, String> {
             val ref = storageReference.child("category_photos/${UUID.nameUUIDFromBytes(imageBytes)}")
        return try {
            val task = awaitTaskResult(
                ref.putBytes(imageBytes)
            )

            resultToTask(task,ref)

           // Result.build { ref.path }
        } catch (exception: Exception) {
            Result.build { throw exception }
        }

    }

    suspend fun uploadSubCategoryIconImage(imageBytes: ByteArray) : Result<Exception, String> {
        val ref = storageReference.child("sub_category_photos/${UUID.nameUUIDFromBytes(imageBytes)}")
        return try {
            val task = awaitTaskResult(
                ref.putBytes(imageBytes)
            )

            resultToTask(task,ref)

            // Result.build { ref.path }
        } catch (exception: Exception) {
            Result.build { throw exception }
        }

    }

    private fun resultToTask(
        task: UploadTask.TaskSnapshot?,
        ref: StorageReference
    ): Result<Exception, String> {
      return  Result.build { ref.path }
    }


    suspend fun uploadProductPhotosRef(imageBytes : List<ByteArray>) : Result<Exception, ArrayList<String>> {

      //  val ref = storageReference.child("productPictures/${UUID.nameUUIDFromBytes(imageBytes)}")
        val imagesRef = arrayListOf<String>()
        return try {
            imageBytes.forEach {
                val ref = storageReference.child("productPictures/${UUID.nameUUIDFromBytes(it)}")
                val task = awaitTaskResult(
                    ref.putBytes(it)
                )
                imagesRef.add(ref.path)
            }
            Result.build { imagesRef }

            // getImageRef(task)
        } catch (exception: Exception) {
            Result.build { throw exception }
        }

    }


    private fun getImageRef(task: UploadTask.TaskSnapshot?): Result<Exception, String> {
        return Result.build { task?.uploadSessionUri.toString()  }


    }


    fun uploadMessageImage(imageBytes: ByteArray,
                           onSuccess: (imagePath: String) -> Unit) {
        val ref = currentUserRef.child("messages/${UUID.nameUUIDFromBytes(imageBytes)}")
        ref.putBytes(imageBytes)
                .addOnSuccessListener {
                    onSuccess(ref.path)
                }
    }

    fun pathToReference(path: String) = storageInstance.getReference(path)
}