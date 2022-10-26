package com.example.bandasdb.Fragments.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bandasdb.databinding.MusicianInBandListItemBinding
import com.example.bandasdb.models.Musician

class MusiciansInBandAdapter(val listener: MusiciansInBandListener) :
    ListAdapter<
            Musician,
            MusiciansInBandAdapter.ViewHolder
            >(MusiciansInBandDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, position)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder.from(parent, listener)
    }

    class ViewHolder private constructor(
        val binding: MusicianInBandListItemBinding,
        val listener: MusiciansInBandListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Musician, position: Int) {
            binding.apply {
                musicianName.text  = item.name
                gender.text = item.gender
                age.text = item.age.toString()

                ivDelete.setOnClickListener {
                    listener.onDeleteClick(item)
                }
            }
        }

        companion object {
            fun from(parent: ViewGroup, listener: MusiciansInBandListener): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MusicianInBandListItemBinding.inflate(
                    layoutInflater, parent, false
                )
                return ViewHolder(binding, listener)
            }
        }
    }

}

class MusiciansInBandDiffCallback : DiffUtil.ItemCallback<Musician>() {

    override fun areItemsTheSame(oldItem: Musician, newItem: Musician): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Musician, newItem: Musician): Boolean {
        return oldItem == newItem
    }
}

// implementar cliques:
// Crie a interface e passe dentro do ViewHolder
interface MusiciansInBandListener {
    fun onDeleteClick(musician: Musician)
}