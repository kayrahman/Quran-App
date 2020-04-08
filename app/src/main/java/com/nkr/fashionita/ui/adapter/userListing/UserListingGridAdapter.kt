package com.nkr.fashionita.ui.adapter.userListing

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.nkr.fashionita.R
import com.nkr.fashionita.databinding.ItemFreshFindsBinding
import com.nkr.fashionita.databinding.ItemUserListingBinding
import com.nkr.fashionita.model.Product
import org.jetbrains.anko.image
import org.jetbrains.anko.imageResource


class UserListingGridAdapter : RecyclerView.Adapter<UserListingGridAdapter.UserListingViewHolder>()  {


    var productList: List<Product>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListingViewHolder {

        val binding: ItemUserListingBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_user_listing, parent, false)
        return UserListingViewHolder(
            binding
        )

    }


    override fun onBindViewHolder(holder: UserListingViewHolder, position: Int) {


        holder.bind(productList!![position])

        holder.binding.clyFreshFindsItem.setOnClickListener {
            listener?.onItemClick(productList!![position])
        }

        holder.binding.ivItemUserListingMore.setOnClickListener {view ->
            listener?.onMoreIconClick(view,productList!![position].uid)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.imvListItem.clipToOutline = true
        }



    }

    override fun getItemCount(): Int {
       return productList?.size ?: 0
    }


    fun updateUserListItem(note_list: List<Product>) {
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



    class UserListingViewHolder(val binding : ItemUserListingBinding) : RecyclerView.ViewHolder(binding.root) {
        val viewModel = UserListingViewModel()

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
        fun onMoreIconClick(view:View,prod_uid: String)

    }


}













