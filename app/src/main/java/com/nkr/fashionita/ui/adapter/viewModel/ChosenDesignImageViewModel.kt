package com.nkr.fashionita.ui.adapter.viewModel


class ChosenDesignImageViewModel{
    lateinit var img: String

    fun bind(imgUrl: String) {
        this.img = imgUrl

    }

}