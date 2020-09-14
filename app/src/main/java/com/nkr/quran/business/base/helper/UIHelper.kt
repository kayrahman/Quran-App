package com.nkr.quran.business.base.helper

import android.app.Activity
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.Point
import android.os.Build
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Display
import android.view.View
import android.view.Window
import android.view.WindowManager


class UIHelper(activity: Activity) {
    val activity: Activity = activity

    /**
     * Set full screen activity
     */
    fun setFullScreen() {
        // To make activity full screen.
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE)
        activity.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)

    }

    /**
     * Set status bar color
     *
     * @param color
     */
    fun setStatusBar(color: Int) {
        val window = activity.window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = color
        }
    }

    /**
     * Set window status bar fully transparent
     */
    fun setStatusBarTransparent() {
        val bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        var on = true
        var set = false

        //make full transparent statusBar
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            on = true
            set = true
        }
        if (Build.VERSION.SDK_INT >= 19) {
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        if (Build.VERSION.SDK_INT >= 21) {
            on = false
            activity.window.statusBarColor = Color.TRANSPARENT
            set = true
        }

        if (set) {
            val win = activity.window
            val winParams = win.attributes
            if (on) {
                winParams.flags = winParams.flags or bits
            } else {
                winParams.flags = winParams.flags and bits.inv()
            }
            win.attributes = winParams
        }
    }

    /**
     * set status bar as padding top
     *
     * @param v
     */
    fun setPaddingTopBelowStatusBar(v: View) {
        //get status bar height
        var result = 0
        val resourceId = activity.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = activity.resources.getDimensionPixelSize(resourceId)
        }
        v.setPadding(0, result, 0, 0)
    }

    /**
     * set view as status bar height
     * @param v
     */
    fun setViewAsStatusBarHeight(v: View) {
        //get status bar height
        var result = 0
        val resourceId = activity.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = activity.resources.getDimensionPixelSize(resourceId)
        }
        val layoutParams = v.layoutParams
        layoutParams.height = result
        v.layoutParams = layoutParams
    }

    /**
     * Get the actionBar height
     *
     * @return
     */
    fun getActionBarHeight(): Int {
        // Calculate ActionBar's height
        val tv = TypedValue()
        var actionBarHeight = 0
        if (activity.theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, activity.resources.displayMetrics)
        }
        return actionBarHeight
    }

    /**
     * check screen size
     */
    fun getScreenSize(): String {
        val screenSize = activity.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK
        val toastMsg: String
        toastMsg = when (screenSize) {
            Configuration.SCREENLAYOUT_SIZE_LARGE -> "Large screen"
            Configuration.SCREENLAYOUT_SIZE_NORMAL -> "Normal screen"
            Configuration.SCREENLAYOUT_SIZE_SMALL -> "Small screen"
            else -> "Screen size is neither large, normal or small"
        }
        return toastMsg
    }

    fun getScreenInches(): Double {
        val windowManager = activity.windowManager
        val display = windowManager.defaultDisplay
        val displayMetrics = DisplayMetrics()
        display.getMetrics(displayMetrics)


        // since SDK_INT = 1;
        var mWidthPixels = displayMetrics.widthPixels
        var mHeightPixels = displayMetrics.heightPixels

        // includes window decorations (statusbar bar/menu bar)
        if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 17) {
            try {
                mWidthPixels = Display::class.java.getMethod("getRawWidth").invoke(display) as Int
                mHeightPixels = Display::class.java.getMethod("getRawHeight").invoke(display) as Int
            } catch (ignored: Exception) {
            }

        }

        // includes window decorations (statusbar bar/menu bar)
        if (Build.VERSION.SDK_INT >= 17) {
            try {
                val realSize = Point()
                Display::class.java.getMethod("getRealSize", Point::class.java).invoke(display, realSize)
                mWidthPixels = realSize.x
                mHeightPixels = realSize.y
            } catch (ignored: Exception) {
            }

        }
        val dm = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(dm)
        val x = Math.pow((mWidthPixels / dm.xdpi).toDouble(), 2.0)
        val y = Math.pow((mHeightPixels / dm.ydpi).toDouble(), 2.0)
        return Math.sqrt(x + y)
    }

    /**
     * change status bar status
     */
    fun changeStatusBarStatus(show: Boolean) {
        if (show) {
            // Show the status bar on Android 4.0 and Lower
            if (Build.VERSION.SDK_INT < 16) {
                activity.window.setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN)
            } else {
                //Show the status bar on Android 4.1 and higher
                val decorView = activity.window.decorView
                // Show the status bar.
                val visibility = View.SYSTEM_UI_FLAG_VISIBLE or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                decorView.systemUiVisibility = visibility
                // Show action bar
                val actionBar = activity.actionBar
                actionBar?.show()

            }
        } else {
            //Hide the status bar on Android 4.0 and Lower
            if (Build.VERSION.SDK_INT < 16) {
                val w = activity.window
                w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN)
            } else {
                val decorView = activity.window.decorView
                // Hide the status bar.
                val visibility = View.SYSTEM_UI_FLAG_FULLSCREEN
                decorView.systemUiVisibility = visibility
                // Hide action bar that too if necessary.
                val actionBar = activity.actionBar
                actionBar?.hide()

            }
        }
    }

    fun setLightStatusBar() {
        // Show the status bar on Android 4.0 and Lower
        if (Build.VERSION.SDK_INT >= 16) {
            //Show the status bar on Android 4.1 and higher
            val decorView = activity.window.decorView
            // Show the status bar.
            val visibility = View.SYSTEM_UI_FLAG_VISIBLE or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            decorView.systemUiVisibility = visibility
            // Show action bar
            val actionBar = activity.actionBar
            actionBar?.show()
        }
    }

    fun clearLightStatusBar() {
        if (Build.VERSION.SDK_INT >= 16) {
            //Show the status bar on Android 4.1 and higher
            val decorView = activity.window.decorView
            // Show the status bar.
            val visibility = View.SYSTEM_UI_FLAG_VISIBLE
            decorView.systemUiVisibility = visibility
            // Show action bar
            val actionBar = activity.actionBar
            actionBar?.show()
        }
    }


}