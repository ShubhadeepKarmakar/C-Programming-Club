package com.example.cprogrammingclub.learning

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cprogrammingclub.R

class ChapterAdapter(
    private val onChapterProblemsClicked: (ChapterModel) -> Unit,
    private val onChapterQuizClicked: (ChapterModel) -> Unit,
    private val onReadingClicked: (ChapterModel) -> Unit
) : ListAdapter<ChapterModel, ChapterAdapter.ChaptersViewHolder>(Diffutil()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChaptersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chapter_item, parent, false)
        return ChaptersViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChaptersViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.findViewById<TextView>(R.id.problems).setOnClickListener {
            onChapterProblemsClicked(item)
        }
        holder.itemView.findViewById<TextView>(R.id.quizzes).setOnClickListener {
            onChapterQuizClicked(item)
        }
        holder.itemView.findViewById<TextView>(R.id.reading).setOnClickListener {
            onReadingClicked(item)
        }
        holder.itemView.setOnClickListener {
            onReadingClicked(item)
        }


    }


    class ChaptersViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val chapter = view.findViewById<TextView>(R.id.chapter_name)
        fun bind(item: ChapterModel) {
            chapter.text = item.chapterName

        }
    }

    class Diffutil : DiffUtil.ItemCallback<ChapterModel>() {
        override fun areItemsTheSame(
            oldItem: ChapterModel,
            newItem: ChapterModel
        ): Boolean {
            return oldItem.chapterKey == newItem.chapterKey
        }

        override fun areContentsTheSame(
            oldItem: ChapterModel,
            newItem: ChapterModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}