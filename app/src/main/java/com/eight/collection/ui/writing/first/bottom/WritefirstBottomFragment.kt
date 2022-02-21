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

        bottomRVAdapter = WritefirstBottomRVAdapter(bottomList)
        bottomRVAdapter.setBottomClickListener(this)
        binding.writefirstBottomRecyclerview.adapter = bottomRVAdapter
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
        var viewHolder : WritefirstBottomRVAdapter.ViewHolder = binding.writefirstBottomRecyclerview.layoutManager?.findViewByPosition(bottomRVAdapter.getSelectId())
            ?.let { binding.writefirstBottomRecyclerview.getChildViewHolder(it) } as WritefirstBottomRVAdapter.ViewHolder

        when (color) {
            "red" -> {
                viewHolder.itemView.setBackgroundColor(Color.parseColor("#d60f0f"))
                viewHolder.binding.writefirstColorBottomTextButton.setTextColor(Color.parseColor("#ffffff"))
            }

            "pink" -> {
                viewHolder.itemView.setBackgroundColor(Color.parseColor("#f59a9a"))
                viewHolder.binding.writefirstColorBottomTextButton.setTextColor(Color.parseColor("#ffffff"))
            }

            "yellow" -> {
                viewHolder.itemView.setBackgroundColor(Color.parseColor("#ffb203"))
                viewHolder.binding.writefirstColorBottomTextButton.setTextColor(Color.parseColor("#ffffff"))
            }

            "lightyellow" -> {
                viewHolder.itemView.setBackgroundColor(Color.parseColor("#fde6b1"))
                viewHolder.binding.writefirstColorBottomTextButton.setTextColor(Color.parseColor("#191919"))
            }

            "green" -> {
                viewHolder.itemView.setBackgroundColor(Color.parseColor("#71a238"))
                viewHolder.binding.writefirstColorBottomTextButton.setTextColor(Color.parseColor("#ffffff"))
            }

            "lightgreen" -> {
                viewHolder.itemView.setBackgroundColor(Color.parseColor("#b7de89"))
                viewHolder.binding.writefirstColorBottomTextButton.setTextColor(Color.parseColor("#191919"))
            }

            "orange" -> {
                viewHolder.itemView.setBackgroundColor(Color.parseColor("#ea7831"))
                viewHolder.binding.writefirstColorBottomTextButton.setTextColor(Color.parseColor("#ffffff"))
            }

            "navy" -> {
                viewHolder.itemView.setBackgroundColor(Color.parseColor("#273e88"))
                viewHolder.binding.writefirstColorBottomTextButton.setTextColor(Color.parseColor("#ffffff"))
            }

            "blue" -> {
                viewHolder.itemView.setBackgroundColor(Color.parseColor("#4168e8"))
                viewHolder.binding.writefirstColorBottomTextButton.setTextColor(Color.parseColor("#ffffff"))
            }

            "lightblue" -> {
                viewHolder.itemView.setBackgroundColor(Color.parseColor("#a5b9fa"))
                viewHolder.binding.writefirstColorBottomTextButton.setTextColor(Color.parseColor("#ffffff"))
            }

            "purple" -> {
                viewHolder.itemView.setBackgroundColor(Color.parseColor("#894ac7"))
                viewHolder.binding.writefirstColorBottomTextButton.setTextColor(Color.parseColor("#ffffff"))
            }

            "lightpurple" -> {
                viewHolder.itemView.setBackgroundColor(Color.parseColor("#dcacff"))
                viewHolder.binding.writefirstColorBottomTextButton.setTextColor(Color.parseColor("#ffffff"))
            }

            "white" -> {
                viewHolder.itemView.setBackgroundColor(Color.parseColor("#ffffff"))
                viewHolder.binding.writefirstColorBottomTextButton.setTextColor(Color.parseColor("#191919"))
            }

            "grey" -> {
                viewHolder.itemView.setBackgroundColor(Color.parseColor("#888888"))
                viewHolder.binding.writefirstColorBottomTextButton.setTextColor(Color.parseColor("#ffffff"))
            }

            "black" -> {
                viewHolder.itemView.setBackgroundColor(Color.parseColor("#191919"))
                viewHolder.binding.writefirstColorBottomTextButton.setTextColor(Color.parseColor("#ffffff"))
            }

            "lightpeach" -> {
                viewHolder.itemView.setBackgroundColor(Color.parseColor("#e8dcd5"))
                viewHolder.binding.writefirstColorBottomTextButton.setTextColor(Color.parseColor("#191919"))
            }

            "pinkishgrey" -> {
                viewHolder.itemView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                viewHolder.binding.writefirstColorBottomTextButton.setTextColor(Color.parseColor("#ffffff"))
            }

            "brown" -> {
                viewHolder.itemView.setBackgroundColor(Color.parseColor("#74461f"))
                viewHolder.binding.writefirstColorBottomTextButton.setTextColor(Color.parseColor("#ffffff"))
            }
        }
    }


}