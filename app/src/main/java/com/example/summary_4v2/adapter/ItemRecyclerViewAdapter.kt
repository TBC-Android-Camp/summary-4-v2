package com.example.summary_4v2.adapter

import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.summary_4v2.R
import com.example.summary_4v2.databinding.ItemFileMessageBinding
import com.example.summary_4v2.databinding.ItemTextMessageBinding
import com.example.summary_4v2.databinding.ItemVoiceMessageBinding
import com.example.summary_4v2.model.Item

class ItemRecyclerViewAdapter : ListAdapter<Item, RecyclerView.ViewHolder>(ItemDiffUtils()) {

    inner class ItemMessageViewHolder(private val binding: ItemTextMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) =
            with(binding) {
                val drawable = R.drawable.ic_attach_background
                Glide.with(itemView.context)
                    .load(item.image)
                    .into(ivAccountImage)
                if (item.image == null || item.image == " ") ivAccountImage.setImageDrawable(
                    drawable.toDrawable()
                )
                tvName.text = item.owner
                tvMessage.text = item.lastMessage
                tvTime.text = item.lastActive
                if (item.unreadMessages < 1) {
                    unreadMessagesCount.visibility = View.GONE
                    unreadMessagesCountBackground.visibility = View.GONE
                } else {
                    unreadMessagesCount.visibility = View.VISIBLE
                    unreadMessagesCountBackground.visibility = View.VISIBLE
                    unreadMessagesCount.text = item.unreadMessages.toString()
                }

                if (item.isTyping) ivTyping.visibility = View.VISIBLE
            }
    }

    inner class ItemVoiceMessageViewHolder(private val binding: ItemVoiceMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) =
            with(binding) {
                val drawable = R.drawable.ic_attach_background
                Glide.with(itemView.context)
                    .load(item.image)
                    .into(ivAccountImage)
                if (item.image == null || item.image == " ") ivAccountImage.setImageDrawable(
                    drawable.toDrawable()
                )
                tvName.text = item.owner
                tvTime.text = item.lastActive
                if (item.isTyping) ivTyping.visibility = View.VISIBLE
            }
    }

    inner class ItemFileMessageViewHolder(private val binding: ItemFileMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) =
            with(binding) {
                val drawable = R.drawable.ic_attach_background
                Glide.with(itemView.context)
                    .load(item.image)
                    .into(ivAccountImage)

                if (item.image == null || item.image == " ") ivAccountImage.setImageDrawable(
                    drawable.toDrawable()
                )
                tvName.text = item.owner
                tvTime.text = item.lastActive
                if (item.isTyping) ivTyping.visibility = View.VISIBLE
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        when (viewType) {
            MESSAGE -> ItemMessageViewHolder(
                ItemTextMessageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            VOICE -> ItemVoiceMessageViewHolder(
                ItemVoiceMessageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            FILE -> ItemFileMessageViewHolder(
                ItemFileMessageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            else -> throw Exception("Invalid View Type!")
        }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemMessageViewHolder -> holder.bind(getItem(position))
            is ItemVoiceMessageViewHolder -> holder.bind(getItem(position))
            is ItemFileMessageViewHolder -> holder.bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).lastMessageType) {
            "text" -> MESSAGE
            "voice" -> VOICE
            else -> FILE
        }
    }

    companion object {
        const val MESSAGE = 0
        const val VOICE = 1
        const val FILE = 2
    }

}