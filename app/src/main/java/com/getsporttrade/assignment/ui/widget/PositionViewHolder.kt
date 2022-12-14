package com.getsporttrade.assignment.ui.widget

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.getsporttrade.assignment.databinding.ListItemPositionBinding
import com.getsporttrade.assignment.service.cache.entity.Position

/**
 * Implementation of the RecyclerView's view holder that binds list item [Position] data and
 * its click listener
 *
 * @param binding generated item layout's data binding class
 */
class PositionViewHolder(private val binding: ListItemPositionBinding) : ViewHolder(binding.root) {

    /**
     * Binds item [Position] data to the view holder [PositionViewHolder] and item click listener
     * to the view holder
     *
     * @param position Position item
     * @param onClick item click listener
     */
    fun onBind(position: Position, onClick: (Position) -> Unit) {
        with(binding) {
            positionName.text = position.name
            positionPrice.text = position.formattedPrice
            root.setOnClickListener { onClick(position) }
        }
    }
}