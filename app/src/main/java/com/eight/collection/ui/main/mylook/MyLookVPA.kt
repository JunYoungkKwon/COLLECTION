package com.eight.collection.ui.main.mylook

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.asksira.loopingviewpager.LoopingPagerAdapter
import com.eight.collection.R

class MyLookVPA(
    itemList: ArrayList<String>,
    isInfinite: Boolean
) : LoopingPagerAdapter<String>(itemList, isInfinite) {

    override fun inflateView(
        viewType: Int,
        container: ViewGroup,
        listPosition: Int
    ): View {
        return LayoutInflater.from(container.context).inflate(R.layout.fragment_my_look_banner, container, false)
    }

    override fun bindView(
        convertView: View,
        listPosition: Int,
        viewType: Int
    ) {
        val description = convertView.findViewById<TextView>(R.id.banner_text_tv)
        description.text = itemList?.get(listPosition).toString()
    }
}
