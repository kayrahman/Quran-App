package com.nkr.fashionita.ui.adapter.viewModel


class CategoryPostAdapterViewModel{
    lateinit var categoryName: String

    fun bind(catName: String) {
        this.categoryName = catName

    }

}