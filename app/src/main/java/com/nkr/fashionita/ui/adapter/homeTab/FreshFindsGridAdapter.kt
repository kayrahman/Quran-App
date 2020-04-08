package com.nkr.fashionita.ui.adapter.homeTab

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nkr.fashionita.R
import com.nkr.fashionita.databinding.ItemFreshFindsBinding
import com.nkr.fashionita.model.Product
import org.jetbrains.anko.image
import org.jetbrains.anko.imageResource


class FreshFindsGridAdapter : RecyclerView.Adapter<FreshFindsGridAdapter.HomeItemViewHolder>()  {


    var productList: List<Product>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeItemViewHolder {

        val binding: ItemFreshFindsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_fresh_finds, parent, false)
        return HomeItemViewHolder(
            binding
        )

    }


    override fun onBindViewHolder(holder: HomeItemViewHolder, position: Int,payloads:MutableList<Any>) {

        if(payloads.isNotEmpty()){


            val updatedProduct = productList!![position].copy(isWishList = payloads[0] as Boolean )
            holder.bind(updatedProduct)

            holder.binding.itemFreshFindsWishlist.setOnClickListener {
                listener?.onWishListItemClick(updatedProduct.uid, updatedProduct.isWishList,position)


            }


        }else{


            holder.bind(productList!![position])

            holder.binding.clyFreshFindsItem.setOnClickListener {
                listener?.onItemClick(productList!![position])
            }


            holder.binding.itemFreshFindsWishlist.setOnClickListener {
                listener?.onWishListItemClick(productList!![position].uid, productList!![position].isWishList,position)


            }


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.binding.imvListItem.clipToOutline = true
            }

        }

/*

        holder.binding.itemFreshFindsWishlist.setOnClickListener {


            if(!productList!![position].isWishList){

                //  holder.binding.itemFreshFindsWishlist.setImageResource(R.drawable.ic_heart)
                listener?.onWishListItemClick(productList!![position].uid,false,position)
                Log.d("wishlist_click","already selected.delete from db")

            }else{

                //    holder.binding.itemFreshFindsWishlist.setImageResource(R.drawable.ic_heart_red)

                listener?.onWishListItemClick(productList!![position].uid,true,position)

                Log.d("wishlist_click","new selection.update the db")
            }

        }

*/

    }



    override fun onBindViewHolder(holder: HomeItemViewHolder, position: Int) {


        holder.bind(productList!![position])

        holder.binding.clyFreshFindsItem.setOnClickListener {
            listener?.onItemClick(productList!![position])
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.imvListItem.clipToOutline = true
        }

        holder.binding.itemFreshFindsWishlist.setOnClickListener {


            if(!productList!![position].isWishList){

              //  holder.binding.itemFreshFindsWishlist.setImageResource(R.drawable.ic_heart)
                listener?.onWishListItemClick(productList!![position].uid,false,position)
                Log.d("wishlist_click","already selected.delete from db")

            }else{

            //    holder.binding.itemFreshFindsWishlist.setImageResource(R.drawable.ic_heart_red)

                listener?.onWishListItemClick(productList!![position].uid,true,position)

                Log.d("wishlist_click","new selection.update the db")
            }

            }



    }

    override fun getItemCount(): Int {
       return productList?.size ?: 0
    }


    fun updateHomeItem(note_list: List<Product>) {
        this.productList = note_list
    }



    companion object {
        val DiffCallBack = object : DiffUtil.ItemCallback<Product>(){
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.creationDate == newItem.creationDate
            }

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.creationDate == newItem.creationDate
            }
        }
    }



    class HomeItemViewHolder(val binding : ItemFreshFindsBinding) : RecyclerView.ViewHolder(binding.root) {
        val viewModel = FresFindsListViewModel()

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
        fun onItemClick(product: Product)
        fun onWishListItemClick(prod_uid: String,isFavourite:Boolean,position : Int)


    }


}













