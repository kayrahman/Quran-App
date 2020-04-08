package com.nkr.fashionita.model


import androidx.recyclerview.widget.DiffUtil


class NoteDiffUtilCallback : DiffUtil.ItemCallback<Product>(){
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.creationDate == newItem.creationDate
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.creationDate == newItem.creationDate
    }
}