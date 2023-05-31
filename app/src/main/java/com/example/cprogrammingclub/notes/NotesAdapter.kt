package com.example.usertodatabase.ui.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cprogrammingclub.R
import com.example.usertodatabase.models.NoteResponseModel

class NotesAdapter(private val onNoteClicked: (NoteResponseModel) -> Unit) :
    ListAdapter<NoteResponseModel, NotesAdapter.NotesViewHolder>(Diffutil()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NotesViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onNoteClicked(item)
        }
    }

    class NotesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.findViewById<TextView>(R.id.title)
        val desc = view.findViewById<TextView>(R.id.desc)
        fun bind(item: NoteResponseModel) {
            title.text = item.TITLE
            desc.text = item.DESCRIPTION

        }
    }

    class Diffutil : DiffUtil.ItemCallback<NoteResponseModel>() {
        override fun areItemsTheSame(
            oldItem: NoteResponseModel,
            newItem: NoteResponseModel
        ): Boolean {
            return oldItem.noteId == newItem.noteId
        }

        override fun areContentsTheSame(
            oldItem: NoteResponseModel,
            newItem: NoteResponseModel
        ): Boolean {
            return oldItem == newItem
        }

    }
}