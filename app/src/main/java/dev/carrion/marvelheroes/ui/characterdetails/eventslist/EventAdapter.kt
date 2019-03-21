package dev.carrion.marvelheroes.ui.characterdetails.eventslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.carrion.marvelheroes.R
import dev.carrion.marvelheroes.models.EventSummary

/**
 * Event Adapter
 *
 * This class has the logic to show Events RecyclerView
 */
class EventAdapter : RecyclerView.Adapter<EventViewHolder>() {

    private var eventList: List<EventSummary> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder =
        EventViewHolder.create(parent)

    override fun getItemCount(): Int = eventList.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) = holder.bind(eventList[position])


    /**
     * Change list of this adapter and notify about the change
     */
    fun setData(eventList: List<EventSummary>){
        this.eventList = eventList
        notifyDataSetChanged()
    }

}

/**
 * Event ViewHolder
 *
 * This class has the logic to create ViewHolder for Event RecyclerView
 *
 */
class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val eventName: TextView = view.findViewById(R.id.txtName)

    /**
     *  Bind data to views.
     */
    fun bind(event: EventSummary){
        eventName.text = event.name
    }


    companion object {
        /**
         * Create instances of this ViewHolder
         *
         * @property parent ViewGroup parent of this view.
         * @return New instance of this ViewHolder.
         */
        fun create(parent: ViewGroup):EventViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comics, parent, false)
            return EventViewHolder(view)
        }
    }
}