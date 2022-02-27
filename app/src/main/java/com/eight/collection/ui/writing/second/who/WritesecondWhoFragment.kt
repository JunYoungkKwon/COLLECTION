package com.eight.collection.ui.writing.second.who

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.eight.collection.databinding.FragmentWritesecondWhoBinding
import com.eight.collection.ui.writing.CustomDialogInterface
import com.eight.collection.ui.writing.second.*
import com.eight.collection.ui.writing.second.weather.WritesecondWeatherRVAdapter
import com.google.android.flexbox.FlexboxLayoutManager

class WritesecondWhoFragment : Fragment(), CustomDialogInterface,
    WritesecondWhoRVAdapter.WhoClickListener, WritesecondActivity.GetWhoDataListener {
    lateinit var binding : FragmentWritesecondWhoBinding
    private var whoDatas = ArrayList<WritesecondWho>()
    lateinit var customDialog: WritesecondWhoCustomDialog
    private var idcount : Int = 7
    private var addtext : String? = null
    lateinit var whoRVAdapter : WritesecondWhoRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWritesecondWhoBinding.inflate(inflater,container,false)

        // 데이터 리스트 생성
        whoDatas.apply {
            add(WritesecondWho("+", 0,0))
            add(WritesecondWho("친구", 1,1))
            add(WritesecondWho("가족", 2,2))
            add(WritesecondWho("동료", 3,3))
            add(WritesecondWho("선생님", 4,4))
            add(WritesecondWho("애인", 5,5))
            add(WritesecondWho("혼자", 6,6))
        }


        whoRVAdapter = WritesecondWhoRVAdapter(whoDatas)
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

        whoRVAdapter.notifyDataSetChanged()
    }

    override fun onCancelButtonClicked() {
        Toast.makeText(requireContext(), "취소", Toast.LENGTH_SHORT).show()
    }

    override fun plusButtonClick() {
        customDialog = WritesecondWhoCustomDialog(requireContext(), this)
        customDialog.show()
    }

    override fun getFixedData(): ArrayList<FixedWho> {
        var fixedWho = arrayListOf<FixedWho>()
        fixedWho = whoRVAdapter.getRVAFixedData()
        return fixedWho
    }

    override fun getAddedData(): ArrayList<AddedWho> {
        var addedWho = arrayListOf<AddedWho>()
        addedWho = whoRVAdapter.getRVAAddedData()
        return addedWho
    }

}