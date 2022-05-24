package com.eight.collection.ui.main.match.color.bottom

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.databinding.ItemWritefirstBottomBinding
import com.eight.collection.databinding.ItemWritefirstTopBinding
import com.eight.collection.ui.main.match.color.MatchClothes

class ColorSearchBottomRVAdapter(var bottomList: ArrayList<ColorSearchBottom>) : RecyclerView.Adapter<ColorSearchBottomRVAdapter.ViewHolder>() {
    private var clickListener: BottomClickListener? = null
    private var selectId: Int = -1


    //Add 버튼 클릭시 데이터 추가
    interface BottomClickListener {
        fun scrollButtonClickFirst()
        fun scrollButtonClickSecond()
        fun scrollButtonClickThird()
        fun scrollButtonClickFourth()
    }

    fun setBottomClickListener(bottomClickListener: BottomClickListener) {
        this.clickListener = bottomClickListener
    }


    //topList 수 반환
    override fun getItemCount(): Int = bottomList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.writefirstColorBottomTextButton.isChecked = bottomList[position].focus
        holder.binding.writefirstBottomItemLayout.setBackgroundColor(Color.parseColor(bottomList[position].color))
        holder.binding.writefirstColorBottomTextButton.setTextColor(Color.parseColor(bottomList[position].textcolor))

        holder.bind(bottomList[position], position)
        holder.setIsRecyclable(false)

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemWritefirstBottomBinding = ItemWritefirstBottomBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return ViewHolder(binding)
    }

    inner class ViewHolder(val binding: ItemWritefirstBottomBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(bottom: ColorSearchBottom, position: Int) {
            binding.writefirstColorBottomTextButton.apply {
                //버튼에 Text 대입
                text = bottom.name

                // select 여부 확인 및 상태 변경
                setOnClickListener {
                    if (bottomList[position].id > 10 && bottomList[position].id < 17) {
                        clickListener?.scrollButtonClickFirst()
                    }

                    else if(bottomList[position].id > 16 && bottomList[position].id < 22){
                        clickListener?.scrollButtonClickSecond()
                    }

                    else if(bottomList[position].id > 21 && bottomList[position].id < 27){
                        clickListener?.scrollButtonClickThird()
                    }

                    else if(bottomList[position].id > 26 && bottomList[position].id < 32){
                        clickListener?.scrollButtonClickFourth()
                    }

                    // 처음 선택시
                    if (selectId == -1) {
                        bottomList[position].focus = true
                        selectId = position
                    }
                    // 선택한거 다시 클릭시
                    else if (selectId == position) {
                        bottomList[selectId].focus = false
                        bottomList[selectId].color = "#00ff0000"
                        bottomList[selectId].textcolor = "#c3b5ac"
                        selectId = -1

                    }
                    // 선택한거말고 다른거 클릭시
                    else {
                        bottomList[selectId].focus = false
                        bottomList[position].focus = true
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
        for(i in bottomList){
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
        for (i in bottomList) {
            if (i.focus == true){
                selectIs = 1
            }
        }
        return selectIs
    }

}