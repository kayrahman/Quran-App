package com.nkr.quran.framework.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nkr.quran.business.domain.models.Chapter
import com.nkr.quran.databinding.ListItemChapterBinding

class QuranChapterListAdapter:ListAdapter<Chapter,RecyclerView.ViewHolder>(QuranChapterDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return QuranChapterViewHolder(
            ListItemChapterBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val chapter = getItem(position)
        (holder as QuranChapterViewHolder).bind(chapter)
    }

}

private class QuranChapterViewHolder(val binding:ListItemChapterBinding):RecyclerView.ViewHolder(binding.root){
    fun bind(item:Chapter){
        binding.apply {
            chapter = item
            executePendingBindings()
        }
    }

}


private class QuranChapterDiffCallback : DiffUtil.ItemCallback<Chapter>() {

    override fun areItemsTheSame(oldItem: Chapter, newItem: Chapter): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Chapter, newItem: Chapter): Boolean {
        return oldItem == newItem
    }
}