package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.databinding.AsteroidViewBinding
import com.udacity.asteroidradar.model.Asteroid

class Adapter(private val itemClkListener: ItemClkListener) :
    RecyclerView.Adapter<Adapter.AsteroidViewHolder>() {

    var asteroidsList = listOf<Asteroid>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        return AsteroidViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        var astr=asteroidsList[position]
        holder.bind(astr, itemClkListener)
    }

    override fun getItemCount() = asteroidsList.size

    class AsteroidViewHolder(private val binding: AsteroidViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(asteroid: Asteroid, onItemClickListener: ItemClkListener) {
            binding.asteroid = asteroid
            binding.clickListener = onItemClickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): AsteroidViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AsteroidViewBinding.inflate(layoutInflater, parent, false)
                return AsteroidViewHolder(binding)
            }
        }
    }
}

class ItemClkListener(val clickListener: (asteroid: Asteroid) -> Unit) {
    fun onClick(asteroid: Asteroid) = clickListener(asteroid)
}