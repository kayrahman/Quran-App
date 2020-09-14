package com.nkr.quran.util

import android.media.Image
import java.io.File

/**
 * Saves a JPEG [Image] into the specified [File].
 */
class ImageSaver internal constructor(

    /*

        private val context: Context,

         * The JPEG image
         *//*
        private val mImage: Image,

         * The file we save the image into.
         *//*
        private val mFile: File) : Runnable {

    override fun run() {
        val buffer = mImage.planes[0].buffer
        val bytes = ByteArray(buffer.remaining())
        buffer.get(bytes)
        var output: FileOutputStream? = null
        try {
            output = FileOutputStream(mFile)
            output.write(bytes)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            mImage.close()
            galleryAddPic(context, mFile.absolutePath)
            if (null != output) {
                try {
                    output.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }

}    }
*/
)