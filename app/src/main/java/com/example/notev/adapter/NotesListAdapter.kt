package com.example.notev.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notev.R
import com.example.notev.data.models.Note
import com.example.notev.databinding.ItemNoteBinding
import java.text.SimpleDateFormat
import java.util.*

class NotesListAdapter(
    private val context: Context,
    private val listener: Listener
) : RecyclerView.Adapter<NotesListAdapter.ViewHolder>() {

    /**
     * Interface to implement on note item click function.
     */
    fun interface Listener {

        /**
         * This method should be implemented in activity to selected note object.
         * @param uses - [Note] object.
         */
        fun onNoteItemClick(note: Note)
    }


    /**
     * View Holder which uses [R.layout.item_note].
     */
    inner class ViewHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // Array of priorities.
        private val priorityLevels: Array<String> =
            context.resources.getStringArray(R.array.priority_array)

        /**
         * Binds data from [Note] object with views.
         * @param uses - [position] of an item in the list.
         */
        fun bind(position: Int) {
            val note = differ.currentList[position] // gets note from list by index
            // Set note params to views.
            binding.run {
                tvTitle.text = note.title // sets title
                tvContent.text = note.content // sets content
                tvPriorityLevel.text = priorityLevels[note.priorityLevel] // sets priority level
                tvDate.text = getFormattedDate(note.date) // sets formatted date
                cvNoteItem.setOnClickListener { listener.onNoteItemClick(note) } // sets on note item click listener
            }
        }


        /**
         * Formatting given date (in millis) into human-read date style.
         */
        private fun getFormattedDate(date: Long): String {
            val formatter =
                SimpleDateFormat(context.getString(R.string.date_format)) // create formatter
            val cal = Calendar.getInstance() // gets calendar instance
            cal.timeInMillis = date // calendar gets date param
            return formatter.format(cal.time) // returns formatted date string
        }
    }


    // Diff Utils.
    private val diffCallback = object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note) =
            oldItem.id == newItem.id // are items the same

        override fun areContentsTheSame(oldItem: Note, newItem: Note) =
            oldItem == newItem // are contents the same
    }
    val differ = AsyncListDiffer(this, diffCallback) // async list differ


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(position)


    override fun getItemCount() = differ.currentList.size
}