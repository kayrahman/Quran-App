package com.nkr.quran.util


import android.content.ContextWrapper
import android.content.res.Resources
import android.text.Editable
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData


fun View.getParentActivity(): AppCompatActivity? {
    var context = this.context
    while (context is ContextWrapper) {
        if (context is AppCompatActivity) {
            return context
        }
        context = context.baseContext
    }
    return null
}


/**
 * default value of mutable list
 */
fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply {
    value = initialValue
}


/**
 * convert string to editable
 */
fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

/**
 * convert to dp
 */
fun Int.toDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()

/**
 * convert to px
 */
fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
