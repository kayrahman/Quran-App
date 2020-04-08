package com.nkr.fashionita.ui.adapter.categoryDetail

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nkr.fashionita.R
import com.nkr.fashionita.databinding.ItemCategoryDetailBinding
import com.nkr.fashionita.databinding.ItemFreshFindsBinding
import com.nkr.fashionita.model.Product

class CategoryItemsGridAdapter : RecyclerView.Adapter<CategoryItemsGridAdapter.HomeItemViewHolder>()  {


    var categoryItemList: List<Product>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeItemViewHolder {

        val binding: ItemCategoryDetailBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_category_detail, parent, false)
        return HomeItemViewHolder(
            binding
        )

    }

    override fun onBindViewHolder(holder: HomeItemViewHolder, position: Int) {
        holder.bind(categoryItemList!![position])

        holder.binding.clyFreshFindsItem.setOnClickListener {
            listener?.onItemClick(categoryItemList!![position].creationDate)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.imvListItem.clipToOutline = true
        }

    }

    override fun getItemCount(): Int {
       return categoryItemList?.size ?: 0
    }


    fun updateCategoryItem(category_item_list: List<Product>) {
        this.categoryItemList = category_item_list
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



    class HomeItemViewHolder(val binding : ItemCategoryDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        private val viewModel = CategoryItemsGridAdapterViewModel()

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
        fun onItemClick(uid: String)

    }


}













