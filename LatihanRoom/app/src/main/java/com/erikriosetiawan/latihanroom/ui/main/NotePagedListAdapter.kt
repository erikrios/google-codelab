package com.erikriosetiawan.latihanroom.ui.main

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.erikriosetiawan.latihanroom.database.Note
import com.erikriosetiawan.latihanroom.databinding.ItemNoteBinding
import com.erikriosetiawan.latihanroom.ui.insert.NoteAddUpdateActivity
import com.erikriosetiawan.latihanroom.ui.insert.NoteAddUpdateActivity.Companion.EXTRA_NOTE
import com.erikriosetiawan.latihanroom.ui.insert.NoteAddUpdateActivity.Companion.EXTRA_POSITION
import com.erikriosetiawan.latihanroom.ui.insert.NoteAddUpdateActivity.Companion.REQUEST_UPDATE

class NotePagedListAdapter(private val activity: Activity) :
    PagedListAdapter<Note, NotePagedListAdapter.NoteViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotePagedListAdapter.NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotePagedListAdapter.NoteViewHolder, position: Int) =
        holder.bind(getItem(position) as Note)

    inner class NoteViewHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            with(binding) {
                tvItemTitle.text = note.title
                tvItemDate.text = note.date
                tvItemDescription.text = note.description
                cvItemNote.setOnClickListener {
                    val intent = Intent(activity, NoteAddUpdateActivity::class.java)
                    intent.putExtra(EXTRA_POSITION, adapterPosition)
                    intent.putExtra(EXTRA_NOTE, note)
                    activity.startActivityForResult(intent, REQUEST_UPDATE)
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Note> =
            object : DiffUtil.ItemCallback<Note>() {
                override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean =
                    oldItem.title == newItem.title && oldItem.description == newItem.description

                override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean =
                    oldItem == newItem
            }
    }
}