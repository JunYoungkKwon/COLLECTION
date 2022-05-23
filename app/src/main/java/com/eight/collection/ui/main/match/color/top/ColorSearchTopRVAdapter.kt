package com.eight.collection.ui.main.match.color.top

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.databinding.ItemWritefirstTopBinding
import com.eight.collection.ui.main.match.color.MatchClothes

class ColorSearchTopRVAdapter(var topList: ArrayList<ColorSearchTop>) : RecyclerView.Adapter<ColorSearchTopRVAdapter.ViewHolder>() {
    private var clickListener: TopClickListener? = null
    private var selectId: Int = -1


    //Add 버튼 클릭시 데이터 추가
    interface TopClickListener {
        fun scrollButtonClickFirst()
        fun scrollButtonClickSecond()
        fun scrollButtonClickThird()
        fun scrollButtonClickFourth()
    }

    fun setTopClickListener(topClickListener: TopClickListener) {
        this.clickListener = topClickListener
    }


    //topList 수 반환
    override fun getItemCount(): Int = topList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.writefirstColorTopTextButton.isChecked = topList[position].focus
        holder.binding.writefirstTopItemLayout.setBackgroundColor(Color.parseColor(topList[position].color))
        holder.binding.writefirstColorTopTextButton.setTextColor(Color.parseColor(topList[position].textcolor))

        holder.bind(topList[position], position)
        holder.setIsRecyclable(false)

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemWritefirstTopBinding = ItemWritefirstTopBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return ViewHolder(binding)
    }

    inner class ViewHolder(val binding: ItemWritefirstTopBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(top: ColorSearchTop, position: Int) {
            binding.writefirstColorTopTextButton.apply {
                //버튼에 Text 대입
                text = top.name

                // select 여부 확인 및 상태 변경
                setOnClickListener {
                    if (topList[position].id > 10 && topList[position].id < 17) {
                        clickListener?.scrollButtonClickFirst()
                    }

                    else if(topList[position].id > 16 && topList[position].id < 22){
                        clickListener?.scrollButtonClickSecond()
                    }

                    else if(topList[position].id > 21 && topList[position].id < 27){
                        clickListener?.scrollButtonClickThird()
                    }

                    else if(topList[position].id > 26 && topList[position].id < 32){
                        clickListener?.scrollButtonClickFourth()
                    }

                    // 처음 선택시
                    if (selectId == -1) {
                        topList[position].focus = true
                        selectId = position
                    }
                    // 선택한거 다시 클릭시
                    else if (selectId == position) {
                        topList[selectId].focus = false
                        topList[selectId].color = "#00ff0000"
                        topList[selectId].textcolor = "#c3b5ac"
                        selectId = -1

                    }
                    // 선택한거말고 다른거 클릭시
                    else {
                        topList[selectId].focus = false
                        topList[position].focus = true
                        selectId = position
                    }
                    notifyDataSetChanged()
                }
            }
        }
    }

    fun getSelectId() : Int{
        return selectId
    }

    fun setSelectId(setId : Int){
        selectId = setId
    }

    fun getRVAData() : ArrayList<MatchClothes> {
        val matchClothes = arrayListOf<MatchClothes>()
        for(i in topList){
            if(i.color != "#00ff0000"){
                matchClothes.apply{
                    add(MatchClothes(i.name,i.color))
                }
            }
        }
        return matchClothes
    }


    fun getSelectIs() : Int {
        var selectIs : Int = 0
        for (i in topList) {
            if (i.focus == true){
                selectIs = 1
            }
        }
        return selectIs
    }

}