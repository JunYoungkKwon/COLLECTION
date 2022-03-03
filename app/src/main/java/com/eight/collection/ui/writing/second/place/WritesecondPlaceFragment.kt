package com.eight.collection.ui.writing.second.place

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.eight.collection.data.remote.getaddedblock.GetAddedBlockResult
import com.eight.collection.data.remote.getaddedblock.GetAddedBlockService
import com.eight.collection.databinding.FragmentWritesecondPlaceBinding
import com.eight.collection.ui.writing.CustomDialogInterface
import com.eight.collection.ui.writing.GetAddedBlockView
import com.eight.collection.ui.writing.first.AddedClothes
import com.eight.collection.ui.writing.first.FixedClothes
import com.eight.collection.ui.writing.first.WritefirstActivity
import com.eight.collection.ui.writing.first.top.WritefirstTopRVAdapter
import com.eight.collection.ui.writing.second.AddedPlace
import com.eight.collection.ui.writing.second.FixedPlace
import com.eight.collection.ui.writing.second.WritesecondActivity
import com.eight.collection.ui.writing.second.weather.WritesecondWeather
import com.google.android.flexbox.FlexboxLayoutManager

class WritesecondPlaceFragment : Fragment(), CustomDialogInterface,
    WritesecondPlaceRVAdapter.PlaceClickListener, WritesecondActivity.GetPlaceDataListener ,
    GetAddedBlockView {
    lateinit var binding : FragmentWritesecondPlaceBinding
    private var placeDatas = ArrayList<WritesecondPlace>()
    lateinit var customDialog: WritesecondPlaceCustomDialog
    private var idcount : Int = 9
    var placeRVAdapter : WritesecondPlaceRVAdapter = WritesecondPlaceRVAdapter(placeDatas)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWritesecondPlaceBinding.inflate(inflater,container,false)

        // 데이터 리스트 생성
        placeDatas.apply {
            add(WritesecondPlace("+", 0,0))
            add(WritesecondPlace("학교", 1,1))
            add(WritesecondPlace("회사", 2,2))
            add(WritesecondPlace("헬스장", 3,3))
            add(WritesecondPlace("집", 4,4))
            add(WritesecondPlace("카페", 5,5))
            add(WritesecondPlace("결혼식장", 6,6))
            add(WritesecondPlace("핫플레이스", 7,7))
            add(WritesecondPlace("휴양지", 8,8))
        }


        placeRVAdapter = WritesecondPlaceRVAdapter(placeDatas)
        placeRVAdapter.setPlaceClickListener(this)

        val flexboxLayoutManager = FlexboxLayoutManager(activity)
        binding.writesecondPlaceRecyclerview.adapter = placeRVAdapter
        binding.writesecondPlaceRecyclerview.layoutManager = flexboxLayoutManager

        getAddedBlock()


        return binding.root
    }

    override fun onAddButtonClicked(addText: String) {
        placeDatas.apply {
            add(WritesecondPlace(addText,idcount))
            idcount += 1
        }
        placeRVAdapter.notifyDataSetChanged()
    }

    override fun onCancelButtonClicked() {
    }

    override fun plusButtonClick() {
        customDialog = WritesecondPlaceCustomDialog(requireContext(), this)
        customDialog.show()
    }

    override fun getFixedData(): ArrayList<Int> {
        var fixedPlace = arrayListOf<Int>()
        fixedPlace = placeRVAdapter.getRVAFixedData()
        return fixedPlace
    }

    override fun getAddedData(): ArrayList<String> {
        var addedPlace = arrayListOf<String>()
        addedPlace = placeRVAdapter.getRVAAddedData()
        return addedPlace
    }

    private fun getAddedBlock(){
        GetAddedBlockService.getAddedBlock(this)
    }

    override fun onGetAddedBlockLoading() {

    }

    override fun onGetAddedBlockSuccess(getaddedblockresult: GetAddedBlockResult) {
        if(getaddedblockresult.aplace != null) {
            for (i in getaddedblockresult.aplace) {
                placeDatas.apply {
                    add(WritesecondPlace(i, idcount))
                    idcount += 1
                }
            }
            placeRVAdapter.notifyDataSetChanged()
        }
    }

    override fun onGetAddedBlockFailure(code: Int, message: String) {

    }

}