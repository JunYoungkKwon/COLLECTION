package com.eight.collection.ui.introduce

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter

class IntroduceVPA(fm : FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position : Int) : Fragment {
        return when(position) {
            0 -> IntroduceFirstFragment()
            1 -> IntroduceSecondFragment()
            2 -> IntroduceThirdFragment()
            else -> IntroduceFourthFragment()
        }
    }

    override fun getCount() = 4

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
    }
}