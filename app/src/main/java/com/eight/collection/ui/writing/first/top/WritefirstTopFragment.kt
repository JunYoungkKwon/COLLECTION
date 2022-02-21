package com.eight.collection.ui.writing.first.top

import android.graphics.Color
import android.os.Bundle
import android.util.Log
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
    private var topList = ArrayList<TopFixedItem>()
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
            add(TopFixedItem("+", 0))
            add(TopFixedItem("맨투맨", 1))
            add(TopFixedItem("티셔츠", 2))
            add(TopFixedItem("블라우스", 3))
            add(TopFixedItem("목폴라", 4))
            add(TopFixedItem("후드티", 5))
            add(TopFixedItem("니트", 6))
            add(TopFixedItem("와이셔츠", 7))
            add(TopFixedItem("나시", 8))
            add(TopFixedItem("패딩", 9))
            add(TopFixedItem("무스탕", 10))
            add(TopFixedItem("후드집업", 11))
            add(TopFixedItem("코트", 12))
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
            add(TopFixedItem(addText,addItemId))
            addItemId += 1
        }
        topRVAdapter = WritefirstTopRVAdapter(topList)
        topRVAdapter.setTopClickListener(this)
        binding.writefirstTopRecyclerview.adapter = topRVAdapter
    }

    override fun onCancelButtonClicked() {
        Toast.makeText(requireContext(), "취소", Toast.LENGTH_SHORT).show()
    }


    // RVAdapter에서 plus 버튼 클릭시 이벤트 생성
    override fun plusButtonClick() {
        customDialog = WritefirstTopCustomDialog(requireContext(), this)
        customDialog.show()
    }



    //Interface 함수
    override fun topChangeButtonColor(color: String) {
        if(topRVAdapter.getSelectId() == -1){
            return
        }

        var viewHolder : WritefirstTopRVAdapter.ViewHolder = binding.writefirstTopRecyclerview.layoutManager?.findViewByPosition(topRVAdapter.getSelectId())
            ?.let { binding.writefirstTopRecyclerview.getChildViewHolder(it) } as WritefirstTopRVAdapter.ViewHolder

        when (color) {
            "red" -> {
                viewHolder.itemView.setBackgroundColor(Color.parseColor("#d60f0f"))
                viewHolder.binding.writefirstColorTopTextButton.setTextColor(Color.parseColor("#ffffff"))
            }

            "pink" -> {
                viewHolder.itemView.setBackgroundColor(Color.parseColor("#f59a9a"))
                viewHolder.binding.writefirstColorTopTextButton.setTextColor(Color.parseColor("#ffffff"))
            }

            "yellow" -> {
                viewHolder.itemView.setBackgroundColor(Color.parseColor("#ffb203"))
                viewHolder.binding.writefirstColorTopTextButton.setTextColor(Color.parseColor("#ffffff"))
            }

            "lightyellow" -> {
                viewHolder.itemView.setBackgroundColor(Color.parseColor("#fde6b1"))
                viewHolder.binding.writefirstColorTopTextButton.setTextColor(Color.parseColor("#191919"))
            }

            "green" -> {
                viewHolder.itemView.setBackgroundColor(Color.parseColor("#71a238"))
                viewHolder.binding.writefirstColorTopTextButton.setTextColor(Color.parseColor("#ffffff"))
            }

            "lightgreen" -> {
                viewHolder.itemView.setBackgroundColor(Color.parseColor("#b7de89"))
                viewHolder.binding.writefirstColorTopTextButton.setTextColor(Color.parseColor("#191919"))
            }

            "orange" -> {
                viewHolder.itemView.setBackgroundColor(Color.parseColor("#ea7831"))
                viewHolder.binding.writefirstColorTopTextButton.setTextColor(Color.parseColor("#ffffff"))
            }

            "navy" -> {
                viewHolder.itemView.setBackgroundColor(Color.parseColor("#273e88"))
                viewHolder.binding.writefirstColorTopTextButton.setTextColor(Color.parseColor("#ffffff"))
            }

            "blue" -> {
                viewHolder.itemView.setBackgroundColor(Color.parseColor("#4168e8"))
                viewHolder.binding.writefirstColorTopTextButton.setTextColor(Color.parseColor("#ffffff"))
            }

            "lightblue" -> {
                viewHolder.itemView.setBackgroundColor(Color.parseColor("#a5b9fa"))
                viewHolder.binding.writefirstColorTopTextButton.setTextColor(Color.parseColor("#ffffff"))
            }

            "purple" -> {
                viewHolder.itemView.setBackgroundColor(Color.parseColor("#894ac7"))
                viewHolder.binding.writefirstColorTopTextButton.setTextColor(Color.parseColor("#ffffff"))
            }

            "lightpurple" -> {
                viewHolder.itemView.setBackgroundColor(Color.parseColor("#dcacff"))
                viewHolder.binding.writefirstColorTopTextButton.setTextColor(Color.parseColor("#ffffff"))
            }

            "white" -> {
                viewHolder.itemView.setBackgroundColor(Color.parseColor("#ffffff"))
                viewHolder.binding.writefirstColorTopTextButton.setTextColor(Color.parseColor("#191919"))
            }

            "grey" -> {
                viewHolder.itemView.setBackgroundColor(Color.parseColor("#888888"))
                viewHolder.binding.writefirstColorTopTextButton.setTextColor(Color.parseColor("#ffffff"))
            }

            "black" -> {
                viewHolder.itemView.setBackgroundColor(Color.parseColor("#191919"))
                viewHolder.binding.writefirstColorTopTextButton.setTextColor(Color.parseColor("#ffffff"))
            }

            "lightpeach" -> {
                viewHolder.itemView.setBackgroundColor(Color.parseColor("#e8dcd5"))
                viewHolder.binding.writefirstColorTopTextButton.setTextColor(Color.parseColor("#191919"))
            }

            "pinkishgrey" -> {
                viewHolder.itemView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                viewHolder.binding.writefirstColorTopTextButton.setTextColor(Color.parseColor("#ffffff"))
            }

            "brown" -> {
                viewHolder.itemView.setBackgroundColor(Color.parseColor("#74461f"))
                viewHolder.binding.writefirstColorTopTextButton.setTextColor(Color.parseColor("#ffffff"))
            }
        }
    }

}