package dev.carrion.marvelheroes.characterdetails.comicslist

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

class ComicListFragment : Fragment(){

    var tabLayout: TabLayout? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_comics_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val id = arguments?.getInt("characterId") ?: 0
        setProperty("id", id)
        val viewModel = getViewModel<ComicListViewModel>()

        val manager = LinearLayoutManager(context)
        manager.orientation = RecyclerView.VERTICAL
        val adapter = ComicAdapter()
        val comicRecycler: RecyclerView = view.findViewById(R.id.comicRecycler)
        comicRecycler.adapter = adapter
        comicRecycler.layoutManager = manager

        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        comicRecycler.addItemDecoration(decoration)

        viewModel.comicList.observe(this, Observer {
            Log.d("ComicListFragment", "Comic List Size: ${it.size}, CharacterId: $id")
            adapter.setData(it)
            tabLayout?.let { itLayout ->
                itLayout.getTabAt(0)?.text = "Comics(${it.size})"
            }
        })


    }



    companion object {
        fun newInstance(args: Bundle?, tabLayout: TabLayout): ComicListFragment {
            val fragment = ComicListFragment()
            args?.let {
                fragment.arguments = it
            }
            fragment.tabLayout = tabLayout
            return fragment
        }
    }
}