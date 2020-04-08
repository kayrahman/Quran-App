package android.justory.com.testkotlin.utils.transformation


import android.view.View
import androidx.viewpager.widget.ViewPager

class ScaleOutTransformation : ViewPager.PageTransformer {
    private val MIN_SCALE = 0.85f
    private val MIN_ALPHA = 0.8f

    override fun transformPage(page: View, position: Float) {
        when {
            position <-1 -> // [-Infinity,-1)
                // This page is way off-screen to the left.
                page.alpha = 0f
            position <=1 -> { // [-1,1]

                page.scaleX = Math.max(MIN_SCALE,1-Math.abs(position))
                page.scaleY = Math.max(MIN_SCALE,1-Math.abs(position))
                page.alpha = Math.max(MIN_ALPHA,1-Math.abs(position))

            }
            else -> // (1,+Infinity]
                // This page is way off-screen to the right.
                page.alpha = 0f
        }
    }

}