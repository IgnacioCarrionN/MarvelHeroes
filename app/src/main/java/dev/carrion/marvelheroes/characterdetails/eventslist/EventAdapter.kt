package dev.carrion.marvelheroes.characterdetails.eventslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.carrion.marvelheroes.R
import dev.carrion.marvelheroes.models.EventSummary

class EventAdapter : RecyclerView.Adapter<EventViewHolder>() {

    private var eventList: List<EventSummary> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder =
        EventViewHolder.create(parent)

    override fun getItemCount(): Int = eventList.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) = holder.bind(eventList[position])

    fun setData(eventList: List<EventSummary>){
        this.eventList = eventList
        notifyDataSetChanged()
    }

}

class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val eventName: TextView = view.findViewById(R.id.txtName)

    fun bind(event: EventSummary){
        eventName.text = event.name
    }


    companion object {
        fun create(parent: ViewGroup):EventViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comics, parent, false)
            return EventViewHolder(view)
        }
    }
}