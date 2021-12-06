package dev.jakal.codechallenge.ui.schedules.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import dev.jakal.codechallenge.databinding.ItemScheduleEventBinding
import dev.jakal.codechallenge.domain.schedule.model.ScheduleEvent
import dev.jakal.codechallenge.ui.common.viewBinding

class ScheduleEventsAdapter :
    ListAdapter<ScheduleEvent, ScheduleEventItemViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ScheduleEventItemViewHolder(parent.viewBinding(ItemScheduleEventBinding::inflate))

    override fun onBindViewHolder(holder: ScheduleEventItemViewHolder, position: Int) {
        holder.bind(item = getItem(position))
    }
}

private val DIFF_CALLBACK: DiffUtil.ItemCallback<ScheduleEvent> =
    object : DiffUtil.ItemCallback<ScheduleEvent>() {

        override fun areContentsTheSame(oldItem: ScheduleEvent, newItem: ScheduleEvent): Boolean =
            oldItem.id == newItem.id && oldItem.title == newItem.title &&
                    oldItem.subtitle == newItem.subtitle && oldItem.imageUrl == newItem.imageUrl

        override fun areItemsTheSame(oldItem: ScheduleEvent, newItem: ScheduleEvent): Boolean =
            oldItem.id == newItem.id
    }
