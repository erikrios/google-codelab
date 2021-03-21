package com.erikriosetiawan.daggerhilttutorial.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.erikriosetiawan.daggerhilttutorial.data.model.User
import com.erikriosetiawan.daggerhilttutorial.databinding.ItemLayoutBinding

class MainAdapter(
    private val users: MutableList<User>,
    private var onItemClickListener: ((User) -> Unit)? = null
) :
    RecyclerView.Adapter<MainAdapter.DataViewHolder>() {

    fun setOnItemClickListener(listener: (User) -> Unit) {
        onItemClickListener = listener
    }

    fun addData(users: List<User>) {
        this.users.addAll(users)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemLayoutBinding.inflate(layoutInflater)
        binding.container.apply {
            this.layoutParams = RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(users[position], onItemClickListener)

    override fun getItemCount(): Int = users.size

    class DataViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User, clickListener: ((User) -> Unit)?) {
            binding.textViewUserName.text = user.name
            binding.textViewUserEmail.text = user.email
            Glide.with(binding.imageViewAvatar.context)
                .load(user.avatar)
                .into(binding.imageViewAvatar)

            itemView.setOnClickListener { clickListener?.let { it(user) } }
        }
    }
}