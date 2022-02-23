package com.eight.collection.ui.writing.first.bottom

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.eight.collection.databinding.FragmentWritefirstBottomBinding
import com.eight.collection.ui.writing.CustomDialogInterface
import com.eight.collection.ui.writing.first.WritefirstActivity
import com.eight.collection.ui.writing.first.top.WritefirstTopRVAdapter
import com.google.android.flexbox.FlexboxLayoutManager

class WritefirstBottomFragment : Fragment(), CustomDialogInterface,
    WritefirstBottomRVAdapter.BottomClickListener, WritefirstActivity.BottomColorClickListner{
    lateinit var binding : FragmentWritefirstBottomBinding
    private var bottomList = ArrayList<WritefirstBottom>()
    lateinit var customDialog: WritefirstBottomCustomDialog
    private var addItemId : Int = 13
    lateinit var bottomRVAdapter : WritefirstBottomRVAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWritefirstBottomBinding.inflate(inflater,container,false)

        // 데이터 리스트 생성
        bottomList.apply {
            add(WritefirstBottom("+", 0))
            add(WritefirstBottom("슬랙스", 1))
            add(WritefirstBottom("와이드팬츠", 2))
            add(WritefirstBottom("스키니팬츠", 3))
            add(WritefirstBottom("조거팬츠", 4))
            add(WritefirstBottom("부츠컷팬츠", 5))
            add(WritefirstBottom("미니스커트", 6))
            add(WritefirstBottom("롱스커트", 7))
            add(WritefirstBottom("반바지", 8))
            add(WritefirstBottom("레깅스", 9))
            add(WritefirstBottom("데님팬츠", 10))
            add(WritefirstBottom("미디스커트", 11))
            add(WritefirstBottom("일자팬츠", 12))
        }

        bottomRVAdapter = WritefirstBottomRVAdapter(bottomList)
        bottomRVAdapter.setBottomClickListener(this)

        val flexboxLayoutManager = FlexboxLayoutManager(activity)
        binding.writefirstBottomRecyclerview.adapter = bottomRVAdapter
        binding.writefirstBottomRecyclerview.layoutManager = flexboxLayoutManager

        return binding.root
    }

    override fun onAddButtonClicked(addText: String) {
        bottomList.apply {
            add(WritefirstBottom(addText,addItemId))
            addItemId += 1
        }
        bottomRVAdapter.notifyDataSetChanged()
    }

    override fun onCancelButtonClicked() {
        Toast.makeText(requireContext(), "취소", Toast.LENGTH_SHORT).show()
    }

    // RVAdapter에서 plus 버튼 클릭시 이벤트 생성
    override fun plusButtonClick() {
        customDialog = WritefirstBottomCustomDialog(requireContext(), this)
        customDialog.show()
    }

    override fun bottomChangeButtonColor(color: String) {
        if(bottomRVAdapter.getSelectId() == -1){
            return
        }
        bottomList[bottomRVAdapter.getSelectId()].color = color
        when (color) {
            //red
            "#d60f0f" -> {
                bottomList[bottomRVAdapter.getSelectId()].textcolor = "#ffffff"
            }
            //pink
            "#f59a9a" -> {
                bottomList[bottomRVAdapter.getSelectId()].textcolor = "#ffffff"
            }
            //yellow
            "#ffb203" -> {
                bottomList[bottomRVAdapter.getSelectId()].textcolor = "#ffffff"
            }
            //lightyellow
            "#fde6b1" -> {
                bottomList[bottomRVAdapter.getSelectId()].textcolor = "#191919"
            }
            //green
            "#71a238" -> {
                bottomList[bottomRVAdapter.getSelectId()].textcolor = "#ffffff"
            }
            //lightgreen
            "#b7de89" -> {
                bottomList[bottomRVAdapter.getSelectId()].textcolor = "#191919"
            }
            //orange
            "#ea7831" -> {
                bottomList[bottomRVAdapter.getSelectId()].textcolor = "#ffffff"
            }
            //navy
            "#273e88" -> {
                bottomList[bottomRVAdapter.getSelectId()].textcolor = "#ffffff"
            }
            //blue
            "#4168e8" -> {
                bottomList[bottomRVAdapter.getSelectId()].textcolor = "#ffffff"
            }
            //lightblue
            "#a5b9fa" -> {
                bottomList[bottomRVAdapter.getSelectId()].textcolor = "#ffffff"
            }
            //purple
            "#894ac7" -> {
                bottomList[bottomRVAdapter.getSelectId()].textcolor = "#ffffff"
            }
            //lightpurple
            "#dcacff" -> {
                bottomList[bottomRVAdapter.getSelectId()].textcolor = "#ffffff"
            }
            //white
            "#ffffff" -> {
                bottomList[bottomRVAdapter.getSelectId()].textcolor = "#191919"
            }
            //grey
            "#888888" -> {
                bottomList[bottomRVAdapter.getSelectId()].textcolor = "#ffffff"
            }
            //black
            "#191919" -> {
                bottomList[bottomRVAdapter.getSelectId()].textcolor = "#ffffff"
            }
            //lightpeach
            "#e8dcd5" -> {
                bottomList[bottomRVAdapter.getSelectId()].textcolor = "#191919"
            }
            //pinkishgrey
            "#c3b5ac" -> {
                bottomList[bottomRVAdapter.getSelectId()].textcolor = "#ffffff"
            }
            //brown
            "#74461f" -> {
                bottomList[bottomRVAdapter.getSelectId()].textcolor = "#ffffff"
            }
        }
        bottomRVAdapter.notifyDataSetChanged()
    }


}