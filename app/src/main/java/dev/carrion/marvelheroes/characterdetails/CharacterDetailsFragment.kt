package dev.carrion.marvelheroes.characterdetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import dev.carrion.marvelheroes.R

class CharacterDetailsFragment : Fragment() {

    val args: CharacterDetailsFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_character_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("CharacterDetails", "ID: ${args.id}")

        val imgThumbnail = view.findViewById<ImageView>(R.id.imgThumbnail)
        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)


        val viewPager = view.findViewById<ViewPager>(R.id.viewPager)
        val adapter = MarvelPagerAdapter(childFragmentManager, tabLayout, args.id)
        viewPager.adapter = adapter

        tabLayout.setupWithViewPager(viewPager)

    }
}