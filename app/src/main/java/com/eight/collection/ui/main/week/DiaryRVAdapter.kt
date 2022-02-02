package com.eight.collection.ui.main.week

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.databinding.ItemWeekDiaryBinding
import com.eight.collection.ui.main.lookpoint.PhotoRVAdapter


class DiaryRVAdapter(private  val Diarylist: MutableList<Diary>) : RecyclerView.Adapter<DiaryRVAdapter.ViewHolder>() {

    interface MyitemClickListener{
        fun onRemoveAlbum(position: Int)
    }

    //리스너 객체를 저장할 변수
    private  lateinit var mItemClickListener: MyitemClickListener

    //리스너 객체를 전달받는 함수
    fun setMyitemClickListener(itemClickListener: MyitemClickListener){
        mItemClickListener = itemClickListener
    }

    fun removeItem(position: Int){
        Diarylist.removeAt(position)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): DiaryRVAdapter.ViewHolder {
        val binding: ItemWeekDiaryBinding = ItemWeekDiaryBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return  ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: DiaryRVAdapter.ViewHolder, position: Int) {
        holder.bind(Diarylist[position])
        holder.binding.itemDiaryEditIv.setOnClickListener { mItemClickListener.onRemoveAlbum(position)}
    }


    override fun getItemCount(): Int = Diarylist.size


    inner class ViewHolder(val binding: ItemWeekDiaryBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(diary: Diary){
            binding.itemDiaryImgCountTv.text= diary.imgCount.toString()
            binding.itemDiaryImgIv.setImageResource(diary.coverImg!!)
            binding.itemDiaryDateTv.text= diary.date.toString()
            binding.itemDiaryPointIv.setImageResource(diary.point!!)

            binding.weekDiaryMoodRecyclerView.adapter = MoodRVAdapter(diary.moodList)
            binding.weekDiaryTopRecyclerView.adapter = ToprRVAdapter(diary.topList)
            binding.weekDiaryBottomRecyclerView.adapter = BottomRVAdapter(diary.bottomList)
            binding.weekDiaryShoesRecyclerView.adapter = ShoesRVAdapter(diary.shoesList)
            binding.weekDiaryEtcRecyclerView.adapter = EtcRVAdapter(diary.etcList)
        }
    }


}


