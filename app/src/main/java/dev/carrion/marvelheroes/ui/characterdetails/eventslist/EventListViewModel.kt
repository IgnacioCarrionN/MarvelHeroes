package dev.carrion.marvelheroes.ui.characterdetails.eventslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dev.carrion.marvelheroes.data.MarvelRepository
import dev.carrion.marvelheroes.models.EventSummary


/**
 * Event List ViewModel
 *
 * This class communicates with the repository and observe changes on the event list on it.
 *
 * @property repository MarvelRepository instance
 * @property id Character ID to search events on DB.
 */
class EventListViewModel(repository: MarvelRepository, id: Int) : ViewModel() {

    val eventList: LiveData<List<EventSummary>> = repository.searchEvents(id)

}