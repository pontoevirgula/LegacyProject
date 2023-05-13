package com.chslcompany.legacyproject.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chslcompany.legacyproject.databinding.AdapterContactItemBinding
import com.chslcompany.legacyproject.framework.data.model.ContactResponse
import com.chslcompany.legacyproject.ui.util.loadImage

class ContactAdapter
    : ListAdapter<ContactResponse, ContactAdapter.UserListItemViewHolder>(ContactAdapter) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListItemViewHolder {
        val itemBinding = AdapterContactItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UserListItemViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: UserListItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class UserListItemViewHolder(
        private val itemBinding : AdapterContactItemBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(user: ContactResponse) {
            itemBinding.name.text = "${user.first_name } ${user.last_name}"
            itemBinding.username.text = user.username
            itemBinding.progressBar.visibility = View.GONE
            itemBinding.picture.loadImage(user.avatar)
        }
    }

    private companion object : DiffUtil.ItemCallback<ContactResponse>() {
        override fun areItemsTheSame(oldItem: ContactResponse, newItem: ContactResponse): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ContactResponse, newItem: ContactResponse): Boolean =
            oldItem == newItem
    }

}

