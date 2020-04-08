package com.nkr.fashionita.base.helper

import android.app.Activity
import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

class MsgHelper(activity: Activity) {
    val activity: Activity = activity

    /**
     * toast msg short duration
     */
    fun toastShort(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }

    /**
     * toast msg long duration
     */
    fun toastLong(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show()
    }

    /**
     * toast msg short duration with y offset
     *
     * @param msg
     * @param yOffSet
     */
    fun toastShortWithGravity(msg: String, yOffSet: Int) {
        val toast = Toast.makeText(activity, msg, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, yOffSet)
        toast.show()
    }

    /**
     * toast msg long duration with y offset
     *
     * @param msg
     * @param yOffSet
     */
    fun toastLongWithGravity(msg: String, yOffSet: Int) {
        val toast = Toast.makeText(activity, msg, Toast.LENGTH_LONG)
        toast.setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, yOffSet)
        toast.show()
    }

    /**
     * snack msg short duration
     *
     * @param msg
     */
    fun snackShort(msg: String, layout: Int) {
        Snackbar.make(activity.findViewById<View>(layout), msg, Snackbar.LENGTH_SHORT).show()
    }

    /**
     * snack msg long duration
     *
     * @param msg
     */
    fun snackLong(msg: String, layout: Int) {
        Snackbar.make(activity.findViewById<View>(layout), msg, Snackbar.LENGTH_LONG).show()
    }
}