package dev.carrion.marvelheroes.mainlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dev.carrion.marvelheroes.GlideApp
import dev.carrion.marvelheroes.R
import dev.carrion.marvelheroes.models.CharacterDatabase

class CharactersAdapter : PagedListAdapter<CharacterDatabase, RecyclerView.ViewHolder>(REPO_COMPARATOR){
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
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<CharacterDatabase>() {
            override fun areItemsTheSame(oldItem: CharacterDatabase, newItem: CharacterDatabase): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: CharacterDatabase, newItem: CharacterDatabase): Boolean =
                oldItem == newItem
        }
    }

}

class CharacterViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    private val thumbnail: ImageView = view.findViewById(R.id.imgThumbnail)

    private val name: TextView = view.findViewById(R.id.txtName)
    private val description: TextView = view.findViewById(R.id.txtDescription)


    fun bind(character: CharacterDatabase?){
        if(character == null){
            val resources = itemView.resources
            name.text = resources.getString(R.string.loading)
        }else{
            GlideApp.with(view)
                .load(character.thumbnail.path)
                .fitCenter()
                .into(thumbnail)

            name.text = character.name
            description.text = character.description
        }
    }

    companion object {
        fun create(parent: ViewGroup): CharacterViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_heroes, parent, false)
            return CharacterViewHolder(view)
        }
    }

}