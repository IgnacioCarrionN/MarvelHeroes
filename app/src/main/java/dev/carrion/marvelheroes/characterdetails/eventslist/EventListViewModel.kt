package dev.carrion.marvelheroes.characterdetails.eventslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dev.carrion.marvelheroes.data.MarvelRepository
import dev.carrion.marvelheroes.models.EventSummary

class EventListViewModel(repository: MarvelRepository, id: Int) : ViewModel() {
    val eventList: LiveData<List<EventSummary>> = repository.searchEvents(id)
}