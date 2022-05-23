package com.eight.collection.ui.main.match

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.R
import com.eight.collection.data.entities.Write.Block
import com.eight.collection.data.entities.Write.Content
import com.eight.collection.data.remote.match.MatchService
import com.eight.collection.databinding.ItemMatchWeatherLastBinding
import com.eight.collection.utils.getPWWC
import kotlin.collections.ArrayList
import kotlin.coroutines.coroutineContext


class MatchButtonRVAdapter() : RecyclerView.Adapter<MatchButtonRVAdapter.ViewHolder>(), DeleteTagView {

    private val buttonlist = ArrayList<LastTag>()
    private var mItemClickListener: MyitemClickListener? = null
    private var PWWC : Int = 3

    interface MyitemClickListener{
        fun onItemClick(lastTag: LastTag, position: Int)
    }

    fun setMyItemClickListener(itemClickListener: MyitemClickListener){
        this.mItemClickListener = itemClickListener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addButton(lastTag:  ArrayList<LastTag>){
        this.buttonlist.clear()
        this.buttonlist.addAll(lastTag)

        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = buttonlist.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(buttonlist[position].isdefault == false) {
            if (buttonlist[position].color != "") {
                holder.binding.matchDefaultRl.setBackgroundColor(Color.parseColor(buttonlist[position].color))
                when (buttonlist[position].color) {
                    //red
                    "#d60f0f" -> {
                        holder.binding.matchDefaultTv.setTextColor(Color.parseColor("#ffffff"))
                        holder.binding.matchDeleteButton.setImageResource(R.drawable.delete_butto_ic_white)
                    }
                    //pink
                    "#f59a9a" -> {
                        holder.binding.matchDefaultTv.setTextColor(Color.parseColor("#ffffff"))
                        holder.binding.matchDeleteButton.setImageResource(R.drawable.delete_butto_ic_white)
                    }
                    //yellow
                    "#ffb203" -> {
                        holder.binding.matchDefaultTv.setTextColor(Color.parseColor("#ffffff"))
                        holder.binding.matchDeleteButton.setImageResource(R.drawable.delete_butto_ic_white)
                    }
                    //lightyellow
                    "#fde6b1" -> {
                        holder.binding.matchDefaultTv.setTextColor(Color.parseColor("#191919"))
                        holder.binding.matchDeleteButton.setImageResource(R.drawable.delete_butto_ic_black)
                    }
                    //green
                    "#71a238" -> {
                        holder.binding.matchDefaultTv.setTextColor(Color.parseColor("#ffffff"))
                        holder.binding.matchDeleteButton.setImageResource(R.drawable.delete_butto_ic_white)
                    }
                    //lightgreen
                    "#b7de89" -> {
                        holder.binding.matchDefaultTv.setTextColor(Color.parseColor("#191919"))
                    }
                    //orange
                    "#ea7831" -> {
                        holder.binding.matchDefaultTv.setTextColor(Color.parseColor("#ffffff"))
                        holder.binding.matchDeleteButton.setImageResource(R.drawable.delete_butto_ic_white)
                    }
                    //navy
                    "#273e88" -> {
                        holder.binding.matchDefaultTv.setTextColor(Color.parseColor("#ffffff"))
                        holder.binding.matchDeleteButton.setImageResource(R.drawable.delete_butto_ic_white)
                    }
                    //blue
                    "#4168e8" -> {
                        holder.binding.matchDefaultTv.setTextColor(Color.parseColor("#ffffff"))
                        holder.binding.matchDeleteButton.setImageResource(R.drawable.delete_butto_ic_white)
                    }
                    //lightblue
                    "#a5b9fa" -> {
                        holder.binding.matchDefaultTv.setTextColor(Color.parseColor("#ffffff"))
                        holder.binding.matchDeleteButton.setImageResource(R.drawable.delete_butto_ic_white)
                    }
                    //purple
                    "#894ac7" -> {
                        holder.binding.matchDefaultTv.setTextColor(Color.parseColor("#ffffff"))
                        holder.binding.matchDeleteButton.setImageResource(R.drawable.delete_butto_ic_white)
                    }
                    //lightpurple
                    "#dcacff" -> {
                        holder.binding.matchDefaultTv.setTextColor(Color.parseColor("#ffffff"))
                        holder.binding.matchDeleteButton.setImageResource(R.drawable.delete_butto_ic_white)
                    }
                    //white
                    "#ffffff" -> {
                        holder.binding.matchDefaultTv.setTextColor(Color.parseColor("#191919"))
                        holder.binding.matchDeleteButton.setImageResource(R.drawable.delete_butto_ic_black)
                    }
                    //grey
                    "#888888" -> {
                        holder.binding.matchDefaultTv.setTextColor(Color.parseColor("#ffffff"))
                        holder.binding.matchDeleteButton.setImageResource(R.drawable.delete_butto_ic_white)
                    }
                    //black
                    "#191919" -> {
                        holder.binding.matchDefaultTv.setTextColor(Color.parseColor("#ffffff"))
                        holder.binding.matchDeleteButton.setImageResource(R.drawable.delete_butto_ic_white)
                    }
                    //lightpeach
                    "#e8dcd5" -> {
                        holder.binding.matchDefaultTv.setTextColor(Color.parseColor("#191919"))
                        holder.binding.matchDeleteButton.setImageResource(R.drawable.delete_butto_ic_black)
                    }
                    //pinkishgrey
                    "#c3b5ac" -> {
                        holder.binding.matchDefaultTv.setTextColor(Color.parseColor("#ffffff"))
                        holder.binding.matchDeleteButton.setImageResource(R.drawable.delete_butto_ic_white)
                    }
                    //brown
                    "#74461f" -> {
                        holder.binding.matchDefaultTv.setTextColor(Color.parseColor("#ffffff"))
                        holder.binding.matchDeleteButton.setImageResource(R.drawable.delete_butto_ic_white)
                    }
                }
            }
        }
        holder.bind(buttonlist[position], position)
        holder.setIsRecyclable(false)
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MatchButtonRVAdapter.ViewHolder {
        val binding: ItemMatchWeatherLastBinding = ItemMatchWeatherLastBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return  ViewHolder(binding)
    }



    inner class ViewHolder(val binding: ItemMatchWeatherLastBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(lastTag: LastTag, position: Int){
            binding.matchDefaultTv.apply {
                if(buttonlist[position].isdefault == true){
                    text = lastTag.text
                } else {
                    text = lastTag.text + "    "
                }
            }

            binding.matchDeleteButton.apply {
                if(buttonlist[position].isdefault == true){
                    visibility = View.GONE
                }
                else{
                    visibility = View.VISIBLE
                }

                setOnClickListener{
                    removeItem(position)
                }
            }

            binding.matchDefaultRl.apply{
                setOnClickListener{
                    mItemClickListener?.onItemClick(buttonlist[position], position)
                }
            }
        }
    }

    fun removeItem(position: Int){
        deleteOneTag(buttonlist[position].text.toString())
        buttonlist.removeAt(position)
        notifyDataSetChanged()
    }

    private fun getContent(content : String) : Content {
        return Content(content)
    }

    private fun deleteOneTag(content : String){
        PWWC = getPWWC()
        Log.d("PWWC","${PWWC}")
        MatchService.deleteTag(this, PWWC, 1, getContent(content))
    }

    override fun onDeleteTagLoading() {
    }

    override fun onDeleteTagSuccess() {
        Log.d("message","Delete Success")
    }

    override fun onDeleteTagFailure(code: Int, message: String) {
        when(code) {
            4016 -> {
                Log.d("message",message)
            }
            else -> {
                Log.d("message","SERVER ERROR")
            }
        }
    }


}


