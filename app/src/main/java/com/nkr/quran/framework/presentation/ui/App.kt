package com.nkr.quran.framework.presentation.ui

import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import com.nkr.quran.util.ResourceProvider
import dagger.hilt.android.HiltAndroidApp
import java.util.*

@HiltAndroidApp
class App : MultiDexApplication() {



    companion object {

        private const val SECOND_MILLIS = 1000
        private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
        private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
        private const val DAY_MILLIS = 24 * HOUR_MILLIS



        var mResourceProvider: ResourceProvider? = null



        private fun currentDate(): Date? {
            val calendar = Calendar.getInstance()
            return calendar.time
        }


        fun getTimeAgo(time: Long): String? {

            // long time = date.getTime();
            var time = time
            if (time < 1000000000000L) {
                time *= 1000
            }
            val now: Long = currentDate()?.time!!
            if (time > now || time <= 0) {
                return "not available"
            }
            val diff = now - time
            return if (diff < MINUTE_MILLIS) {
                "moments ago"
            } else if (diff < 2 * MINUTE_MILLIS) {
                "a minute ago"
            } else if (diff < 60 * MINUTE_MILLIS) {
                (diff / MINUTE_MILLIS).toString() + " minutes ago"
            } else if (diff < 2 * HOUR_MILLIS) {
                "an hour ago"
            } else if (diff < 24 * HOUR_MILLIS) {
                ( diff / HOUR_MILLIS).toString() + " hours ago"
            } else if (diff < 48 * HOUR_MILLIS) {
                "yesterday"
            } else {
                (diff / DAY_MILLIS).toString() + " days ago"
            }
        }



    }

    override fun onCreate() {
        super.onCreate()
        mResourceProvider = ResourceProvider(this)


        // Make sure we use vector drawables
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)


    }




}