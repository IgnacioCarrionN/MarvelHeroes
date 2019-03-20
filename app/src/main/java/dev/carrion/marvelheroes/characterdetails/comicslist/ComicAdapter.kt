package dev.carrion.marvelheroes.characterdetails.comicslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.carrion.marvelheroes.R
import dev.carrion.marvelheroes.models.ComicSummary

class ComicAdapter : RecyclerView.Adapter<ComicViewHolder>() {

    private var comicList: List<ComicSummary> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicViewHolder =
        ComicViewHolder.create(parent)

    override fun getItemCount(): Int = comicList.size

    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) = holder.bind(comicList[position])

    fun setData(comicList: List<ComicSummary>){
        this.comicList = comicList
        notifyDataSetChanged()
    }

}

class ComicViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val comicName: TextView = view.findViewById(R.id.txtName)

    fun bind(comic: ComicSummary){
        comicName.text = comic.name
    }


    companion object {
        fun create(parent: ViewGroup):ComicViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comics, parent, false)
            return ComicViewHolder(view)
        }
    }
}