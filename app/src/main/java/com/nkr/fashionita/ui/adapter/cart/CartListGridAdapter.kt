package com.nkr.fashionita.ui.adapter.cart

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
import com.nkr.fashionita.databinding.*
import com.nkr.fashionita.model.Product
import com.nkr.fashionita.ui.fragment.wishlist.WishListDiffUtilCallback
import kotlinx.android.synthetic.main.item_cart_list.view.*

class CartListGridAdapter : ListAdapter<Product,CartListGridAdapter.CartListItemViewHolder>(CartListDiffUtilCallback())  {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartListItemViewHolder {

        val binding: ItemCartListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_cart_list, parent, false)

        return CartListItemViewHolder(binding)

    }


    override fun onBindViewHolder(holder: CartListItemViewHolder, position: Int,payloads: MutableList<Any>

    ) {

        if(payloads.isNotEmpty()) {

            getItem(position).let {product ->

                val updatedProduct = product.copy(isWishList = payloads[0] as Boolean )
                holder.bind(updatedProduct)




                /*

                holder.binding.ivWishlistIcon.setOnClickListener {
                    listener?.onWishListIconClick(updatedProduct.uid, updatedProduct.isWishList,position)

*/
                }

        }else{

        getItem(position).let { product ->

            holder.bind(product)

            holder.binding.clyFreshFindsItem.setOnClickListener {
                listener?.onItemClick(product)
            }


            holder.binding.checkbox.setOnClickListener {
                if(it.checkbox.isChecked){
                listener?.onCheckBoxClick(true,product.price.toInt(),product.uid)
                }else{
                    listener?.onCheckBoxClick(false,product.price.toInt(),product.uid)
                }
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.binding.imvListItem.clipToOutline = true

            }
        }
        }

    }
    override fun onBindViewHolder(holder: CartListItemViewHolder, position: Int) {

    }


    class CartListItemViewHolder(val binding : ItemCartListBinding) : RecyclerView.ViewHolder(binding.root) {
        private val viewModel = CartListAdapterViewModel()

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
        fun onCheckBoxClick(isCheck : Boolean,price : Int,prod_uid:String)

    }




}













