package com.eight.collection.ui.writing.second.place

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.data.remote.getaddedblock.GetAddedBlockResult
import com.eight.collection.data.remote.getaddedblock.GetAddedBlockService
import com.eight.collection.data.remote.modi.ModiResult
import com.eight.collection.data.remote.modi.ModiService
import com.eight.collection.databinding.FragmentWritesecondPlaceBinding
import com.eight.collection.ui.writing.CustomDialogInterface
import com.eight.collection.ui.writing.GetAddedBlockView
import com.eight.collection.ui.writing.ModiView
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
    GetAddedBlockView, ModiView, WritesecondActivity.RefreshPlaceDataListener {
    lateinit var binding : FragmentWritesecondPlaceBinding
    private var placeDatas = ArrayList<WritesecondPlace>()
    lateinit var customDialog: WritesecondPlaceCustomDialog
    private var idcount : Int = 9
    var placeRVAdapter : WritesecondPlaceRVAdapter = WritesecondPlaceRVAdapter(placeDatas)
    var date : String = "2019-01-01"
    var mode : Int = 1

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

        getAddedBlock()

        mode = (activity as WritesecondActivity).mode

        Handler(Looper.getMainLooper()).postDelayed({
            modi()
        }, 100)


        Handler(Looper.getMainLooper()).postDelayed({
            if(mode == 1) {
                placeRVAdapter = WritesecondPlaceRVAdapter(placeDatas)
                placeRVAdapter.setPlaceClickListener(this)

                val flexboxLayoutManager = FlexboxLayoutManager(activity)
                binding.writesecondPlaceRecyclerview.adapter = placeRVAdapter
                binding.writesecondPlaceRecyclerview.layoutManager = flexboxLayoutManager
            }
        }, 200)


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

    override fun scrollButtonClickFirst() {
        val smoothScroller: RecyclerView.SmoothScroller by lazy {
            object : LinearSmoothScroller(context) {
                override fun getVerticalSnapPreference() = SNAP_TO_START
            }
        }
        smoothScroller.targetPosition = 13
        binding.writesecondPlaceRecyclerview.layoutManager?.startSmoothScroll(smoothScroller)
    }
    override fun scrollButtonClickSecond() {
        val smoothScroller: RecyclerView.SmoothScroller by lazy {
            object : LinearSmoothScroller(context) {
                override fun getVerticalSnapPreference() = SNAP_TO_START
            }
        }
        smoothScroller.targetPosition = 20
        binding.writesecondPlaceRecyclerview.layoutManager?.startSmoothScroll(smoothScroller)
    }

    override fun scrollButtonClickThird() {
        val smoothScroller: RecyclerView.SmoothScroller by lazy {
            object : LinearSmoothScroller(context) {
                override fun getVerticalSnapPreference() = SNAP_TO_START
            }
        }
        smoothScroller.targetPosition = 27
        binding.writesecondPlaceRecyclerview.layoutManager?.startSmoothScroll(smoothScroller)
    }

    override fun scrollButtonClickFourth() {
        val smoothScroller: RecyclerView.SmoothScroller by lazy {
            object : LinearSmoothScroller(context) {
                override fun getVerticalSnapPreference() = SNAP_TO_START
            }
        }
        smoothScroller.targetPosition = 37
        binding.writesecondPlaceRecyclerview.layoutManager?.startSmoothScroll(smoothScroller)
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

    private fun modi(){
        date = (activity as WritesecondActivity).modidate.toString()
        ModiService.modi(this, date!!)
    }

    override fun onModiLoading() {
    }

    override fun onModiSuccess(modiresult: ModiResult) {
        if(modiresult.selected?.place.isNullOrEmpty() == false){
            for(i in placeDatas){
                for(j in modiresult.selected?.place!!){
                    if(i.name == j){
                        i.apply{
                            i.focus = true
                        }
                    }
                }
            }
        }
        placeRVAdapter = WritesecondPlaceRVAdapter(placeDatas)
        placeRVAdapter.setPlaceClickListener(this)

        val flexboxLayoutManager = FlexboxLayoutManager(activity)
        binding.writesecondPlaceRecyclerview.adapter = placeRVAdapter
        binding.writesecondPlaceRecyclerview.layoutManager = flexboxLayoutManager
    }

    override fun onModiFailure(code: Int, message: String) {
    }

    override fun refreshData() {
        for(i in placeDatas){
            i.apply{
                i.focus = false
            }
        }
        placeRVAdapter.notifyDataSetChanged()
    }

}