package com.eight.collection.ui.main.week

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.*
import android.view.View.OnLongClickListener
import android.view.View.inflate
import android.widget.PopupMenu
import android.widget.PopupWindow
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.widget.PopupMenuCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eight.collection.R
import com.eight.collection.data.entities.Cloth
import com.eight.collection.data.entities.Diary
import com.eight.collection.data.entities.Photo
import com.eight.collection.databinding.ItemWeekDiaryBinding
import com.eight.collection.ui.main.MainActivity
import com.eight.collection.ui.writing.first.WritefirstActivity
import com.skydoves.powermenu.OnMenuItemClickListener
import com.skydoves.powermenu.PowerMenu
import com.skydoves.powermenu.PowerMenuItem
import com.skydoves.powermenu.kotlin.createPowerMenu
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


class DiaryRVAdapter(val context: Context) : RecyclerView.Adapter<DiaryRVAdapter.ViewHolder>() {
    private  val diarylist = mutableListOf<Diary>()

    interface MyitemClickListener{
        fun onRemoveDiary(view: View, position: Int)
    }

    private  lateinit var mItemClickListener: MyitemClickListener

    fun setMyitemClickListener(itemClickListener: MyitemClickListener){
        mItemClickListener = itemClickListener
    }

    fun removeItem(position: Int){
        diarylist.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
    }


    @SuppressLint("NotifyDataSetChanged")
    fun addWeekly(diarylist: MutableList<Diary>) {
        this.diarylist.clear()
        this.diarylist.addAll(diarylist)

        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeWeekly() {
        this.diarylist.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): DiaryRVAdapter.ViewHolder {
        val binding: ItemWeekDiaryBinding = ItemWeekDiaryBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return  ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: DiaryRVAdapter.ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.bind(diarylist[position])
        holder.binding.itemDiaryEditIv.setOnClickListener {
                view ->
            if (position != RecyclerView.NO_POSITION) {
                mItemClickListener.onRemoveDiary(view, position)
            }
            else{
                Log.d("Test","error")
            }
            false
        }
    }


    override fun getItemCount(): Int = diarylist.size

    inner class ViewHolder(val binding: ItemWeekDiaryBinding): RecyclerView.ViewHolder(binding.root){


        fun bind(diary: Diary){
            if(diary.imgCount == 0){
                binding.itemDiaryCountBgView.visibility = View.GONE
                binding.itemDiaryImgCountTv.visibility = View.GONE
            }else{
                binding.itemDiaryImgCountTv.text= "+"+ diary.imgCount.toString()
            }


//            if(diary.coverImg == "null"){
//                Glide.with(context).load(diary.coverImg).into(binding.itemDiaryImgIv)
//            }
//            else{
//                Glide.with(context).load(diary.coverImg).into(binding.itemDiaryImgIv)
//            }

            if(diary.coverImg.isNullOrEmpty()){
                Glide.with(context).load(R.drawable.week_diary_default).into(binding.itemDiaryImgIv)
            }
            else{
                Glide.with(context).load(diary.coverImg).into(binding.itemDiaryImgIv)
            }

            if(diary.topList.isNullOrEmpty()){
                diary.topList.add(Cloth("해당 항목 없음", ""))
            }

            if(diary.bottomList.isNullOrEmpty()){
                diary.bottomList.add(Cloth("해당 항목 없음", ""))
            }

            if(diary.shoesList.isNullOrEmpty()){
                diary.shoesList.add(Cloth("해당 항목 없음", ""))
            }

            if(diary.etcList.isNullOrEmpty()){
                diary.etcList.add(Cloth("해당 항목 없음", ""))
            }

            val date: Date = diary.date
            val localdate: LocalDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
            val formatters = DateTimeFormatter.ofPattern("yyyy/MM/dd")
            val convertDate: String = localdate.format(formatters)
            binding.itemDiaryDateTv.text= convertDate
            when(diary.point!!){
                1 -> binding.itemDiaryPointIv.setImageResource(R.drawable.ic_diary_point_1)
                2 ->  binding.itemDiaryPointIv.setImageResource(R.drawable.ic_diary_point_2)
                3 ->  binding.itemDiaryPointIv.setImageResource(R.drawable.ic_diary_point_3)
                4 ->  binding.itemDiaryPointIv.setImageResource(R.drawable.ic_diary_point_4)
                5 ->  binding.itemDiaryPointIv.setImageResource(R.drawable.ic_diary_point_5)
                else ->  binding.itemDiaryPointIv.setImageResource(R.drawable.ic_diary_point_5)
            }

            val mood = diary.placeList + diary.weatherList + diary.whoList

            if(mood.isNullOrEmpty()){
                diary.placeList.add("선택 사항 없음")
            }
            val mood1 = diary.placeList + diary.weatherList + diary.whoList
            binding.weekDiaryMoodRecyclerView.adapter = MoodRVAdapter(mood1.toMutableList())
            binding.weekDiaryTopRecyclerView.adapter = ToprRVAdapter(diary.topList)
            binding.weekDiaryBottomRecyclerView.adapter = BottomRVAdapter(diary.bottomList)
            binding.weekDiaryShoesRecyclerView.adapter = ShoesRVAdapter(diary.shoesList)
            binding.weekDiaryEtcRecyclerView.adapter = EtcRVAdapter(diary.etcList)
        }
    }


}



