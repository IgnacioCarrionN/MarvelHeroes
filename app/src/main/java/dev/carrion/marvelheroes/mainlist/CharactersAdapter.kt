package dev.carrion.marvelheroes.mainlist

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dev.carrion.marvelheroes.R
import dev.carrion.marvelheroes.models.Character

class CharactersAdapter : PagedListAdapter<Character, RecyclerView.ViewHolder>(REPO_COMPARATOR){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CharacterViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val character = getItem(position)
        if(character != null) {
            (holder as CharacterViewHolder).bind(character)
        }
    }

    override fun getItemId(position: Int): Long {
        currentList?.let {
            return it[position]?.id?.toLong() ?: 0L
        }
        return 0L
    }




    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean =
                oldItem == newItem
        }
    }

}

class CharacterViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val name: TextView = view.findViewById(R.id.txtName)

    fun bind(character: Character?){
        if(character == null){
            val resources = itemView.resources
            name.text = resources.getString(R.string.loading)
        }else{
            name.text = character.name
        }
    }

    companion object {
        fun create(parent: ViewGroup): CharacterViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_heroes, parent, false)
            return CharacterViewHolder(view)
        }
    }

}