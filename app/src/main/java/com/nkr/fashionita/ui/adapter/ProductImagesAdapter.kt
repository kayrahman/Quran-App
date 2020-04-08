package com.nkr.fashionita.ui.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.nkr.fashionita.ui.fragment.account.StorageUtil
import org.jetbrains.anko.image
import org.jetbrains.anko.imageURI

class ProductImagesAdapter(imageList:ArrayList<String>) : PagerAdapter() {

    private var productImageList = arrayListOf<String>()
        set(value) {
            field = value
        }


    init {
            this.productImageList = imageList
    }


    override fun instantiateItem(container: ViewGroup, position: Int): Any {

       var productImage = ImageView(container.context)
     //   productImage.setImageResource(productImageList.get(position))

        Glide.with(container.context).load(StorageUtil.pathToReference(productImageList.get(position))).into(productImage)


     //  productImage.image (productImageList.get(position))


        container.addView(productImage,0)

        return productImage
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
       return productImageList.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        //super.destroyItem(container, position, `object`)

        container.removeView(`object` as ImageView)

    }
}