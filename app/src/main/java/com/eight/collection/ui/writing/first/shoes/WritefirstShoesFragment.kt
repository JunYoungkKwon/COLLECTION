package com.eight.collection.ui.writing.first.shoes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.eight.collection.databinding.FragmentWritefirstBottomBinding
import com.eight.collection.databinding.FragmentWritefirstShoesBinding
import com.eight.collection.ui.writing.CustomDialogInterface
import com.eight.collection.ui.writing.first.WritefirstActivity
import com.eight.collection.ui.writing.first.bottom.WritefirstBottom
import com.eight.collection.ui.writing.first.bottom.WritefirstBottomCustomDialog
import com.eight.collection.ui.writing.first.bottom.WritefirstBottomRVAdapter
import com.eight.collection.ui.writing.first.etc.WritefirstEtcRVAdapter
import com.google.android.flexbox.FlexboxLayoutManager

class WritefirstShoesFragment : Fragment(), CustomDialogInterface,
    WritefirstShoesRVAdapter.ShoesClickListener, WritefirstActivity.ShoesColorClickListner {
    lateinit var binding : FragmentWritefirstShoesBinding
    private var shoesList = ArrayList<WritefirstShoes>()
    lateinit var customDialog: WritefirstShoesCustomDialog
    private var addItemId : Int = 13
    var shoesRVAdapter : WritefirstShoesRVAdapter = WritefirstShoesRVAdapter(shoesList)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWritefirstShoesBinding.inflate(inflater,container,false)

        // 데이터 리스트 생성
        shoesList.apply {
            add(WritefirstShoes("+", 0))
            add(WritefirstShoes("힐", 1))
            add(WritefirstShoes("단화", 2))
            add(WritefirstShoes("부츠", 3))
            add(WritefirstShoes("워커", 4))
            add(WritefirstShoes("스니커즈", 5))
            add(WritefirstShoes("샌들", 6))
            add(WritefirstShoes("슬리퍼", 7))
            add(WritefirstShoes("더비슈즈", 8))
            add(WritefirstShoes("로퍼/플랫", 9))
            add(WritefirstShoes("뮬", 10))
            add(WritefirstShoes("펌프스", 11))
            add(WritefirstShoes("아쿠아슈즈/트래킹슈즈", 12))
        }

        shoesRVAdapter = WritefirstShoesRVAdapter(shoesList)
        shoesRVAdapter.setShoesClickListener(this)

        val flexboxLayoutManager = FlexboxLayoutManager(activity)
        binding.writefirstShoesRecyclerview.adapter = shoesRVAdapter
        binding.writefirstShoesRecyclerview.layoutManager = flexboxLayoutManager

        return binding.root
    }

    override fun onAddButtonClicked(addText: String) {
        shoesList.apply {
            add(WritefirstShoes(addText,addItemId))
            addItemId += 1
        }
        shoesRVAdapter.notifyDataSetChanged()
    }

    override fun onCancelButtonClicked() {
        Toast.makeText(requireContext(), "취소", Toast.LENGTH_SHORT).show()
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
        shoesRVAdapter.notifyDataSetChanged()
    }

}