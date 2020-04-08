package com.nkr.fashionita.ui.adapter.viewModel

import com.nkr.fashionita.model.CategoryItem


class CategoryImageListViewModel{
     var img: String = ""
    lateinit var categoryName:String

    fun bind(category_item : CategoryItem) {
        this.img = category_item.img_url
        this.categoryName = category_item.category_name



    }

}