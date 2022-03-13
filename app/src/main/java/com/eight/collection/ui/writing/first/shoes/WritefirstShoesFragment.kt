package com.eight.collection.ui.writing.first.shoes

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.eight.collection.data.remote.getaddedblock.GetAddedBlockResult
import com.eight.collection.data.remote.getaddedblock.GetAddedBlockService
import com.eight.collection.data.remote.modi.ModiResult
import com.eight.collection.data.remote.modi.ModiService
import com.eight.collection.databinding.FragmentWritefirstBottomBinding
import com.eight.collection.databinding.FragmentWritefirstShoesBinding
import com.eight.collection.ui.writing.CustomDialogInterface
import com.eight.collection.ui.writing.GetAddedBlockView
import com.eight.collection.ui.writing.ModiView
import com.eight.collection.ui.writing.first.AddedClothes
import com.eight.collection.ui.writing.first.FixedClothes
import com.eight.collection.ui.writing.first.WritefirstActivity
import com.eight.collection.ui.writing.first.bottom.WritefirstBottom
import com.eight.collection.ui.writing.first.bottom.WritefirstBottomCustomDialog
import com.eight.collection.ui.writing.first.bottom.WritefirstBottomRVAdapter
import com.eight.collection.ui.writing.first.etc.WritefirstEtc
import com.eight.collection.ui.writing.first.etc.WritefirstEtcRVAdapter
import com.google.android.flexbox.FlexboxLayoutManager

class WritefirstShoesFragment : Fragment(), CustomDialogInterface,
    WritefirstShoesRVAdapter.ShoesClickListener, WritefirstActivity.ShoesColorClickListener,WritefirstActivity.GetShoesDataListener,
    GetAddedBlockView, ModiView,WritefirstActivity.RefreshShoesDataListener {
    lateinit var binding : FragmentWritefirstShoesBinding
    var shoesList = ArrayList<WritefirstShoes>()
    lateinit var customDialog: WritefirstShoesCustomDialog
    private var addItemId : Int = 13
    var shoesRVAdapter : WritefirstShoesRVAdapter = WritefirstShoesRVAdapter(shoesList)
    var date : String = "2021-01-01"
    var looknameEt : EditText? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWritefirstShoesBinding.inflate(inflater,container,false)

        // 데이터 리스트 생성
        shoesList.apply {
            add(WritefirstShoes("+", 0,0))
            add(WritefirstShoes("단화", 1,25))
            add(WritefirstShoes("더비슈즈", 2,26))
            add(WritefirstShoes("로퍼/플랫", 3,27))
            add(WritefirstShoes("뮬", 4,28))
            add(WritefirstShoes("부츠", 5,29))
            add(WritefirstShoes("스니커즈", 6,30))
            add(WritefirstShoes("슬리퍼", 7,31))
            add(WritefirstShoes("샌들", 8,32))
            add(WritefirstShoes("아쿠아슈즈/트래킹슈즈", 9,33))
            add(WritefirstShoes("워커", 10,34))
            add(WritefirstShoes("펌프스", 11,35))
            add(WritefirstShoes("힐", 12,36))
        }

        getAddedBlock()

        modi()

        Handler(Looper.getMainLooper()).postDelayed({
            shoesRVAdapter = WritefirstShoesRVAdapter(shoesList)
            shoesRVAdapter.setShoesClickListener(this)

            val flexboxLayoutManager = FlexboxLayoutManager(activity)
            binding.writefirstShoesRecyclerview.adapter = shoesRVAdapter
            binding.writefirstShoesRecyclerview.layoutManager = flexboxLayoutManager
        }, 200)

        return binding.root
    }

    override fun onAddButtonClicked(addText: String) {
        shoesList.apply {
            add(WritefirstShoes(addText,addItemId))
            addItemId += 1
        }
        shoesRVAdapter.notifyDataSetChanged()
        looknameEt = (activity as WritefirstActivity).binding.writefirstLookstyleTv
        looknameEt!!.clearFocus()
    }

    override fun onCancelButtonClicked() {
    }

    // RVAdapter에서 plus 버튼 클릭시 이벤트 생성
    override fun plusButtonClick() {
        customDialog = WritefirstShoesCustomDialog(requireContext(), this)
        customDialog.show()
    }

    override fun shoesChangeButtonColor(color: String) {
        if(shoesRVAdapter.getSelectId() == -1){
            return
        }
        shoesList[shoesRVAdapter.getSelectId()].color = color
        if(shoesList[shoesRVAdapter.getSelectId()].focus == true) {
            when (color) {
                //red
                "#d60f0f" -> {
                    shoesList[shoesRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //pink
                "#f59a9a" -> {
                    shoesList[shoesRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //yellow
                "#ffb203" -> {
                    shoesList[shoesRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //lightyellow
                "#fde6b1" -> {
                    shoesList[shoesRVAdapter.getSelectId()].textcolor = "#191919"
                }
                //green
                "#71a238" -> {
                    shoesList[shoesRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //lightgreen
                "#b7de89" -> {
                    shoesList[shoesRVAdapter.getSelectId()].textcolor = "#191919"
                }
                //orange
                "#ea7831" -> {
                    shoesList[shoesRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //navy
                "#273e88" -> {
                    shoesList[shoesRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //blue
                "#4168e8" -> {
                    shoesList[shoesRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //lightblue
                "#a5b9fa" -> {
                    shoesList[shoesRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //purple
                "#894ac7" -> {
                    shoesList[shoesRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //lightpurple
                "#dcacff" -> {
                    shoesList[shoesRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //white
                "#ffffff" -> {
                    shoesList[shoesRVAdapter.getSelectId()].textcolor = "#191919"
                }
                //grey
                "#888888" -> {
                    shoesList[shoesRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //black
                "#191919" -> {
                    shoesList[shoesRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //lightpeach
                "#e8dcd5" -> {
                    shoesList[shoesRVAdapter.getSelectId()].textcolor = "#191919"
                }
                //pinkishgrey
                "#c3b5ac" -> {
                    shoesList[shoesRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //brown
                "#74461f" -> {
                    shoesList[shoesRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
            }

            shoesList[shoesRVAdapter.getSelectId()].focus = false
            shoesRVAdapter.setSelectId(-1)
        }
        shoesRVAdapter.notifyDataSetChanged()
    }

    override fun getFixedData(): ArrayList<FixedClothes> {
        var shoesfixedClothes = arrayListOf<FixedClothes>()
        shoesfixedClothes = shoesRVAdapter.getRVAFixedData()
        return shoesfixedClothes
    }

    override fun getAddedData(): ArrayList<AddedClothes> {
        var shoesaddedClothes = arrayListOf<AddedClothes>()
        shoesaddedClothes = shoesRVAdapter.getRVAAddedData()
        return shoesaddedClothes
    }

    private fun getAddedBlock(){
        GetAddedBlockService.getAddedBlock(this)
    }

    override fun onGetAddedBlockLoading() {
    }

    override fun onGetAddedBlockSuccess(getaddedblockresult: GetAddedBlockResult) {
        if (getaddedblockresult.ashoes != null) {
            for (i in getaddedblockresult.ashoes) {
                shoesList.apply {
                    add(WritefirstShoes(i, addItemId))
                    addItemId += 1
                }
            }
            shoesRVAdapter.notifyDataSetChanged()
        }
    }

    override fun onGetAddedBlockFailure(code: Int, message: String) {
    }

    private fun modi(){
        date = (activity as WritefirstActivity).modidate.toString()
        ModiService.modi(this, date!!)
    }

    override fun onModiLoading() {
    }

    override fun onModiSuccess(modiresult: ModiResult) {
        if(modiresult.selected?.shoes.isNullOrEmpty() == false){
            for(i in shoesList){
                for(j in modiresult.selected?.shoes!!){
                    if(i.name == j.cloth){
                        i.apply{
                            i.color = j.color.toString()
                            when (i.color) {
                                "#d60f0f" -> {
                                    i.textcolor = "#ffffff"
                                }
                                //pink
                                "#f59a9a" -> {
                                    i.textcolor = "#ffffff"
                                }
                                //yellow
                                "#ffb203" -> {
                                    i.textcolor = "#ffffff"
                                }

                                //lightyellow
                                "#fde6b1" -> {
                                    i.textcolor = "#191919"
                                }
                                //green
                                "#71a238" -> {
                                    i.textcolor = "#ffffff"
                                }
                                //lightgreen
                                "#b7de89" -> {
                                    i.textcolor = "#191919"
                                }
                                //orange
                                "#ea7831" -> {
                                    i.textcolor = "#ffffff"
                                }
                                //navy
                                "#273e88" -> {
                                    i.textcolor = "#ffffff"
                                }
                                //blue
                                "#4168e8" -> {
                                    i.textcolor = "#ffffff"
                                }
                                //lightblue
                                "#a5b9fa" -> {
                                    i.textcolor = "#ffffff"
                                }
                                //purple
                                "#894ac7" -> {
                                    i.textcolor = "#ffffff"
                                }
                                //lightpurple
                                "#dcacff" -> {
                                    i.textcolor = "#ffffff"
                                }
                                //white
                                "#ffffff" -> {
                                    i.textcolor = "#191919"
                                }
                                //grey
                                "#888888" -> {
                                    i.textcolor = "#ffffff"
                                }
                                //black
                                "#191919" -> {
                                    i.textcolor = "#ffffff"
                                }
                                //lightpeach
                                "#e8dcd5" -> {
                                    i.textcolor = "#191919"
                                }
                                //pinkishgrey
                                "#c3b5ac" -> {
                                    i.textcolor = "#ffffff"
                                }
                                //brown
                                "#74461f" -> {
                                    i.textcolor = "#ffffff"
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onModiFailure(code: Int, message: String) {
    }

    override fun refreshData() {
        for(i in shoesList){
            i.apply{
                i.color = "#00ff0000"
                i.textcolor = "#c3b5ac"
            }
        }
        Log.d("Shoes success","Shoes success")
        shoesRVAdapter.notifyDataSetChanged()
    }


}