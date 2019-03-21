package dev.carrion.marvelheroes.ui.characterdetails.comicslist

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

/**
 * Comic List Fragment
 *
 * This class has the logic of the ComicListFragment
 *
 * @author Ignacio Carrión
 */
class ComicListFragment : Fragment(){

    private var tabLayout: TabLayout? = null
    private val adapter = ComicAdapter()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_comics_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val id = arguments?.getInt("characterId") ?: 0
        setProperty("id", id)
        val viewModel = getViewModel<ComicListViewModel>()

        initRecycler(view)

        viewModel.comicList.observe(this, Observer {
            Log.d("ComicListFragment", "Comic List Size: ${it.size}, CharacterId: $id")
            adapter.setData(it)
            tabLayout?.let { itLayout ->
                itLayout.getTabAt(0)?.text = "Comics(${it.size})"
            }
        })

    }


    /**
     * Initialize RecyclerView.
     *
     * @property view Fragment View.
     */
    private fun initRecycler(view: View){
        val manager = LinearLayoutManager(context)
        manager.orientation = RecyclerView.VERTICAL
        val comicRecycler: RecyclerView = view.findViewById(R.id.comicRecycler)
        comicRecycler.adapter = adapter
        comicRecycler.layoutManager = manager

        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        comicRecycler.addItemDecoration(decoration)
    }



    companion object {

        /**
         * Creates new Instance of this fragment.
         *
         * @property args Bundle with arguments for this fragment.
         * @property tabLayout TabLayout View to add to the title the number of comics available.
         * @return ComicListFragment instance.
         */
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