package dev.carrion.marvelheroes.mainlist

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.carrion.marvelheroes.MarvelViewModel
import dev.carrion.marvelheroes.R
import dev.carrion.marvelheroes.models.Character
import org.koin.android.viewmodel.ext.android.viewModel

class CharactersListFragment : Fragment() {

    private val model : MarvelViewModel by viewModel()

    private val adapter = CharactersAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_characters_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycler: RecyclerView = view.findViewById(R.id.recyclerCharacters)
        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        recycler.addItemDecoration(decoration)

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = RecyclerView.VERTICAL
        recycler.layoutManager = layoutManager

        recycler.adapter = adapter
        model.characterList.observe(this, Observer<PagedList<Character>> {
            Log.d("CharactersListFragment", "list size: ${it?.size}")
            adapter.submitList(it)
        })

        model.networkErrors.observe(this, Observer<String> {
            Toast.makeText(context, "Error: $it", Toast.LENGTH_LONG).show()
        })

        val etName: EditText = view.findViewById(R.id.etName)
        etName.setOnKeyListener { _, keyCode, _ ->
            if(keyCode == KeyEvent.KEYCODE_ENTER){
                if(etName.text.isNotEmpty())
                    model.searchCharacters(etName.text.toString())

            }
            false
        }
    }
}