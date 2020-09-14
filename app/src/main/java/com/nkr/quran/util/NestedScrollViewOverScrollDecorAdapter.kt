package com.nkr.quran.util

import android.view.View
import androidx.core.widget.NestedScrollView
import me.everything.android.ui.overscroll.adapters.IOverScrollDecoratorAdapter


class NestedScrollViewOverScrollDecorAdapter(protected val mView: NestedScrollView) :
    IOverScrollDecoratorAdapter {

    override fun getView(): View {
        return this.mView
    }

    override fun isInAbsoluteStart(): Boolean {
        return !this.mView.canScrollVertically(-1)
    }

    override fun isInAbsoluteEnd(): Boolean {
        return !this.mView.canScrollVertically(1)
    }
}