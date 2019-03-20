package dev.carrion.marvelheroes.characterdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.tabs.TabLayout
import dev.carrion.marvelheroes.characterdetails.comicslist.ComicListFragment
import dev.carrion.marvelheroes.characterdetails.eventslist.EventListFragment

class MarvelPagerAdapter(fm: FragmentManager, private val tabLayout: TabLayout, private val characterId: Int) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        val bundle = Bundle()
        bundle.putInt("characterId", characterId)

        return when (position) {
            0 -> ComicListFragment.newInstance(bundle, tabLayout)
            1 -> EventListFragment.newInstance(bundle, tabLayout)
            else -> ComicListFragment.newInstance(bundle, tabLayout)
        }
    }

    override fun getCount(): Int = NUM_TABS

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> "Comics"
            1 -> "Events"
            else -> "Error"
        }
    }

    companion object {
        const val NUM_TABS = 2
    }
}