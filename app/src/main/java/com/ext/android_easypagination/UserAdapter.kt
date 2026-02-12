package com.ext.android_easypagination

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ext.easypagination.pagination.PagingDataAdapter

class UserAdapter :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>(),
    PagingDataAdapter<String> {

    private val items = mutableListOf<String>()

    // ✅ Replace items (page 1 / refresh)
    override fun setItems(items: List<String>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    // ✅ Append items (next pages)
    override fun addItems(items: List<String>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val text: TextView = itemView.findViewById(R.id.txtItem)

        fun bind(item: String) {
            text.text = item
        }
    }
}
