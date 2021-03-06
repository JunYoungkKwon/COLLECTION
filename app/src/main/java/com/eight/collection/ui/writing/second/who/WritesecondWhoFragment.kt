package com.eight.collection.ui.writing.second.who

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
import com.eight.collection.databinding.FragmentWritesecondWhoBinding
import com.eight.collection.ui.writing.CustomDialogInterface
import com.eight.collection.ui.writing.GetAddedBlockView
import com.eight.collection.ui.writing.ModiView
import com.eight.collection.ui.writing.first.shoes.WritefirstShoes
import com.eight.collection.ui.writing.second.*
import com.eight.collection.ui.writing.second.place.WritesecondPlaceRVAdapter
import com.eight.collection.ui.writing.second.weather.WritesecondWeatherRVAdapter
import com.google.android.flexbox.FlexboxLayoutManager

class WritesecondWhoFragment : Fragment(), CustomDialogInterface,
    WritesecondWhoRVAdapter.WhoClickListener, WritesecondActivity.GetWhoDataListener,
    GetAddedBlockView, ModiView, WritesecondActivity.RefreshWhoDataListener {
    lateinit var binding : FragmentWritesecondWhoBinding
    var whoDatas = ArrayList<WritesecondWho>()
    lateinit var customDialog: WritesecondWhoCustomDialog
    private var idcount : Int = 7
    var whoRVAdapter : WritesecondWhoRVAdapter = WritesecondWhoRVAdapter(whoDatas)
    var date : String = "2019-01-01"
    var mode : Int = 1
    var selectId : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWritesecondWhoBinding.inflate(inflater,container,false)

        // ????????? ????????? ??????
        whoDatas.apply {
            add(WritesecondWho("+", 0,0))
            add(WritesecondWho("??????", 1,1))
            add(WritesecondWho("??????", 2,2))
            add(WritesecondWho("??????", 3,3))
            add(WritesecondWho("?????????", 4,4))
            add(WritesecondWho("??????", 5,5))
            add(WritesecondWho("??????", 6,6))
        }

        getAddedBlock()

        mode = (activity as WritesecondActivity).mode

        Handler(Looper.getMainLooper()).postDelayed({
            modi()
        }, 100)

        Handler(Looper.getMainLooper()).postDelayed({
            if(mode == 1) {
                whoRVAdapter = WritesecondWhoRVAdapter(whoDatas)
                whoRVAdapter.setWhoClickListener(this)

                val flexboxLayoutManager = FlexboxLayoutManager(activity)
                binding.writesecondWhoRecyclerview.adapter = whoRVAdapter
                binding.writesecondWhoRecyclerview.layoutManager = flexboxLayoutManager
            }
        }, 200)


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
    }

    override fun plusButtonClick() {
        customDialog = WritesecondWhoCustomDialog(requireContext(), this)
        customDialog.show()
    }

    override fun scrollButtonClickFirst() {
        val smoothScroller: RecyclerView.SmoothScroller by lazy {
            object : LinearSmoothScroller(context) {
                override fun getVerticalSnapPreference() = SNAP_TO_START
            }
        }
        smoothScroller.targetPosition = 13
        binding.writesecondWhoRecyclerview.layoutManager?.startSmoothScroll(smoothScroller)
    }
    override fun scrollButtonClickSecond() {
        val smoothScroller: RecyclerView.SmoothScroller by lazy {
            object : LinearSmoothScroller(context) {
                override fun getVerticalSnapPreference() = SNAP_TO_START
            }
        }
        smoothScroller.targetPosition = 20
        binding.writesecondWhoRecyclerview.layoutManager?.startSmoothScroll(smoothScroller)
    }

    override fun scrollButtonClickThird() {
        val smoothScroller: RecyclerView.SmoothScroller by lazy {
            object : LinearSmoothScroller(context) {
                override fun getVerticalSnapPreference() = SNAP_TO_START
            }
        }
        smoothScroller.targetPosition = 27
        binding.writesecondWhoRecyclerview.layoutManager?.startSmoothScroll(smoothScroller)
    }

    override fun scrollButtonClickFourth() {
        val smoothScroller: RecyclerView.SmoothScroller by lazy {
            object : LinearSmoothScroller(context) {
                override fun getVerticalSnapPreference() = SNAP_TO_START
            }
        }
        smoothScroller.targetPosition = 37
        binding.writesecondWhoRecyclerview.layoutManager?.startSmoothScroll(smoothScroller)
    }

    override fun getFixedData(): ArrayList<Int> {
        var fixedWho = arrayListOf<Int>()
        fixedWho = whoRVAdapter.getRVAFixedData()
        return fixedWho
    }

    override fun getAddedData(): ArrayList<String> {
        var addedWho = arrayListOf<String>()
        addedWho = whoRVAdapter.getRVAAddedData()
        return addedWho
    }

    private fun getAddedBlock(){
        GetAddedBlockService.getAddedBlock(this)
    }

    override fun onGetAddedBlockLoading() {

    }

    override fun onGetAddedBlockSuccess(getaddedblockresult: GetAddedBlockResult) {
        if(getaddedblockresult.awho != null) {
            for (i in getaddedblockresult.awho) {
                whoDatas.apply {
                    add(WritesecondWho(i, idcount))
                    idcount += 1
                }
            }
            whoRVAdapter.notifyDataSetChanged()
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
        if(modiresult.selected?.who.isNullOrEmpty() == false){
            for(i in whoDatas){
                for(j in modiresult.selected?.who!!){
                    if(i.name == j){
                        i.apply{
                            i.focus = true
                            selectId = i.id
                        }
                    }
                }
            }
        }
        whoRVAdapter = WritesecondWhoRVAdapter(whoDatas)
        whoRVAdapter.setWhoClickListener(this)

        val flexboxLayoutManager = FlexboxLayoutManager(activity)
        binding.writesecondWhoRecyclerview.adapter = whoRVAdapter
        binding.writesecondWhoRecyclerview.layoutManager = flexboxLayoutManager
        whoRVAdapter.setSelectId(selectId)
    }

    override fun onModiFailure(code: Int, message: String) {
    }

    override fun refreshData() {
        for(i in whoDatas){
            i.apply{
                i.focus = false
            }
        }
        whoRVAdapter.notifyDataSetChanged()
    }

}