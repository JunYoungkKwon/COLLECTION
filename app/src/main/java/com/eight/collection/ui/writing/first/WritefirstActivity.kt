package com.eight.collection.ui.writing.first

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.ApplicationClass
import com.eight.collection.R
import com.eight.collection.databinding.ActivityWritefirstBinding
import com.eight.collection.ui.signup.SignupThirdActivity
import com.eight.collection.ui.writing.first.top.PostColorText
import com.eight.collection.ui.writing.first.top.WritefirstTopRVAdapter
import com.eight.collection.ui.writing.second.WritesecondActivity
import com.eight.collection.utils.removeColor
import com.eight.collection.utils.setColor
import com.google.android.material.tabs.TabLayoutMediator

class WritefirstActivity() : AppCompatActivity(){

    lateinit var binding : ActivityWritefirstBinding
    private var photoDatas = ArrayList<Photo>()
    val information = arrayListOf("TOP","BOTTOM","SHOES","ETC")


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
        binding.writefirstPhotoRecyclerview.layoutManager = LinearLayoutManager(baseContext,LinearLayoutManager.HORIZONTAL,false)


        //Write First PAGE - 뷰페이저 연결
        val writefirstAdapter = WritefirstVPA(this)
        binding.writefirstColorVp.adapter = writefirstAdapter
        TabLayoutMediator(binding.writefirstColorTb,binding.writefirstColorVp){
                tab, position ->
            tab.text = information[position]
        }.attach()


        // 다음버튼 클릭시 Writing Second Page Start
        binding.writefirstNextButton.setOnClickListener{
            startActivity(Intent(this, WritesecondActivity::class.java))
        }


        binding.writefirstColorTopSelectorRed.setOnClickListener{
            removeColor()
            setColor("red")
        }

        binding.writefirstColorTopSelectorPink.setOnClickListener{
            removeColor()
            setColor("pink")
        }

        binding.writefirstColorTopSelectorYellow.setOnClickListener{
            removeColor()
            setColor("yellow")
        }

        binding.writefirstColorTopSelectorLightyellow.setOnClickListener{
            removeColor()
            setColor("lightyellow")
        }


        binding.writefirstColorTopSelectorGreen.setOnClickListener{
            removeColor()
            setColor("green")
        }

        binding.writefirstColorTopSelectorLightgreen.setOnClickListener{
            removeColor()
            setColor("lightreen")
        }

        binding.writefirstColorTopSelectorOrange.setOnClickListener{
            removeColor()
            setColor("orange")
        }

        binding.writefirstColorTopSelectorNavy.setOnClickListener{
            removeColor()
            setColor("navy")
        }

        binding.writefirstColorTopSelectorBlue.setOnClickListener{
            removeColor()
            setColor("blue")
        }

        binding.writefirstColorTopSelectorLightblue.setOnClickListener{
            removeColor()
            setColor("lightblue")
        }

        binding.writefirstColorTopSelectorPurple.setOnClickListener{
            removeColor()
            setColor("purple")
        }

        binding.writefirstColorTopSelectorLightpurple.setOnClickListener{
            removeColor()
            setColor("lightpurple")
        }

        binding.writefirstColorTopSelectorWhite.setOnClickListener{
            removeColor()
            setColor("white")
        }

        binding.writefirstColorTopSelectorGrey.setOnClickListener{
            removeColor()
            setColor("grey")
        }

        binding.writefirstColorTopSelectorBlack.setOnClickListener{
            removeColor()
            setColor("black")
        }

        binding.writefirstColorTopSelectorLightpeach.setOnClickListener{
            removeColor()
            setColor("lightpeach")
        }

        binding.writefirstColorTopSelectorPinkishgrey.setOnClickListener{
            removeColor()
            setColor("pinkishgrey")
        }

        binding.writefirstColorTopSelectorBrown.setOnClickListener{
            removeColor()
            setColor("brown")
        }
    }

    /*interface ColorClickListner {
        fun colorTextPost(colorText : String)
    }*/

    /*fun setColorClickListener(colorClickListener: ColorClickListner) {
        this.colorclicklistner = colorClickListener
    }*/


}