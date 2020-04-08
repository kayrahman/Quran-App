package com.nkr.fashionita.ui.adapter.searchResult

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nkr.fashionita.R
import com.nkr.fashionita.databinding.ItemFreshFindsBinding
import com.nkr.fashionita.databinding.ItemSearchResultBinding
import com.nkr.fashionita.databinding.SearchResultFragmentBinding
import com.nkr.fashionita.model.Product

class SearchResultItemGridAdapter : RecyclerView.Adapter<SearchResultItemGridAdapter.HomeItemViewHolder>()  {


    var noteList: List<Product>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeItemViewHolder {

        val binding: ItemSearchResultBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_search_result, parent, false)
        return HomeItemViewHolder(
            binding
        )

    }

    override fun onBindViewHolder(holder: HomeItemViewHolder, position: Int) {
        holder.bind(noteList!![position])

        holder.binding.clyFreshFindsItem.setOnClickListener {
            listener?.onItemClick(noteList!![position].creationDate)
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.imvListItem.clipToOutline = true
        }


    }

    override fun getItemCount(): Int {
       return noteList?.size ?: 0
    }


    fun updateHomeItem(note_list: List<Product>) {
        this.noteList = note_list
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



    class HomeItemViewHolder(val binding : ItemSearchResultBinding) : RecyclerView.ViewHolder(binding.root) {
        private val viewModel = SearchResultItemGridListViewModel()

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













