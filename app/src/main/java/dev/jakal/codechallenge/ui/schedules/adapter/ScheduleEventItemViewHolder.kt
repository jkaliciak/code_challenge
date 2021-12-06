package dev.jakal.codechallenge.ui.schedules.adapter

import androidx.recyclerview.widget.RecyclerView
import dev.jakal.codechallenge.common.extensions.humanize
import dev.jakal.codechallenge.databinding.ItemScheduleEventBinding
import dev.jakal.codechallenge.domain.schedule.model.ScheduleEvent
import dev.jakal.codechallenge.ui.common.extensions.bindPhoto

class ScheduleEventItemViewHolder(
    private val binding: ItemScheduleEventBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ScheduleEvent) = with(binding) {
        binding.image.bindPhoto(binding.root.context, item.imageUrl)
        title.text = item.title
        subtitle.text = item.subtitle
        date.text = item.date.toLocalDateTime().humanize()
    }
}
