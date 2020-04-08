package com.nkr.fashionita.ui.adapter.viewModel


class GalleryGridImageViewModel{
    lateinit var img: String
    var tvCount: Int = -1

    fun bind(imgUrl: String, tvCount: Int) {
        this.img = imgUrl
        this.tvCount = tvCount
    }

}