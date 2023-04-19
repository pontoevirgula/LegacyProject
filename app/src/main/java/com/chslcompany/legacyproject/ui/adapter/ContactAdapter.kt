package com.chslcompany.legacyproject.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chslcompany.legacyproject.data.model.Contact
import com.chslcompany.legacyproject.databinding.AdapterContactItemBinding

class UserListAdapter(private val contactList: MutableList<Contact>) : RecyclerView.Adapter<UserListItemViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun getContacts(list: List<Contact>){
        contactList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListItemViewHolder {
        val itemBinding = AdapterContactItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UserListItemViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: UserListItemViewHolder, position: Int) {
        holder.bind(contactList[position])
    }

    override fun getItemCount(): Int = contactList.size


}

