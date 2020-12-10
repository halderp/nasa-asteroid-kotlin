package com.phalder.nasa.asteroid.asteroidlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.phalder.nasa.asteroid.R
import com.phalder.nasa.asteroid.databinding.NeoFeedItemViewBinding
import com.phalder.nasa.asteroid.network.Asteroid

class NeoFeedListAdapter(val clickListener: AsteroidItemClickListener): ListAdapter<Asteroid, NeoFeedListAdapter.ViewHolder>(AsteroidDiffCallback())  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(clickListener, item!!)
    }

    class  ViewHolder private constructor(val binding: NeoFeedItemViewBinding) : RecyclerView.ViewHolder(binding.root){

        // populates the data into view holder. Viewholder knows the details of internal structure it can take care of
        // binding data to its views
        fun bind(clickListener:AsteroidItemClickListener, item: Asteroid) {
            binding.asteroid = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
        companion object {
            // Companion Object method to create a ViewHolder from Adapter. Made constructor private. This will act as a factory creation method
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NeoFeedItemViewBinding.inflate(layoutInflater,parent,false)
                return ViewHolder(binding)
            }
        }
    }
}

class AsteroidDiffCallback : DiffUtil.ItemCallback<Asteroid>() {
    override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return (oldItem.id == newItem.id)
    }

    override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        // Asteroid is Kotlin data class automatically checks all member of Asteroid
        return oldItem == newItem
    }
}

class AsteroidItemClickListener(val clickListener: (asteroid: Asteroid) -> Unit) {
    fun onClick(item: Asteroid) = clickListener(item)
}

