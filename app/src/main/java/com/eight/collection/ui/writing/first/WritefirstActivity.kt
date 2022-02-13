package com.eight.collection.ui.writing.first

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.eight.collection.R
import com.eight.collection.databinding.ActivityWritefirstBinding
import com.eight.collection.ui.writing.first.top.ColorTextPost
import com.eight.collection.ui.writing.first.top.WritefirstTop
import com.eight.collection.ui.writing.first.top.WritefirstTopRVAdapter
import com.eight.collection.ui.writing.second.WritesecondActivity
import com.eight.collection.utils.getSelectedId
import com.eight.collection.utils.removeColor
import com.eight.collection.utils.setColor
import com.google.android.material.tabs.TabLayoutMediator

class WritefirstActivity() : AppCompatActivity(){

    lateinit var binding: ActivityWritefirstBinding
    private var photoDatas = ArrayList<Photo>()
    val information = arrayListOf("TOP", "BOTTOM", "SHOES", "ETC")
    private var topDatas = ArrayList<WritefirstTop>()
    var data: String? = com.eight.collection.utils.getColor()
    var selectedId : Int = getSelectedId()
    private var colortextpost: ColorTextPost? = null

    /*private var colorclicklistner : ColorClickListner? = null*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWritefirstBinding.inflate(layoutInflater)

        setContentView(binding.root)


        //Photo 더미 데이터 생성
        photoDatas.apply {
            add(Photo(R.drawable.example1))
            add(Photo(R.drawable.example2))
            add(Photo(R.drawable.example3))
            add(Photo(R.drawable.example4))
            add(Photo(R.drawable.example1))
        }

        //Photo 부분 Adapter 연결
        val photoRVAdapter = PhotoRVAdapter(photoDatas)
        binding.writefirstPhotoRecyclerview.adapter = photoRVAdapter
        binding.writefirstPhotoRecyclerview.layoutManager =
            LinearLayoutManager(baseContext, LinearLayoutManager.HORIZONTAL, false)


        //Write First PAGE - 뷰페이저 연결
        val writefirstAdapter = WritefirstVPA(this)
        binding.writefirstColorVp.adapter = writefirstAdapter
        TabLayoutMediator(binding.writefirstColorTb, binding.writefirstColorVp) { tab, position ->
            tab.text = information[position]
        }.attach()


        // 다음버튼 클릭시 Writing Second Page Start
        binding.writefirstNextButton.setOnClickListener {
            startActivity(Intent(this, WritesecondActivity::class.java))
        }

        binding.writefirstColorTopSelectorRed.setOnClickListener{
            removeColor()
            setColor("red")
            colortextpost?.refreshColor()
        }

        binding.writefirstColorTopSelectorPink.setOnClickListener{
            removeColor()
            setColor("pink")
            colortextpost?.refreshColor()
        }

        binding.writefirstColorTopSelectorYellow.setOnClickListener{
            removeColor()
            setColor("yellow")
            colortextpost?.refreshColor()
        }

        binding.writefirstColorTopSelectorLightyellow.setOnClickListener{
            removeColor()
            setColor("lightyellow")
            colortextpost?.refreshColor()
        }


        binding.writefirstColorTopSelectorGreen.setOnClickListener{
            removeColor()
            setColor("green")
            colortextpost?.refreshColor()
        }

        binding.writefirstColorTopSelectorLightgreen.setOnClickListener{
            removeColor()
            setColor("lightreen")
            colortextpost?.refreshColor()
        }

        binding.writefirstColorTopSelectorOrange.setOnClickListener{
            removeColor()
            setColor("orange")
            colortextpost?.refreshColor()
        }

        binding.writefirstColorTopSelectorNavy.setOnClickListener{
            removeColor()
            setColor("navy")
            colortextpost?.refreshColor()
        }

        binding.writefirstColorTopSelectorBlue.setOnClickListener{
            removeColor()
            setColor("blue")
            colortextpost?.refreshColor()
        }

        binding.writefirstColorTopSelectorLightblue.setOnClickListener{
            removeColor()
            setColor("lightblue")
            colortextpost?.refreshColor()
        }

        binding.writefirstColorTopSelectorPurple.setOnClickListener{
            removeColor()
            setColor("purple")
            colortextpost?.refreshColor()
        }

        binding.writefirstColorTopSelectorLightpurple.setOnClickListener{
            removeColor()
            setColor("lightpurple")
            colortextpost?.refreshColor()
        }

        binding.writefirstColorTopSelectorWhite.setOnClickListener{
            removeColor()
            setColor("white")
            colortextpost?.refreshColor()
        }

        binding.writefirstColorTopSelectorGrey.setOnClickListener{
            removeColor()
            setColor("grey")
            colortextpost?.refreshColor()
        }

        binding.writefirstColorTopSelectorBlack.setOnClickListener{
            removeColor()
            setColor("black")
            colortextpost?.refreshColor()
        }

        binding.writefirstColorTopSelectorLightpeach.setOnClickListener{
            removeColor()
            setColor("lightpeach")
            colortextpost?.refreshColor()
        }

        binding.writefirstColorTopSelectorPinkishgrey.setOnClickListener{
            removeColor()
            setColor("pinkishgrey")
            colortextpost?.refreshColor()
        }

        binding.writefirstColorTopSelectorBrown.setOnClickListener{
            removeColor()
            setColor("brown")
            colortextpost?.refreshColor()
        }
    }

}