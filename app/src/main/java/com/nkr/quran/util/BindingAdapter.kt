package com.nkr.quran.util

import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.*
import com.bumptech.glide.Glide
import com.nkr.quran.framework.presentation.ui.fragment.account.StorageUtil
import java.io.File
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import me.everything.android.ui.overscroll.VerticalOverScrollBounceEffectDecorator


@BindingAdapter("mutableVisibility")
fun setMutableVisibility(view: View, visibility: MutableLiveData<Int>?) {
    val parentActivity: AppCompatActivity? = view.getParentActivity()
    if (parentActivity != null && visibility != null) {
        visibility.observe(parentActivity, Observer { value -> view.visibility = value ?: View.VISIBLE })
    }
}

@BindingAdapter("mutableText")
fun setMutableText(view: TextView, text: MutableLiveData<String>?) {
    val parentActivity: AppCompatActivity? = view.getParentActivity()
    if (parentActivity != null && text != null) {
        text.observe(parentActivity, Observer { value -> view.text = value ?: "" })
    }
}

@BindingAdapter("adapterGrid")
fun setAdapterGrid(view: RecyclerView, adapter: RecyclerView.Adapter<*>) {
    view.adapter = adapter
    val mLayoutManager = GridLayoutManager(view.context, 3)
    view.layoutManager = mLayoutManager
    val spanCount = 3 // 3 columns
    val spacing = 10 // 50px
    val includeEdge = false
    view.addItemDecoration(GridSpacingItemDecoration(spanCount, spacing, includeEdge))
    // Vertical
    OverScrollDecoratorHelper.setUpOverScroll(view, OverScrollDecoratorHelper.ORIENTATION_VERTICAL)
}

@BindingAdapter("adapterGridHome")
fun setAdapterGridHome(view: RecyclerView, adapter: RecyclerView.Adapter<*>) {
    view.adapter = adapter
    val mLayoutManager = GridLayoutManager(view.context, 2)
    view.layoutManager = mLayoutManager
    val spanCount = 2 // 3 columns
    val spacing = 10 // 50px
    val includeEdge = false
    view.addItemDecoration(GridSpacingItemDecoration(spanCount, spacing, includeEdge))
    // Vertical
    OverScrollDecoratorHelper.setUpOverScroll(view, OverScrollDecoratorHelper.ORIENTATION_VERTICAL)
}

@BindingAdapter("adapterGridChosenImages")
fun setAdapterGridChosenImages(view: RecyclerView, adapter: RecyclerView.Adapter<*>) {
    view.adapter = adapter
   // val mLayoutManager = GridLayoutManager(view.context, 3)
    val mLayoutManager = LinearLayoutManager(view.context,RecyclerView.HORIZONTAL,false)
    view.layoutManager = mLayoutManager
    OverScrollDecoratorHelper.setUpOverScroll(view, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL)
}





@BindingAdapter(value = ["bindingAdapter", "defaultRvLayout", "showDefaultDividerLine"], requireAll = false)
fun setBindingAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>, defaultLayout: Boolean?, showDefaultDividerLine: Boolean?) {
    if (defaultLayout != null && defaultLayout) {
        val mLayoutManager = LinearLayoutManager(view.context, LinearLayout.VERTICAL, false)
        view.layoutManager = mLayoutManager
        view.itemAnimator = DefaultItemAnimator()
        if (showDefaultDividerLine == true) {
            view.addItemDecoration(
                DividerItemDecoration(view.context, LinearLayout.VERTICAL)
            )
        }
        // Vertical
        OverScrollDecoratorHelper.setUpOverScroll(view, OverScrollDecoratorHelper.ORIENTATION_VERTICAL)

    }
    view.adapter = adapter
    view.setHasFixedSize(true)

}




@BindingAdapter(value = ["bindingHorizontalAdapter", "defaultRvLayout", "showDefaultDividerLine"], requireAll = false)
fun setBindingAdapterHorizontal(view: RecyclerView, adapter: RecyclerView.Adapter<*>, defaultLayout: Boolean?, showDefaultDividerLine: Boolean?) {
    if (defaultLayout != null && defaultLayout) {
        val mLayoutManager = LinearLayoutManager(view.context, LinearLayout.HORIZONTAL, false)
        view.layoutManager = mLayoutManager
        view.itemAnimator = DefaultItemAnimator()
        if (showDefaultDividerLine == true) {
            view.addItemDecoration(
                DividerItemDecoration(view.context, LinearLayout.HORIZONTAL)
            )
        }
        // Vertical
        OverScrollDecoratorHelper.setUpOverScroll(view, OverScrollDecoratorHelper.ORIENTATION_VERTICAL)

    }
    view.adapter = adapter
}


/*

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String?) {
    Glide.with(imageView.context).load(url)
        .apply {RequestOptions()
            .placeholder(R.drawable.avatar_female)
        }
        .into(imageView)
}*/

@BindingAdapter("firestoreImageUrl")
fun setFirestoreImageUrl(imageView: ImageView, url: String?) {
    Glide.with(imageView.context).load(StorageUtil.pathToReference(url.toString())).into(imageView)
}


@BindingAdapter("imageUrlLocal")
fun setImageUrlLocal(imageView: ImageView, url: String?) {
    if (url!!.startsWith("http")) {
        Glide.with(imageView.context).load(url).into(imageView)
    } else {
        val imgFile = File(url)

        if (imgFile.exists()) {
            val imageUri = Uri.fromFile(imgFile)

            //load without library (performance issues)
//                val myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath())
//                iv_gallery.setImageBitmap(myBitmap)

            Glide.with(imageView.context)
                    .load(imageUri)
                    .into(imageView)
        }
    }
}


@BindingAdapter("customOverScroll")
fun setCustomOverScroll(view: ScrollView, overScroll: Boolean) {
    if(overScroll) {
        OverScrollDecoratorHelper.setUpOverScroll(view)
    }
}


@BindingAdapter("customOverScroll")
fun setNestedCustomOverScroll(view: NestedScrollView, overScroll: Boolean) {
    if(overScroll) {
        VerticalOverScrollBounceEffectDecorator(NestedScrollViewOverScrollDecorAdapter(view))
    }




}
