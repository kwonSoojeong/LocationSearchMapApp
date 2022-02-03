package com.crystal.locationsearchmapapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.crystal.locationsearchmapapp.databinding.ViewholderSearchTimeBinding

class SearchRecyclerAdapter(private val searchResultClickListener: (SearchResultEntity) -> Unit) :
    ListAdapter<SearchResultEntity, SearchRecyclerAdapter.ViewHolder>(diffUtil) {

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<SearchResultEntity>() {
            override fun areItemsTheSame(
                oldItem: SearchResultEntity,
                newItem: SearchResultEntity
            ): Boolean {
                return oldItem.locationLatLan == newItem.locationLatLan
            }

            override fun areContentsTheSame(
                oldItem: SearchResultEntity,
                newItem: SearchResultEntity
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    inner class ViewHolder(private val binding: ViewholderSearchTimeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: SearchResultEntity) = with(binding) {
            titleTextView.text = data.BuildingName
            subTitleTextView.text = data.FullAddress
            this.root.setOnClickListener {
                searchResultClickListener(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ViewholderSearchTimeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}