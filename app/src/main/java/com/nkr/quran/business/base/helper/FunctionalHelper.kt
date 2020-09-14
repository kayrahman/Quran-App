package com.nkr.quran.business.base.helper

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.provider.Settings
import android.util.Base64
import android.util.DisplayMetrics
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.regex.Pattern


class FunctionalHelper(activity: Activity) {
    val activity: Activity = activity

    /**
     * Hide Keyboard function using view
     *
     * @param view
     */
    fun hideKeyboard(view: View) {
        try {
            val inputManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Check email validation
     *
     * @param email
     * @return
     */
    fun isEmailValid(email: String): Boolean {
        val expression = "[a-zA-Z0-9+._%-+]{1,256}" +
                "@" +
                "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" +
                "(" +
                "." +
                "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" +
                ")+"
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }

    /**
     * Get device id function
     *
     * @return
     */
    fun getDeviceID(): String {
        return Settings.Secure.getString(activity.contentResolver, Settings.Secure.ANDROID_ID)
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    fun convertDpToPixel(dp: Float, context: Context): Float {
        val resources = context.resources
        val metrics = resources.displayMetrics
        return dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    /**
     * get base64 from bitmap
     *
     * @param bitmap
     * @return
     */
    fun getBase64String(bitmap: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val imageBytes = baos.toByteArray()
        var str = Base64.encodeToString(imageBytes, Base64.NO_WRAP)
        return str
    }

    fun compressorFromHell(image: File, context: Context): File{
        var counter = 0
        var compressedImageFile = image
        while ((compressedImageFile.length()/1024)  > 150) {
            compressedImageFile = id.zelory.compressor.Compressor(context)
                    .setQuality(90-counter)
                    .compressToFile(image)
            counter+=10
            if (counter > 90){
                break
            }
        }
        return compressedImageFile
    }
    /**
     * save image to local
     */
    /*

    fun saveImageToFolder(url: String) {
        Dexter.withActivity(activity)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                        *//*Picasso.get()
                                .load(url)
                                .into(object : com.squareup.picasso.Target {
                                    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

                                    }

                                    override fun onBitmapFailed(e: java.lang.Exception?, errorDrawable: Drawable?) {
                                    }

                                    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                                        saveImage(activity.applicationContext, bitmap!!, true)
                                    }
                                })*//*
                        val glideUrl = GlideUrl(url,
                                LazyHeaders.Builder()
                                        .addHeader("Authorization", "Bearer ${App.token}")
                                        .build())
                        Glide.with(activity.applicationContext)
                                .asBitmap()
                                .load(glideUrl)
                                .apply(RequestOptions()
                                        .placeholder(R.mipmap.ic_launcher)
                                        .fitCenter()
                                )
                                .apply(RequestOptions.skipMemoryCacheOf(true))
                                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                                .into(object:  SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)  {

                                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                                        saveImage(activity.applicationContext, resource!!, true)
                                    }

                                })
                    }

                    override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {
                        token?.continuePermissionRequest()
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    }
                }).check()

    }

    */

    /**
     * get image real path in local
     */
    fun getRealPathFromURI(uri: Uri): String {
        var path = ""
        if (activity.contentResolver != null) {
            val cursor = activity.contentResolver.query(uri, null, null, null, null)
            if (cursor != null) {
                cursor!!.moveToFirst()
                val idx = cursor!!.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                path = cursor!!.getString(idx)
                cursor!!.close()
            }
        }
        return path
    }
}