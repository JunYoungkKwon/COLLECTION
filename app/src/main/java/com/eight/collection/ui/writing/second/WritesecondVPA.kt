package com.eight.collection.ui.writing.second

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.eight.collection.ui.writing.second.place.WritesecondPlaceFragment
import com.eight.collection.ui.writing.second.weather.WritesecondWeatherFragment
import com.eight.collection.ui.writing.second.who.WritesecondWhoFragment

class WritesecondVPA(fragment: WritesecondActivity) : FragmentStateAdapter(fragment){
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> WritesecondPlaceFragment()
            1 -> WritesecondWeatherFragment()
            else -> WritesecondWhoFragment()
        }
    }

}