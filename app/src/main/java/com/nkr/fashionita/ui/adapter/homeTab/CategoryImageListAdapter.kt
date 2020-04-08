package com.nkr.fashionita.ui.adapter.homeTab

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nkr.fashionita.R
import com.nkr.fashionita.databinding.ItemCategoryHorizontalBinding
import com.nkr.fashionita.databinding.ItemChosenImagesBinding
import com.nkr.fashionita.databinding.ItemGalleryBinding
import com.nkr.fashionita.model.CategoryItem
import com.nkr.fashionita.ui.adapter.viewModel.CategoryImageListViewModel
import com.nkr.fashionita.ui.adapter.viewModel.ChosenDesignImageViewModel
import com.nkr.fashionita.ui.adapter.viewModel.GalleryGridImageViewModel


/**
 * Created by kay_rahman
 */

class CategoryImageListAdapter :
    RecyclerView.Adapter<CategoryImageListAdapter.MyViewHolder>() {

    private var galleryObjectList: List<CategoryItem>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: ItemCategoryHorizontalBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_category_horizontal, parent, false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var path = galleryObjectList!![position]
        holder.bind(path)

        holder.binding.ivCategoryItem.setOnClickListener {
            // itemClick(holder)
            listener.onItemClick(galleryObjectList!![position].category_name)
        }
    }

    override fun getItemCount(): Int {

        Log.d("gallery_list_item", galleryObjectList?.size.toString())
        return if (galleryObjectList != null) galleryObjectList!!.size else 0
    }


    fun updateGalleryList(categoryList: List<CategoryItem>) {
        this.galleryObjectList = categoryList

    }


    class MyViewHolder(val binding: ItemCategoryHorizontalBinding) : RecyclerView.ViewHolder(binding.root) {
        val categoryListViewModel = CategoryImageListViewModel()

        fun bind(category_item: CategoryItem) {
            categoryListViewModel.bind(category_item)
            binding.viewModel = categoryListViewModel


        }

    }


    /**
     * listener class related
     */
    internal lateinit var listener: AdapterListener

    interface AdapterListener {
        fun errorToast(msg: String)
        fun onItemClick(size: String)
    }

}