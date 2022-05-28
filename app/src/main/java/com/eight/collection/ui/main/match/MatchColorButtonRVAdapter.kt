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
import com.eight.collection.databinding.ItemWritefirstTopBinding
import com.eight.collection.ui.main.match.color.BottomSheet
import com.eight.collection.utils.getPWWC
import kotlin.collections.ArrayList
import kotlin.coroutines.coroutineContext


class MatchColorButtonRVAdapter() : RecyclerView.Adapter<MatchColorButtonRVAdapter.ViewHolder>() {

    private val buttonlist = ArrayList<LastTag>()
    private var mItemClickListener: MyitemClickListener? = null
    private var selectId: Int = -1

    interface MyitemClickListener{
        fun openDialog(content : String)
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
        holder.binding.writefirstColorTopTextButton.isChecked = buttonlist[position].focus
        holder.bind(buttonlist[position], position)
        holder.setIsRecyclable(false)
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MatchColorButtonRVAdapter.ViewHolder {
        val binding: ItemWritefirstTopBinding = ItemWritefirstTopBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return  ViewHolder(binding)
    }



    inner class ViewHolder(val binding: ItemWritefirstTopBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(lastTag: LastTag, position: Int){
            binding.writefirstColorTopTextButton.apply {
                text = lastTag.text

                setOnClickListener{
                    // 처음 선택시
                    if (selectId == -1) {
                        buttonlist[position].focus = true
                        Log.d("dd","${buttonlist[position].text}")
                        mItemClickListener?.openDialog(buttonlist[position].text!!.toString())
                        selectId = position
                    }
                    // 선택한거 다시 클릭시
                    else if (selectId == position) {
                        buttonlist[selectId].focus = false
                        selectId = -1

                    }
                    // 선택한거말고 다른거 클릭시
                    else {
                        buttonlist[selectId].focus = false
                        buttonlist[position].focus = true
                        Log.d("dd","${buttonlist[position].text}")
                        mItemClickListener?.openDialog(buttonlist[position].text!!.toString())
                        selectId = position
                    }
                    notifyDataSetChanged()
                }

            }
        }
    }


}


