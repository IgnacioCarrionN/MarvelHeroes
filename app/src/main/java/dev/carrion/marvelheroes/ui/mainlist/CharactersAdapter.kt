package dev.carrion.marvelheroes.ui.mainlist

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

/**
 * Adapter for character list RecyclerView.
 * Works with PagedListAdapter.
 */
class CharactersAdapter(val handler: CharacterViewHolder.OnAdapterInteractions) : PagedListAdapter<CharacterDatabase, RecyclerView.ViewHolder>(REPO_COMPARATOR){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CharacterViewHolder.create(parent, handler)
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
        /**
         * Handles the DiffUtil callback to compare list on the RecyclerView and apply the changes.
         */
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<CharacterDatabase>() {
            override fun areItemsTheSame(oldItem: CharacterDatabase, newItem: CharacterDatabase): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: CharacterDatabase, newItem: CharacterDatabase): Boolean =
                oldItem == newItem
        }
    }

}

/**
 * ViewHolder for the CharactersAdapter.
 * Handles binding data to the view and setting an OnClickListener on each item.
 */
class CharacterViewHolder(private val view: View, private val handler: OnAdapterInteractions) : RecyclerView.ViewHolder(view) {

    private val thumbnail: ImageView = view.findViewById(R.id.imgThumbnail)

    private val name: TextView = view.findViewById(R.id.txtName)
    private val description: TextView = view.findViewById(R.id.txtDescription)

    private var character: CharacterDatabase? = null

    init {
        view.setOnClickListener {
            character?.id?.let {
                handler.onItemClicked(it)
            }
        }
    }


    fun bind(character: CharacterDatabase?){
        if(character == null){
            val resources = itemView.resources
            name.text = resources.getString(R.string.loading)
        }else{

            this.character = character

            GlideApp.with(view)
                .load(character.thumbnail.path)
                .fitCenter()
                .into(thumbnail)

            name.text = character.name
            description.text = character.description
        }
    }

    /**
     * Interface to communicate with the parent fragment on item clicks.
     */
    interface OnAdapterInteractions {
        fun onItemClicked(id: Int)
    }

    companion object {
        /**
         * Create a new ViewHolder.
         * @property parent ViewGroup parent of this ViewHolder.
         * @property handler OnAdapterInteractions implementation to handle item clicks.
         * @return CharacterViewHolder instance.
         */
        fun create(parent: ViewGroup, handler: OnAdapterInteractions): CharacterViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_heroes, parent, false)
            return CharacterViewHolder(view, handler)
        }
    }
}

