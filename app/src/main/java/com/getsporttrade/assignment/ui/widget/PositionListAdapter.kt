package com.getsporttrade.assignment.ui.widget

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.getsporttrade.assignment.databinding.ListItemPositionBinding
import com.getsporttrade.assignment.service.cache.entity.Position

/**
 * List view (RecyclerView) adapter that binds the item view holder [PositionViewHolder] which binds the [Position] item
 *
 * @param onItemClick call back for list item click event
 * @param diffUtil required for ListAdapter to uniquely identify its type paramter item
 */
class PositionListAdapter(private val onItemClick: (Position) -> Unit) :
    ListAdapter<Position, PositionViewHolder>(object :
        DiffUtil.ItemCallback<Position>() {
        override fun areItemsTheSame(oldItem: Position, newItem: Position) = (oldItem.identifier == newItem.identifier)

        override fun areContentsTheSame(oldItem: Position, newItem: Position) =
            oldItem.equals(newItem)
    }) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PositionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemPositionBinding.inflate(inflater, parent, false)
        return PositionViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: PositionViewHolder, position: Int) =
        holder.onBind(getItem(position), onClick = onItemClick)
}