package com.eight.collection.ui.writing.second

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.eight.collection.ui.writing.second.place.WritesecondPlaceFragment
import com.eight.collection.ui.writing.second.weather.WritesecondWeatherFragment
import com.eight.collection.ui.writing.second.who.WritesecondWhoFragment

class WritesecondVPA(fragment: WritesecondActivity, var fragmentList : ArrayList<Fragment>) : FragmentStateAdapter(fragment){
    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }


}