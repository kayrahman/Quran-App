package com.nkr.fashionita.ui.adapter.cart


import androidx.recyclerview.widget.DiffUtil
import com.nkr.fashionita.model.Product

class CartListDiffUtilCallback : DiffUtil.ItemCallback<Product>(){
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.uid == newItem.uid
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.uid == newItem.uid
    }
}