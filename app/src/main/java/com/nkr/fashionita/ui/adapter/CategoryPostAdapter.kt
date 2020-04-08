package com.nkr.fashionita.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nkr.fashionita.R
import com.nkr.fashionita.databinding.ItemCategoryBinding
import com.nkr.fashionita.databinding.ItemChosenImagesBinding
import com.nkr.fashionita.databinding.ItemGalleryBinding
import com.nkr.fashionita.model.CategoryItem
import com.nkr.fashionita.model.Product
import com.nkr.fashionita.ui.adapter.viewModel.CategoryPostAdapterViewModel
import com.nkr.fashionita.ui.adapter.viewModel.ChosenDesignImageViewModel
import com.nkr.fashionita.ui.adapter.viewModel.GalleryGridImageViewModel


/**
 * Created by.
 */

class CategoryPostAdapter : RecyclerView.Adapter<CategoryPostAdapter.MyViewHolder>() {

    private var cateroyItemList: List<CategoryItem>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var imgSelected = arrayListOf<String>()
    var textViewSelected = arrayListOf<TextView>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val binding: ItemCategoryBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.item_category, parent, false)

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var path = cateroyItemList!![position]
       // var tvCount = imgSelected.indexOf(path)

        holder.bind(path)
        holder.binding.rlCategoryItem.setOnClickListener {
           // post product info on database

         //   listener.onItemClick(galleryObjectList!!.get(position))
          listener.onItemClick(cateroyItemList!!.get(position))

        }


    }

    override fun getItemCount(): Int {

      //  Log.d("gallery_list_item",galleryObjectList?.size.toString())
        return if (cateroyItemList != null) cateroyItemList!!.size else 0
    }

    fun updateGalleryList(galleryList: List<CategoryItem>) {
        this.cateroyItemList = galleryList

    }

    /**
     * clear all selected image
     */
    fun clearAllSelected(){
        imgSelected.clear()
        textViewSelected.clear()
        notifyDataSetChanged()
    }

    class MyViewHolder(val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        val gridImageViewModel = CategoryPostAdapterViewModel()

        fun bind(item: CategoryItem) {
            gridImageViewModel.bind(item.category_name)
            binding.viewModel = gridImageViewModel

        }

    }

    /**
     * listener class related
     */
    internal lateinit var listener: AdapterListener

    interface AdapterListener {
        fun errorToast(msg: String)
        fun onItemClick(category_name : CategoryItem)
    }
}
