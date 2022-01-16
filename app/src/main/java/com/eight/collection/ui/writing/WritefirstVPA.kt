package com.eight.collection.ui.writing

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class WritefirstVPA(fragment: WritefirstActivity) : FragmentStateAdapter(fragment){
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> TopFragment()
            1 -> BottomFragment()
            2 -> ShoesFragment()
            else -> EtcFragment()
        }
    }

}