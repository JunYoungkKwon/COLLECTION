package com.eight.collection.ui.main.match.color.Shoes

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.databinding.ItemWritefirstShoesBinding
import com.eight.collection.databinding.ItemWritefirstTopBinding
import com.eight.collection.ui.main.match.color.MatchClothes

class ColorSearchShoesRVAdapter(var shoesList: ArrayList<ColorSearchShoes>) : RecyclerView.Adapter<ColorSearchShoesRVAdapter.ViewHolder>() {
    private var clickListener: ShoesClickListener? = null
    private var selectId: Int = -1


    //Add 버튼 클릭시 데이터 추가
    interface ShoesClickListener {
        fun scrollButtonClickFirst()
        fun scrollButtonClickSecond()
        fun scrollButtonClickThird()
        fun scrollButtonClickFourth()
    }

    fun setShoesClickListener(shoesClickListener: ShoesClickListener) {
        this.clickListener = shoesClickListener
    }


    //topList 수 반환
    override fun getItemCount(): Int = shoesList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.writefirstColorShoesTextButton.isChecked = shoesList[position].focus
        holder.binding.writefirstShoesItemLayout.setBackgroundColor(Color.parseColor(shoesList[position].color))
        holder.binding.writefirstColorShoesTextButton.setTextColor(Color.parseColor(shoesList[position].textcolor))

        holder.bind(shoesList[position], position)
        holder.setIsRecyclable(false)

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemWritefirstShoesBinding = ItemWritefirstShoesBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return ViewHolder(binding)
    }

    inner class ViewHolder(val binding: ItemWritefirstShoesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(shoes: ColorSearchShoes, position: Int) {
            binding.writefirstColorShoesTextButton.apply {
                //버튼에 Text 대입
                text = shoes.name

                // select 여부 확인 및 상태 변경
                setOnClickListener {
                    if (shoesList[position].id > 10 && shoesList[position].id < 17) {
                        clickListener?.scrollButtonClickFirst()
                    }

                    else if(shoesList[position].id > 16 && shoesList[position].id < 22){
                        clickListener?.scrollButtonClickSecond()
                    }

                    else if(shoesList[position].id > 21 && shoesList[position].id < 27){
                        clickListener?.scrollButtonClickThird()
                    }

                    else if(shoesList[position].id > 26 && shoesList[position].id < 32){
                        clickListener?.scrollButtonClickFourth()
                    }

                    // 처음 선택시
                    if (selectId == -1) {
                        shoesList[position].focus = true
                        selectId = position
                    }
                    // 선택한거 다시 클릭시
                    else if (selectId == position) {
                        shoesList[selectId].focus = false
                        shoesList[selectId].color = "#00ff0000"
                        shoesList[selectId].textcolor = "#c3b5ac"
                        selectId = -1

                    }
                    // 선택한거말고 다른거 클릭시
                    else {
                        shoesList[selectId].focus = false
                        shoesList[position].focus = true
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
        for(i in shoesList){
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
        for (i in shoesList) {
            if (i.focus == true){
                selectIs = 1
            }
        }
        return selectIs
    }

}