package com.eight.collection.ui.main.match.color.Etc

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.databinding.ItemWritefirstEtcBinding
import com.eight.collection.databinding.ItemWritefirstTopBinding
import com.eight.collection.ui.main.match.color.MatchClothes

class ColorSearchEtcRVAdapter(var etcList: ArrayList<ColorSearchEtc>) : RecyclerView.Adapter<ColorSearchEtcRVAdapter.ViewHolder>() {
    private var clickListener: EtcClickListener? = null
    private var selectId: Int = -1


    //Add 버튼 클릭시 데이터 추가
    interface EtcClickListener {
        fun scrollButtonClickFirst()
        fun scrollButtonClickSecond()
        fun scrollButtonClickThird()
        fun scrollButtonClickFourth()
    }

    fun setEtcClickListener(etcClickListener: EtcClickListener) {
        this.clickListener = etcClickListener
    }


    //topList 수 반환
    override fun getItemCount(): Int = etcList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.writefirstColorEtcTextButton.isChecked = etcList[position].focus
        holder.binding.writefirstEtcItemLayout.setBackgroundColor(Color.parseColor(etcList[position].color))
        holder.binding.writefirstColorEtcTextButton.setTextColor(Color.parseColor(etcList[position].textcolor))

        holder.bind(etcList[position], position)
        holder.setIsRecyclable(false)

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemWritefirstEtcBinding = ItemWritefirstEtcBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return ViewHolder(binding)
    }

    inner class ViewHolder(val binding: ItemWritefirstEtcBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(etc: ColorSearchEtc, position: Int) {
            binding.writefirstColorEtcTextButton.apply {
                //버튼에 Text 대입
                text = etc.name

                // select 여부 확인 및 상태 변경
                setOnClickListener {
                    if (etcList[position].id > 10 && etcList[position].id < 17) {
                        clickListener?.scrollButtonClickFirst()
                    }

                    else if(etcList[position].id > 16 && etcList[position].id < 22){
                        clickListener?.scrollButtonClickSecond()
                    }

                    else if(etcList[position].id > 21 && etcList[position].id < 27){
                        clickListener?.scrollButtonClickThird()
                    }

                    else if(etcList[position].id > 26 && etcList[position].id < 32){
                        clickListener?.scrollButtonClickFourth()
                    }

                    // 처음 선택시
                    if (selectId == -1) {
                        etcList[position].focus = true
                        selectId = position
                    }
                    // 선택한거 다시 클릭시
                    else if (selectId == position) {
                        etcList[selectId].focus = false
                        etcList[selectId].color = "#00ff0000"
                        etcList[selectId].textcolor = "#c3b5ac"
                        selectId = -1

                    }
                    // 선택한거말고 다른거 클릭시
                    else {
                        etcList[selectId].focus = false
                        etcList[position].focus = true
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
        for(i in etcList){
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
        for (i in etcList) {
            if (i.focus == true){
                selectIs = 1
            }
        }
        return selectIs
    }

}