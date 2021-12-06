package dev.jakal.codechallenge.ui.events.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import dev.jakal.codechallenge.databinding.ItemVideoEventBinding
import dev.jakal.codechallenge.domain.events.model.VideoEvent
import dev.jakal.codechallenge.ui.common.viewBinding

class VideoEventsAdapter(
    private val onItemClick: (VideoEvent) -> Unit
) : ListAdapter<VideoEvent, VideoEventItemViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VideoEventItemViewHolder(parent.viewBinding(ItemVideoEventBinding::inflate))

    override fun onBindViewHolder(holder: VideoEventItemViewHolder, position: Int) {
        holder.bind(
            item = getItem(position),
            onItemClick = onItemClick
        )
    }
}

private val DIFF_CALLBACK: DiffUtil.ItemCallback<VideoEvent> =
    object : DiffUtil.ItemCallback<VideoEvent>() {

        override fun areContentsTheSame(oldItem: VideoEvent, newItem: VideoEvent): Boolean =
            oldItem.id == newItem.id && oldItem.title == newItem.title &&
                    oldItem.subtitle == newItem.subtitle && oldItem.imageUrl == newItem.imageUrl &&
                    oldItem.videoUrl == newItem.videoUrl

        override fun areItemsTheSame(oldItem: VideoEvent, newItem: VideoEvent): Boolean =
            oldItem.id == newItem.id
    }
