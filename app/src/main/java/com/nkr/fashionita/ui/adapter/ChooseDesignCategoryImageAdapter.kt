package com.nkr.fashionita.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nkr.fashionita.R
import com.nkr.fashionita.databinding.ItemChosenImagesBinding
import com.nkr.fashionita.ui.adapter.viewModel.ChosenDesignImageViewModel


/**
 * Created by.
 */

class ChooseDesignCategoryImageAdapter :
    RecyclerView.Adapter<ChooseDesignCategoryImageAdapter.MyViewHolder>() {

    private var galleryObjectList = mutableListOf<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: ItemChosenImagesBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_chosen_images, parent, false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var path = galleryObjectList!![position]
        holder.bind(path)
        holder.binding.ivGallery.setOnClickListener {
            listener.onItemClick(position,path)
        }
    }


    //bind view holder with payload

    override fun onBindViewHolder(holder: MyViewHolder, position: Int, payloads: MutableList<Any>) {
        if(payloads.isNotEmpty()){
           var newPath = payloads[0]
            galleryObjectList[position] = newPath as String
            holder.bind(newPath.toString())

            holder.binding.ivGallery.setOnClickListener {
                listener.onItemClick(position,newPath)
            }


            Log.d("choose_photo_payload","true")

        }else{

            var path = galleryObjectList!![position]
            holder.bind(path)
            holder.binding.ivGallery.setOnClickListener {
                listener.onItemClick(position,path)
            }

            Log.d("choose_photo_payload","false")
        }

    }



    override fun getItemCount(): Int {

        Log.d("gallery_list_item", galleryObjectList?.size.toString())
        return if (galleryObjectList != null) galleryObjectList!!.size else 0
    }


    fun updateGalleryList(galleryList: List<String>) {
        this.galleryObjectList = galleryList as MutableList<String>


    }


    class MyViewHolder(val binding: ItemChosenImagesBinding) : RecyclerView.ViewHolder(binding.root) {
        val categoryListViewModel = ChosenDesignImageViewModel()

        fun bind(category_name: String) {
            categoryListViewModel.bind(category_name)
            binding.viewModel = categoryListViewModel


        }

    }


    /**
     * listener class related
     */
    internal lateinit var listener: AdapterListener

    interface AdapterListener {
        fun errorToast(msg: String)
        fun onItemClick(position: Int,size: String)
    }

}