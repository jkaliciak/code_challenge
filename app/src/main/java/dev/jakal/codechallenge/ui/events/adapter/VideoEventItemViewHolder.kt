package dev.jakal.codechallenge.ui.events.adapter

import androidx.recyclerview.widget.RecyclerView
import dev.jakal.codechallenge.common.extensions.humanize
import dev.jakal.codechallenge.databinding.ItemVideoEventBinding
import dev.jakal.codechallenge.domain.events.model.VideoEvent
import dev.jakal.codechallenge.ui.common.extensions.bindPhoto

class VideoEventItemViewHolder(
    private val binding: ItemVideoEventBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: VideoEvent,
        onItemClick: (VideoEvent) -> Unit
    ) = with(binding) {
        binding.image.bindPhoto(binding.root.context, item.imageUrl)
        title.text = item.title
        subtitle.text = item.subtitle
        date.text = item.date.toLocalDateTime().humanize()
        binding.root.setOnClickListener { onItemClick(item) }
    }
}
