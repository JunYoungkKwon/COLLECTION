package com.eight.collection.ui.main.match.color

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ColorSearchVPA(fragment:ColorSearchActivity, var fragmentList : ArrayList<Fragment>) : FragmentStateAdapter(fragment){
    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}