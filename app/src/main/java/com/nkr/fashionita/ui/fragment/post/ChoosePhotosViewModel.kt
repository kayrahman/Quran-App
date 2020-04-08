package com.nkr.fashionita.ui.fragment.post

import android.app.Activity
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.ViewModel
import com.nkr.fashionita.ui.adapter.GalleryGridImageAdapter

class ChoosePhotosViewModel : ViewModel() {

    var allImageList: MutableList<String> = mutableListOf()
    var adapter: GalleryGridImageAdapter = GalleryGridImageAdapter()


    /**
     * Getting All Images Path.
     *
     * @param activity
     * the activity
     * @return ArrayList with images Path
     */
    fun getAllShownImagesPath(activity: Activity) {
        val uri: Uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val columnIndexData: Int
        val columnIndexFolderName: Int
        val listOfAllImages = ArrayList<String>()
        var absolutePathOfImage: String? = null
        // Return only image metadata.

        val projection = arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME)

        //add this two for sort by latest date
        val BUCKET_GROUP_BY = "1) GROUP BY 1,(2"
        val BUCKET_ORDER_BY = "datetaken DESC"

        val orderBy = MediaStore.Images.ImageColumns.DATE_ADDED + " DESC"
        val cursor: Cursor?
        cursor = activity.contentResolver.query(uri, projection, null, null, orderBy)
//        cursor = activity.contentResolver.query(uri, projection, null, null, null)

        columnIndexData = cursor!!.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
        columnIndexFolderName = cursor
            .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(columnIndexData)

            //get folder name for grouping
            var folder_name = cursor.getString(columnIndexFolderName)

            listOfAllImages.add(absolutePathOfImage)
        }
        allImageList = listOfAllImages

        Log.d("local_size",allImageList.size.toString())


        adapter.updateGalleryList(allImageList)



    }

}
