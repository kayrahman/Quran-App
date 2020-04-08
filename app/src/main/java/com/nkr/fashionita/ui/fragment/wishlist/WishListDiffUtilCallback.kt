package com.nkr.fashionita.ui.fragment.wishlist


import androidx.recyclerview.widget.DiffUtil
import com.nkr.fashionita.model.Product

class WishListDiffUtilCallback : DiffUtil.ItemCallback<Product>(){
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.isWishList == newItem.isWishList
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.isWishList == newItem.isWishList
    }
}