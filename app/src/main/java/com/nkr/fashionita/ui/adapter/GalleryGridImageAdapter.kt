package com.nkr.fashionita.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nkr.fashionita.R
import com.nkr.fashionita.databinding.ItemGalleryBinding
import com.nkr.fashionita.ui.adapter.viewModel.GalleryGridImageViewModel


/**
 * Created by...
 */

class GalleryGridImageAdapter : RecyclerView.Adapter<GalleryGridImageAdapter.MyViewHolder>() {

    private var galleryObjectList: List<String>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var imgSelected = arrayListOf<String>()
    var textViewSelected = arrayListOf<TextView>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: ItemGalleryBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.item_gallery, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var path = galleryObjectList!![position]
        var tvCount = imgSelected.indexOf(path)
        holder.bind(path, tvCount)
        holder.binding.ivGallery.setOnClickListener {
            itemClick(holder)
        }
    }

    override fun getItemCount(): Int {

        Log.d("gallery_list_item",galleryObjectList?.size.toString())
        return if (galleryObjectList != null) galleryObjectList!!.size else 0
    }

    fun updateGalleryList(galleryList: List<String>) {
        this.galleryObjectList = galleryList

    }

    /**
     * item click function
     */
    private fun itemClick(holder: MyViewHolder) {
        var path = holder.gridImageViewModel.img
        var textView = holder.binding.tvSelect
        if (imgSelected.contains(path)) {
            //deselect
            var selectedCount = textView.text
            imgSelected.remove(path)
            textViewSelected.remove(textView)
            textView.text = imgSelected.size.toString()
            textView.visibility = View.GONE
            for (tv in textViewSelected) {
                if (tv.text.toString().toInt() > selectedCount.toString().toInt()) {
                    //case of count bigger than current deselect one
                    tv.text = (tv.text.toString().toInt() - 1).toString()
                }
            }
        } else {
            //select
            if (imgSelected.size >= 5) {
                //case of over select
                //listener?.errorToast("Max 5 image")
                listener?.errorToast("Max 5 image")

            } else {
                //case of within 5
                imgSelected.add(path)
                textViewSelected.add(textView)
                textView.text = imgSelected.size.toString()
                textView.visibility = View.VISIBLE
            }
        }
        listener?.onItemClick(imgSelected.size)
    }

    /**
     * clear all selected image
     */
    fun clearAllSelected(){
        imgSelected.clear()
        textViewSelected.clear()
        notifyDataSetChanged()
    }

    class MyViewHolder(val binding: ItemGalleryBinding) : RecyclerView.ViewHolder(binding.root) {
        val gridImageViewModel = GalleryGridImageViewModel()

        fun bind(imgUrl: String, tvCount: Int) {
            gridImageViewModel.bind(imgUrl, tvCount)
            binding.viewModel = gridImageViewModel


        }

    }

    /**
     * listener class related
     */
    internal lateinit var listener: AdapterListener

    interface AdapterListener {
        fun errorToast(msg: String)
        fun onItemClick(size: Int)
    }
}
