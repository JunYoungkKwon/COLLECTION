package com.eight.collection.ui.writing

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class WritefirstVPA(fragment: WritefirstActivity) : FragmentStateAdapter(fragment){
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> WritefirstTopFragment()
            1 -> WritefirstBottomFragment()
            2 -> WritefirstShoesFragment()
            else -> WritefirstEtcFragment()
        }
    }

}