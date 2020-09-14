package com.nkr.quran.util

import android.content.Context
import android.graphics.drawable.Drawable

import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat

class ResourceProvider(private val mContext: Context) {

    fun getString(resId: Int): String {
        return mContext.getString(resId)
    }

    fun getString(resId: Int, value: String): String {
        return mContext.getString(resId, value)
    }

    fun getStringArray(resId: Int): Array<String> {
        return mContext.resources.getStringArray(resId)
    }

    fun getColor(resId: Int): Int {
        return ContextCompat.getColor(mContext, resId)
    }

    fun getDrawable(resId: Int): Drawable? {
        return ResourcesCompat.getDrawable(mContext.resources, resId, null)
    }

    fun getDimens(resId: Int): Float? {
        return mContext.resources.getDimension(resId)
    }
}