package dev.carrion.marvelheroes.ui.characterdetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import dev.carrion.marvelheroes.GlideApp
import dev.carrion.marvelheroes.R
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.setProperty
import org.koin.android.viewmodel.ext.android.getViewModel


/**
 * Character Details Fragment
 *
 * This class has the logic of the CharacterDetailsFragment
 *
 * @author Ignacio Carrión
 */
class CharacterDetailsFragment : Fragment() {

    private val args: CharacterDetailsFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_character_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("CharacterDetails", "ID: ${args.id}")
        setProperty("id", args.id)
        val viewModel = getViewModel<CharacterDetailsViewModel>()

        val imgThumbnail: ImageView = view.findViewById(R.id.imgThumbnail)
        val txtName: TextView = view.findViewById(R.id.txtName)
        val txtDescription: TextView = view.findViewById(R.id.txtDescription)


        viewModel.character.observe(this, Observer {
            txtName.text = it.name
            txtDescription.text = it.description

            GlideApp.with(fragment)
                .load(it.thumbnail.path)
                .fitCenter()
                .into(imgThumbnail)
        })

        initViewPager(view)

    }


    /**
     * Initialize ViewPager.
     *
     * @property view Fragment View.
     */
    private fun initViewPager(view: View){
        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)
        val viewPager = view.findViewById<ViewPager>(R.id.viewPager)
        val adapter = MarvelPagerAdapter(childFragmentManager, tabLayout, args.id)
        viewPager.adapter = adapter

        tabLayout.setupWithViewPager(viewPager)
    }
}