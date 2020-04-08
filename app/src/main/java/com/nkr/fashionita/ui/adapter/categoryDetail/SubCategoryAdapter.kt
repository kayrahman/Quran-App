package com.nkr.fashionita.ui.adapter.categoryDetail

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nkr.fashionita.R
import com.nkr.fashionita.databinding.ItemFreshFindsBinding
import com.nkr.fashionita.databinding.ItemSubCategoryBinding
import com.nkr.fashionita.model.CategoryItem
import com.nkr.fashionita.model.Product
import com.nkr.fashionita.model.SubCategoryItem

class SubCategoryAdapter : RecyclerView.Adapter<SubCategoryAdapter.SubCategoryItemViewHolder>()  {


    var subCategoryList: List<SubCategoryItem>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubCategoryItemViewHolder {

        val binding: ItemSubCategoryBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_sub_category , parent, false)
        return SubCategoryItemViewHolder(
            binding
        )

    }

    override fun onBindViewHolder(holder: SubCategoryItemViewHolder, position: Int) {

        holder.bind(subCategoryList!![position])
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.ivGallery.clipToOutline = true
        }

        /*

        holder.binding.clyFreshFindsItem.setOnClickListener {
            listener?.onItemClick(subCategoryList!![position].creationDate)
        }



        */


    }

    override fun getItemCount(): Int {
       return subCategoryList?.size ?: 0
    }


    fun updateSubCategoryItem(note_list: List<SubCategoryItem>) {
        this.subCategoryList = note_list
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



    class SubCategoryItemViewHolder(val binding : ItemSubCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        private val viewModel = SubCategoryAdapterViewModel()

        fun bind(item : SubCategoryItem){
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













