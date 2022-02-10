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
import com.eight.collection.ui.writing.first.bottom.WritefirstBottom
import com.eight.collection.ui.writing.first.bottom.WritefirstBottomCustomDialog
import com.eight.collection.ui.writing.first.bottom.WritefirstBottomRVAdapter
import com.google.android.flexbox.FlexboxLayoutManager

class WritefirstShoesFragment : Fragment(), CustomDialogInterface,
    WritefirstShoesRVAdapter.ShoesClickListener {
    lateinit var binding : FragmentWritefirstShoesBinding
    private var shoesDatas = ArrayList<WritefirstShoes>()
    lateinit var customDialog: WritefirstShoesCustomDialog
    private var idcount : Int = 13
    private var addtext : String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWritefirstShoesBinding.inflate(inflater,container,false)

        // 데이터 리스트 생성
        shoesDatas.apply {
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

        val shoesRVAdapter = WritefirstShoesRVAdapter(shoesDatas)
        shoesRVAdapter.setShoesClickListener(this)

        val flexboxLayoutManager = FlexboxLayoutManager(activity)
        binding.writefirstShoesRecyclerview.adapter = shoesRVAdapter
        binding.writefirstShoesRecyclerview.layoutManager = flexboxLayoutManager

        return binding.root
    }

    override fun onAddButtonClicked(addText: String) {
        shoesDatas.apply {
            add(WritefirstShoes(addText,idcount))
            idcount += 1
        }

        val shoesRVAdapter = WritefirstShoesRVAdapter(shoesDatas)
        shoesRVAdapter.setShoesClickListener(this)

        val flexboxLayoutManager = FlexboxLayoutManager(activity)
        binding.writefirstShoesRecyclerview.adapter = shoesRVAdapter
        binding.writefirstShoesRecyclerview.layoutManager = flexboxLayoutManager
    }

    override fun onCancelButtonClicked() {
        Toast.makeText(requireContext(), "취소", Toast.LENGTH_SHORT).show()
    }

    // RVAdapter에서 plus 버튼 클릭시 이벤트 생성
    override fun plusButtonClick() {
        customDialog = WritefirstShoesCustomDialog(requireContext(), this)
        customDialog.show()
    }

}