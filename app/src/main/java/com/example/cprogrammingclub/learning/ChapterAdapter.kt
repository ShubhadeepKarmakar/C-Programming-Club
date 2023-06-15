package com.example.cprogrammingclub.learning

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cprogrammingclub.R
import com.example.cprogrammingclub.progressbar.model.ProgressModel

class ChapterAdapter(
    private val onChapterProblemsClicked: (ChapterModel) -> Unit,
    private val onChapterQuizClicked: (ChapterModel) -> Unit,
    private val onReadingClicked: (ChapterModel) -> Unit,
    private val requireActivity: FragmentActivity,
) : ListAdapter<ChapterModel, ChapterAdapter.ChaptersViewHolder>(Diffutil()) {

    var progressStatus: List<ProgressModel>? = null
        set(value) {
          field=value
      }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChaptersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chapter_item, parent, false)
        return ChaptersViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChaptersViewHolder, position: Int) {
        val item = getItem(position)
        if (progressStatus!=null){
            holder.bind(item, progressStatus!!, requireActivity)
        }
        holder.itemView.findViewById<TextView>(R.id.problems).setOnClickListener {
            onChapterProblemsClicked(item)
        }
        holder.itemView.findViewById<LinearLayout>(R.id.quizzes).setOnClickListener {
            onChapterQuizClicked(item)
        }
        holder.itemView.findViewById<LinearLayout>(R.id.reading).setOnClickListener {
            onReadingClicked(item)
        }
        holder.itemView.setOnClickListener {
            onReadingClicked(item)
        }
    }

    class ChaptersViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val chapter = view.findViewById<TextView>(R.id.chapter_name)
        val rProgress = view.findViewById<TextView>(R.id.rProgress)
        val qProgress = view.findViewById<TextView>(R.id.qProgress)
        fun bind(
            item: ChapterModel,
            progressStatus: List<ProgressModel>,
            requireActivity: FragmentActivity
        ) {
            chapter.text = item.chapterName
            for (i in progressStatus) {
                if (i.chapterName == item.chapterName) {
                    if (i.cProgress > 0) {
                        rProgress.text = "Completed"
                        rProgress.setTextColor(
                            ContextCompat.getColor(
                                requireActivity,
                                R.color.dark_green
                            )
                        )
                        rProgress.setTypeface(null, Typeface.BOLD)
                    }
                    if (i.qProgress > 0) {
                        qProgress.text = "Completed"
                        qProgress.setTextColor(
                            ContextCompat.getColor(
                                requireActivity,
                                R.color.dark_green
                            )
                        )
                        qProgress.setTypeface(null, Typeface.BOLD)
                    }
                }
            }

        }
    }

    class Diffutil : DiffUtil.ItemCallback<ChapterModel>() {
        override fun areItemsTheSame(
            oldItem: ChapterModel,
            newItem: ChapterModel
        ): Boolean {
            return oldItem.chapterName == newItem.chapterName
        }

        override fun areContentsTheSame(
            oldItem: ChapterModel,
            newItem: ChapterModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}