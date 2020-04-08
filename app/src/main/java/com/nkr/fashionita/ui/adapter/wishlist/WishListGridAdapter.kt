package com.nkr.fashionita.ui.adapter.wishlist

import android.os.Build
import android.service.voice.AlwaysOnHotwordDetector
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nkr.fashionita.R
import com.nkr.fashionita.databinding.ItemFreshFindsBinding
import com.nkr.fashionita.databinding.ItemSearchResultBinding
import com.nkr.fashionita.databinding.ItemWishListBinding
import com.nkr.fashionita.databinding.SearchResultFragmentBinding
import com.nkr.fashionita.model.Product
import com.nkr.fashionita.ui.fragment.wishlist.WishListDiffUtilCallback

class WishListGridAdapter : ListAdapter<Product,WishListGridAdapter.WishListItemViewHolder>(WishListDiffUtilCallback())  {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishListItemViewHolder {

        val binding: ItemWishListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_wish_list, parent, false)

        return WishListItemViewHolder(
            binding
        )

    }


    override fun onBindViewHolder(holder: WishListItemViewHolder, position: Int,payloads: MutableList<Any>

    ) {

        if(payloads.isNotEmpty()) {

            getItem(position).let {product ->

                val updatedProduct = product.copy(isWishList = payloads[0] as Boolean )
                holder.bind(updatedProduct)

                holder.binding.ivWishlistIcon.setOnClickListener {
                    listener?.onWishListIconClick(updatedProduct.uid, updatedProduct.isWishList,position)


                }

            }


        }else{

        getItem(position).let { product ->

            holder.bind(product)

            holder.binding.clyFreshFindsItem.setOnClickListener {
                listener?.onItemClick(product)
            }

            holder.binding.ivWishlistIcon.setOnClickListener {
                listener?.onWishListIconClick(product.uid, product.isWishList, position)


            }


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.binding.imvListItem.clipToOutline = true

            }
        }
        }

    }


    override fun onBindViewHolder(holder: WishListItemViewHolder, position: Int) {

    }


    class WishListItemViewHolder(val binding : ItemWishListBinding) : RecyclerView.ViewHolder(binding.root) {
        private val viewModel = WishListAdapterViewModel()

        fun bind(item : Product){
            viewModel.bind(item)
            binding.viewModel = viewModel

        }

    }


    /**
     * listener class related
     */
    var listener: AdapterListener? = null

    interface AdapterListener {
        fun onItemClick(product : Product)
        fun onWishListIconClick(uid : String, isAlreadySaved : Boolean,position: Int)

    }




}













