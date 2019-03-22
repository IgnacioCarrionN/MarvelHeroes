package dev.carrion.marvelheroes.ui.mainlist

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.carrion.marvelheroes.R
import dev.carrion.marvelheroes.models.CharacterDatabase
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Characters List Fragment
 *
 * This class handles the logic of the CharactersListFragment.
 *
 * @author Ignacio Carrión
 */
class CharactersListFragment : Fragment(), CharacterViewHolder.OnAdapterInteractions {

    private val model : CharactersListViewModel by viewModel()

    private val adapter = CharactersAdapter(this)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_characters_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler(view)
        initSearchEditText(view)

        val loadingDialog: LoadingDialog? = context?.let { LoadingDialog(it) }
        val txtAttribution: TextView = view.findViewById(R.id.txtAttribution)

        model.characterList.observe(this, Observer<PagedList<CharacterDatabase>> {
            Log.d("CharactersListFragment", "list size: ${it?.size}")
            adapter.submitList(it)
        })

        model.networkErrors.observe(this, Observer<String> {
            Toast.makeText(context, "Error: $it", Toast.LENGTH_LONG).show()
        })

        model.loading.observe(this, Observer {
            if(it)
                loadingDialog?.showDialog()
            else
                loadingDialog?.hideDialog()
        })

        model.attributionText.observe(this, Observer {
            txtAttribution.text = it
        })


    }

    /**
     * Initialize Character List RecyclerView
     */
    private fun initRecycler(view: View){
        val recycler: RecyclerView = view.findViewById(R.id.recyclerCharacters)
        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        recycler.addItemDecoration(decoration)

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = RecyclerView.VERTICAL
        recycler.layoutManager = layoutManager
        recycler.adapter = adapter
    }

    /**
     * Initialize Search EditText and set OnKeyPressed listeners.
     */
    private fun initSearchEditText(view: View){
        val etName: EditText = view.findViewById(R.id.etName)
        etName.setOnKeyListener { _, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN){
                searchCharacter(etName.text)
            }
            false
        }

        etName.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_GO){
                searchCharacter(etName.text)
            }
            false
        }
    }

    /**
     * Calls the ViewModel to search by character name.
     * If EditText is empty, query null character name to search all characters.
     *
     * @property text Editable with the text on the Search EditText to check if it's empty.
     */
    private fun searchCharacter(text: Editable){
        if(text.isNotEmpty())
            model.searchCharacters(text.toString())
        else
            model.searchCharacters(null)

        adapter.submitList(null)
    }

    /**
     * Interface method implementation. Called from the adapter when an item is clicked.
     */
    override fun onItemClicked(id: Int) {
        activity?.let {
            val action = CharactersListFragmentDirections.actionCharactersListFragmentToCharacterDetailsFragment(id)
            findNavController().navigate(action)
        }
    }
}