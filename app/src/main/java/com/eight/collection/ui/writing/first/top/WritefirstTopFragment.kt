package com.eight.collection.ui.writing.first.top

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.eight.collection.databinding.FragmentWritefirstTopBinding
import com.eight.collection.ui.writing.CustomDialogInterface
import com.eight.collection.ui.writing.first.WritefirstActivity
import com.google.android.flexbox.FlexboxLayoutManager

class WritefirstTopFragment : Fragment(), CustomDialogInterface,
    WritefirstTopRVAdapter.TopClickListener, WritefirstActivity.TopColorClickListner {
    lateinit var binding : FragmentWritefirstTopBinding
    private var topList = ArrayList<WritefirstTop>()
    lateinit var customDialog: WritefirstTopCustomDialog
    private var addItemId : Int = 13
    lateinit var topRVAdapter : WritefirstTopRVAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWritefirstTopBinding.inflate(inflater,container,false)

        //고정 Top 리스트 생성
        topList.apply {
            add(WritefirstTop("+", 0))
            add(WritefirstTop("맨투맨", 1))
            add(WritefirstTop("티셔츠", 2))
            add(WritefirstTop("블라우스", 3))
            add(WritefirstTop("목폴라", 4))
            add(WritefirstTop("후드티", 5))
            add(WritefirstTop("니트", 6))
            add(WritefirstTop("와이셔츠", 7))
            add(WritefirstTop("나시", 8))
            add(WritefirstTop("패딩", 9))
            add(WritefirstTop("무스탕", 10))
            add(WritefirstTop("후드집업", 11))
            add(WritefirstTop("코트", 12))
        }

        // Top RVA
        topRVAdapter = WritefirstTopRVAdapter(topList)
        topRVAdapter.setTopClickListener(this)

        val flexboxLayoutManager = FlexboxLayoutManager(activity)
        binding.writefirstTopRecyclerview.adapter = topRVAdapter
        binding.writefirstTopRecyclerview.layoutManager = flexboxLayoutManager

        return binding.root
    }


    override fun onAddButtonClicked(addText: String) {
        topList.apply {
            add(WritefirstTop(addText,addItemId))
            addItemId += 1
        }
        topRVAdapter.notifyDataSetChanged()
    }

    override fun onCancelButtonClicked() {
        Toast.makeText(requireContext(), "취소", Toast.LENGTH_SHORT).show()
    }


    // RVAdapter에서 plus 버튼 클릭시 이벤트 생성
    override fun plusButtonClick() {
        customDialog = WritefirstTopCustomDialog(requireContext(), this)
        customDialog.show()
    }


    //Color Change 함수
    override fun topChangeButtonColor(color: String) {
        if(topRVAdapter.getSelectId() == -1){
            return
        }
        topList[topRVAdapter.getSelectId()].color = color
        when (color) {
            //red
            "#d60f0f" -> {
                topList[topRVAdapter.getSelectId()].textcolor = "#ffffff"
            }
            //pink
            "#f59a9a" -> {
                topList[topRVAdapter.getSelectId()].textcolor = "#ffffff"
            }
            //yellow
            "#ffb203" -> {
                topList[topRVAdapter.getSelectId()].textcolor = "#ffffff"
            }
            //lightyellow
            "#fde6b1" -> {
                topList[topRVAdapter.getSelectId()].textcolor = "#191919"
            }
            //green
            "#71a238" -> {
                topList[topRVAdapter.getSelectId()].textcolor = "#ffffff"
            }
            //lightgreen
            "#b7de89" -> {
                topList[topRVAdapter.getSelectId()].textcolor = "#191919"
            }
            //orange
            "#ea7831" -> {
                topList[topRVAdapter.getSelectId()].textcolor = "#ffffff"
            }
            //navy
            "#273e88" -> {
                topList[topRVAdapter.getSelectId()].textcolor = "#ffffff"
            }
            //blue
            "#4168e8" -> {
                topList[topRVAdapter.getSelectId()].textcolor = "#ffffff"
            }
            //lightblue
            "#a5b9fa" -> {
                topList[topRVAdapter.getSelectId()].textcolor = "#ffffff"
            }
            //purple
            "#894ac7" -> {
                topList[topRVAdapter.getSelectId()].textcolor = "#ffffff"
            }
            //lightpurple
            "#dcacff" -> {
                topList[topRVAdapter.getSelectId()].textcolor = "#ffffff"
            }
            //white
            "#ffffff" -> {
                topList[topRVAdapter.getSelectId()].textcolor = "#191919"
            }
            //grey
            "#888888" -> {
                topList[topRVAdapter.getSelectId()].textcolor = "#ffffff"
            }
            //black
            "#191919" -> {
                topList[topRVAdapter.getSelectId()].textcolor = "#ffffff"
            }
            //lightpeach
            "#e8dcd5" -> {
                topList[topRVAdapter.getSelectId()].textcolor = "#191919"
            }
            //pinkishgrey
            "#c3b5ac" -> {
                topList[topRVAdapter.getSelectId()].textcolor = "#ffffff"
            }
            //brown
            "#74461f" -> {
                topList[topRVAdapter.getSelectId()].textcolor = "#ffffff"
            }
        }
        topRVAdapter.notifyDataSetChanged()
    }

}