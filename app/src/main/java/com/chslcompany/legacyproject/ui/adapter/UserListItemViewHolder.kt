package com.chslcompany.legacyproject.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.chslcompany.legacyproject.data.model.Contact
import com.chslcompany.legacyproject.databinding.AdapterContactItemBinding
import com.chslcompany.legacyproject.ui.util.loadImage

class UserListItemViewHolder(
    private val itemBinding : AdapterContactItemBinding
) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind( user: Contact) {
        itemBinding.name.text = user.name
        itemBinding.username.text = user.username
        itemBinding.progressBar.visibility = View.GONE
        itemBinding.picture.loadImage(user.img)
    }
}