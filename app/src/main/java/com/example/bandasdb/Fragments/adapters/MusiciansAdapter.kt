package com.example.bandasdb.Fragments.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bandasdb.databinding.MusicianListItemBinding
import com.example.bandasdb.models.Musician

class MusiciansAdapter(val listener: MusicianListener) :
    ListAdapter<
            Musician,
            MusiciansAdapter.ViewHolder
            >(MusicianDiffCallback()) {

//    val swipeToDeleteCallback = SwipeToDeleteCallback()


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

    /**
     * ViewHolder: Fixa os dados do modelo no item da lista
     */
    class ViewHolder private constructor(
        val binding: MusicianListItemBinding,
        val listener: MusicianListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Musician, position: Int) {
            binding.apply {
                musicianName.text = item.name
                musicianAge.text = item.age.toString()
                musicianGender.text = item.gender
            }
        }

        companion object {
            fun from(parent: ViewGroup, listener: MusicianListener): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MusicianListItemBinding.inflate(
                    layoutInflater, parent, false
                )
                return ViewHolder(binding, listener)
            }
        }
    }

}


class MusicianDiffCallback : DiffUtil.ItemCallback<Musician>() {

    override fun areItemsTheSame(oldItem: Musician, newItem: Musician): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Musician, newItem: Musician): Boolean {
        return oldItem == newItem
    }
}


// implementar cliques:
// Crie a interface e passe dentro do ViewHolder
interface MusicianListener {
    fun onClick(posicao: Int)
}