package com.example.bandasdb.Fragments.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bandasdb.databinding.BandListItemBinding
import com.example.bandasdb.models.Band

class BandsAdapter(val listener: BandListener) :
    ListAdapter<
            Band,
            BandsAdapter.ViewHolder
            >(TurmaDiffCallback()) {

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
        val binding: BandListItemBinding,
        val listener: BandListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Band, position: Int) {
            binding.apply {
                bandName.text = item.name
                bandFormation.text = item.formation.toString()
                bandGenre.text = item.genre
            }
        }

        companion object {
            fun from(parent: ViewGroup, listener: BandListener): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = BandListItemBinding.inflate(
                    layoutInflater, parent, false
                )
                return ViewHolder(binding, listener)
            }
        }
    }

}


class TurmaDiffCallback : DiffUtil.ItemCallback<Band>() {

    override fun areItemsTheSame(oldItem: Band, newItem: Band): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Band, newItem: Band): Boolean {
        return oldItem == newItem
    }
}


// implementar cliques:
// Crie a interface e passe dentro do ViewHolder
interface BandListener {
    fun onClick(posicao: Int)
}