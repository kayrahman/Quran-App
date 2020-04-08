package com.nkr.fashionita.ui.Item.notelist


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nkr.fashionita.R
import com.nkr.fashionita.model.Product
import com.nkr.fashionita.model.NoteDiffUtilCallback


class NoteListAdapter(val event:MutableLiveData<NoteListEvent> = MutableLiveData()): ListAdapter<Product, NoteListAdapter.NoteViewHolder>(
    NoteDiffUtilCallback()
){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return NoteViewHolder(
            inflater.inflate(R.layout.item_fresh_finds, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        getItem(position).let { note ->
          //  holder.content.text = note.contents
           // holder.imageview.strokeColor = R.color.colorGray
           // holder.imageview.setImageResource(R.drawable.women_casual)
           // holder.date.text = note.creationDate

            holder.itemView.setOnClickListener {
                event.value = NoteListEvent.OnNoteItemClick(position)
            }
        }
    }


    class NoteViewHolder(root: View): RecyclerView.ViewHolder(root){
       // var content: TextView = root.lbl_message
       // var imageview:MyCustomView = root.imv_list_item_icon
    }
}



