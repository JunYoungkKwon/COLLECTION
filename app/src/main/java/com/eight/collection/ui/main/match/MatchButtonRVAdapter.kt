package com.eight.collection.ui.main.match

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.data.entities.Write.Block
import com.eight.collection.data.entities.Write.Content
import com.eight.collection.data.remote.match.MatchService
import com.eight.collection.databinding.ItemMatchWeatherLastBinding
import kotlin.collections.ArrayList
import kotlin.coroutines.coroutineContext


class MatchButtonRVAdapter() : RecyclerView.Adapter<MatchButtonRVAdapter.ViewHolder>(),DeleteTagView {

    private val buttonlist = ArrayList<LastTag>()
    private var mItemClickListener: MyitemClickListener? = null

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


    override fun onBindViewHolder(holder: MatchButtonRVAdapter.ViewHolder, position: Int) {
        holder.bind(buttonlist[position], position)
        holder.setIsRecyclable(false)
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MatchButtonRVAdapter.ViewHolder {
        val binding: ItemMatchWeatherLastBinding = ItemMatchWeatherLastBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return  ViewHolder(binding)
    }



    inner class ViewHolder(val binding: ItemMatchWeatherLastBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(lastTag: LastTag, position: Int){
            binding.matchDefaultRl.apply{
                setOnClickListener{
                    mItemClickListener?.onItemClick(buttonlist[position], position)
                }
            }

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
        MatchService.deleteTag(this, 1, 1, getContent(content))
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


