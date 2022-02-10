package com.eight.collection.ui.writing.second.who

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.eight.collection.databinding.FragmentWritesecondWhoBinding
import com.eight.collection.ui.writing.CustomDialogInterface
import com.eight.collection.ui.writing.second.weather.WritesecondWeatherRVAdapter
import com.google.android.flexbox.FlexboxLayoutManager

class WritesecondWhoFragment : Fragment(), CustomDialogInterface,
    WritesecondWhoRVAdapter.WhoClickListener{
    lateinit var binding : FragmentWritesecondWhoBinding
    private var whoDatas = ArrayList<WritesecondWho>()
    lateinit var customDialog: WritesecondWhoCustomDialog
    private var idcount : Int = 9
    private var addtext : String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWritesecondWhoBinding.inflate(inflater,container,false)

        // 데이터 리스트 생성
        whoDatas.apply {
            add(WritesecondWho("+", 0))
            add(WritesecondWho("매우추움", 1))
            add(WritesecondWho("매우더움", 2))
            add(WritesecondWho("추움", 3))
            add(WritesecondWho("더움", 4))
            add(WritesecondWho("적당함", 5))
            add(WritesecondWho("눈", 6))
            add(WritesecondWho("비", 7))
            add(WritesecondWho("우박", 8))
        }


        val whoRVAdapter = WritesecondWhoRVAdapter(whoDatas)
        whoRVAdapter.setWhoClickListener(this)

        val flexboxLayoutManager = FlexboxLayoutManager(activity)
        binding.writesecondWhoRecyclerview.adapter = whoRVAdapter
        binding.writesecondWhoRecyclerview.layoutManager = flexboxLayoutManager

        return binding.root
    }

    override fun onAddButtonClicked(addText: String) {
        whoDatas.apply {
            add(WritesecondWho(addText,idcount))
            idcount += 1
        }

        val whoRVAdapter = WritesecondWhoRVAdapter(whoDatas)
        whoRVAdapter.setWhoClickListener(this)

        val flexboxLayoutManager = FlexboxLayoutManager(activity)
        binding.writesecondWhoRecyclerview.adapter = whoRVAdapter
        binding.writesecondWhoRecyclerview.layoutManager = flexboxLayoutManager
    }

    override fun onCancelButtonClicked() {
        Toast.makeText(requireContext(), "취소", Toast.LENGTH_SHORT).show()
    }

    override fun plusButtonClick() {
        customDialog = WritesecondWhoCustomDialog(requireContext(), this)
        customDialog.show()
    }

}