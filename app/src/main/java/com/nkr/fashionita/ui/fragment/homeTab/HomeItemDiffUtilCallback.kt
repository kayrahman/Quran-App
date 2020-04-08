package com.nkr.fashionita.model


import androidx.recyclerview.widget.DiffUtil


class HomeItemDiffUtilCallback : DiffUtil.ItemCallback<Product>(){
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.creationDate == newItem.creationDate
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.creationDate == newItem.creationDate
    }
}