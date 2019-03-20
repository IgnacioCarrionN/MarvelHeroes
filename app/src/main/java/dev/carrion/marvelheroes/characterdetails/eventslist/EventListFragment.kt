package dev.carrion.marvelheroes.characterdetails.eventslist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import dev.carrion.marvelheroes.R
import org.koin.android.ext.android.setProperty
import org.koin.android.viewmodel.ext.android.getViewModel

class EventListFragment : Fragment() {

    var tabLayout: TabLayout? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_events_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val id = arguments?.getInt("characterId") ?: 0
        setProperty("id", id)
        val viewModel = getViewModel<EventListViewModel>()

        val manager = LinearLayoutManager(context)
        manager.orientation = RecyclerView.VERTICAL
        val adapter = EventAdapter()
        val eventRecycler: RecyclerView = view.findViewById(R.id.eventRecycler)
        eventRecycler.adapter = adapter
        eventRecycler.layoutManager = manager

        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        eventRecycler.addItemDecoration(decoration)

        viewModel.eventList.observe(this, Observer {
            Log.d("ComicListFragment", "Comic List Size: ${it.size}, CharacterId: $id")
            adapter.setData(it)
            tabLayout?.let { itLayout ->
                itLayout.getTabAt(1)?.text = "Events(${it.size})"
            }
        })

    }

    companion object {
        fun newInstance(args: Bundle?, tabLayout: TabLayout): EventListFragment {
            val fragment = EventListFragment()
            args?.let {
                fragment.arguments = it
            }
            fragment.tabLayout = tabLayout
            return fragment
        }
    }

}