package com.eight.collection.ui.writing.first

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.R
import com.eight.collection.databinding.ActivityWritefirstBinding
import com.eight.collection.ui.writing.first.bottom.WritefirstBottomFragment
import com.eight.collection.ui.writing.first.etc.WritefirstEtcFragment
import com.eight.collection.ui.writing.first.shoes.WritefirstShoesFragment
import com.eight.collection.ui.writing.first.top.WritefirstTopFragment
import com.eight.collection.ui.writing.first.top.WritefirstTopRVAdapter
import com.eight.collection.ui.writing.second.WritesecondActivity
import com.eight.collection.utils.*
import com.google.android.material.tabs.TabLayoutMediator
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.collections.ArrayList

class WritefirstActivity() : AppCompatActivity(){
    lateinit var binding: ActivityWritefirstBinding
    val photoList = ArrayList<Uri>()
    val photoRVAdapter = PhotoRVAdapter(photoList, this)
    val fragmentList = arrayListOf<Fragment>()
    val information = arrayListOf("TOP", "BOTTOM", "SHOES", "ETC")
    private var topclickListener: WritefirstActivity.TopColorClickListner? = null
    private var bottomclickListner : WritefirstActivity.BottomColorClickListner? = null
    private var shoesclickListner : WritefirstActivity.ShoesColorClickListner? = null
    private var etcclickListner : WritefirstActivity.EtcColorClickListner? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWritefirstBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //날짜 데이터 삽입
        var date = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
        val formatted = date.format(formatter)

        binding.writefirstDateTv.text = formatted


        //이미지 리사이클러뷰 및 갤러리에서 이미지 불러오기
        var getImage_btn = findViewById<ImageView>(R.id.writefirst_add_photo_iv)
        var recyclerview = findViewById<RecyclerView>(R.id.writefirst_photo_recyclerview)

        getImage_btn.setOnClickListener{
            var intent = Intent(Intent.ACTION_PICK)
            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)

            startActivityForResult(intent, 200)
        }

        recyclerview.layoutManager =
            LinearLayoutManager(baseContext, LinearLayoutManager.HORIZONTAL, false)
        recyclerview.adapter = photoRVAdapter



        //Top,Bottom,Shoes,Etc 뷰페이저 연결
        fragmentList.apply {
            add(WritefirstTopFragment())
            add(WritefirstBottomFragment())
            add(WritefirstShoesFragment())
            add(WritefirstEtcFragment())
        }

        val writefirstAdapter = WritefirstVPA(this, fragmentList)
        binding.writefirstColorVp.adapter = writefirstAdapter
        TabLayoutMediator(binding.writefirstColorTb, binding.writefirstColorVp) { tab, position ->
            tab.text = information[position]
        }.attach()


        setTopColorClickListener((fragmentList[0] as WritefirstTopFragment))
        setBottomColorClickListner((fragmentList[1] as WritefirstBottomFragment))
        setShoesColorClickListner((fragmentList[2] as WritefirstShoesFragment))
        setEtcColorClickListner((fragmentList[3] as WritefirstEtcFragment))



        //Color블록 클릭시 데이터 전달
        binding.writefirstColorTopSelectorRed.setOnClickListener{
            topclickListener?.topChangeButtonColor("#d60f0f")
            bottomclickListner?.bottomChangeButtonColor("#d60f0f")
            shoesclickListner?.shoesChangeButtonColor("#d60f0f")
            etcclickListner?.etcChangeButtonColor("#d60f0f")
        }

        binding.writefirstColorTopSelectorPink.setOnClickListener{
            topclickListener?.topChangeButtonColor("#f59a9a")
            bottomclickListner?.bottomChangeButtonColor("#f59a9a")
            shoesclickListner?.shoesChangeButtonColor("#f59a9a")
            etcclickListner?.etcChangeButtonColor("#f59a9a")
        }

        binding.writefirstColorTopSelectorYellow.setOnClickListener{
            topclickListener?.topChangeButtonColor("#ffb203")
            bottomclickListner?.bottomChangeButtonColor("#ffb203")
            shoesclickListner?.shoesChangeButtonColor("#ffb203")
            etcclickListner?.etcChangeButtonColor("#ffb203")
        }

        binding.writefirstColorTopSelectorLightyellow.setOnClickListener{
            topclickListener?.topChangeButtonColor("#fde6b1")
            bottomclickListner?.bottomChangeButtonColor("#fde6b1")
            shoesclickListner?.shoesChangeButtonColor("#fde6b1")
            etcclickListner?.etcChangeButtonColor("#fde6b1")
        }

        binding.writefirstColorTopSelectorGreen.setOnClickListener{
            topclickListener?.topChangeButtonColor("#71a238")
            bottomclickListner?.bottomChangeButtonColor("#71a238")
            shoesclickListner?.shoesChangeButtonColor("#71a238")
            etcclickListner?.etcChangeButtonColor("#71a238")
        }

        binding.writefirstColorTopSelectorLightgreen.setOnClickListener{
            topclickListener?.topChangeButtonColor("#b7de89")
            bottomclickListner?.bottomChangeButtonColor("#b7de89")
            shoesclickListner?.shoesChangeButtonColor("#b7de89")
            etcclickListner?.etcChangeButtonColor("#b7de89")
        }

        binding.writefirstColorTopSelectorOrange.setOnClickListener{
            topclickListener?.topChangeButtonColor("#ea7831")
            bottomclickListner?.bottomChangeButtonColor("#ea7831")
            shoesclickListner?.shoesChangeButtonColor("#ea7831")
            etcclickListner?.etcChangeButtonColor("#ea7831")
        }

        binding.writefirstColorTopSelectorNavy.setOnClickListener{
            topclickListener?.topChangeButtonColor("#273e88")
            bottomclickListner?.bottomChangeButtonColor("#273e88")
            shoesclickListner?.shoesChangeButtonColor("#273e88")
            etcclickListner?.etcChangeButtonColor("#273e88")
        }

        binding.writefirstColorTopSelectorBlue.setOnClickListener{
            topclickListener?.topChangeButtonColor("#4168e8")
            bottomclickListner?.bottomChangeButtonColor("#4168e8")
            shoesclickListner?.shoesChangeButtonColor("#4168e8")
            etcclickListner?.etcChangeButtonColor("#4168e8")
        }

        binding.writefirstColorTopSelectorLightblue.setOnClickListener{
            topclickListener?.topChangeButtonColor("#a5b9fa")
            bottomclickListner?.bottomChangeButtonColor("#a5b9fa")
            shoesclickListner?.shoesChangeButtonColor("#a5b9fa")
            etcclickListner?.etcChangeButtonColor("#a5b9fa")
        }

        binding.writefirstColorTopSelectorPurple.setOnClickListener{
            topclickListener?.topChangeButtonColor("#894ac7")
            bottomclickListner?.bottomChangeButtonColor("#894ac7")
            shoesclickListner?.shoesChangeButtonColor("#894ac7")
            etcclickListner?.etcChangeButtonColor("#894ac7")
        }

        binding.writefirstColorTopSelectorLightpurple.setOnClickListener{
            topclickListener?.topChangeButtonColor("#dcacff")
            bottomclickListner?.bottomChangeButtonColor("#dcacff")
            shoesclickListner?.shoesChangeButtonColor("#dcacff")
            etcclickListner?.etcChangeButtonColor("#dcacff")
        }

        binding.writefirstColorTopSelectorWhite.setOnClickListener{
            topclickListener?.topChangeButtonColor("#ffffff")
            bottomclickListner?.bottomChangeButtonColor("#ffffff")
            shoesclickListner?.shoesChangeButtonColor("#ffffff")
            etcclickListner?.etcChangeButtonColor("#ffffff")
        }

        binding.writefirstColorTopSelectorGrey.setOnClickListener{
            topclickListener?.topChangeButtonColor("#888888")
            bottomclickListner?.bottomChangeButtonColor("#888888")
            shoesclickListner?.shoesChangeButtonColor("#888888")
            etcclickListner?.etcChangeButtonColor("#888888")
        }

        binding.writefirstColorTopSelectorBlack.setOnClickListener{
            topclickListener?.topChangeButtonColor("#191919")
            bottomclickListner?.bottomChangeButtonColor("#191919")
            shoesclickListner?.shoesChangeButtonColor("#191919")
            etcclickListner?.etcChangeButtonColor("#191919")
        }

        binding.writefirstColorTopSelectorLightpeach.setOnClickListener{
            topclickListener?.topChangeButtonColor("#e8dcd5")
            bottomclickListner?.bottomChangeButtonColor("#e8dcd5")
            shoesclickListner?.shoesChangeButtonColor("#e8dcd5")
            etcclickListner?.etcChangeButtonColor("#e8dcd5")
        }

        binding.writefirstColorTopSelectorPinkishgrey.setOnClickListener{
            topclickListener?.topChangeButtonColor("#c3b5ac")
            bottomclickListner?.bottomChangeButtonColor("#c3b5ac")
            shoesclickListner?.shoesChangeButtonColor("#c3b5ac")
            etcclickListner?.etcChangeButtonColor("#c3b5ac")
        }

        binding.writefirstColorTopSelectorBrown.setOnClickListener{
            topclickListener?.topChangeButtonColor("#74461f")
            bottomclickListner?.bottomChangeButtonColor("#74461f")
            shoesclickListner?.shoesChangeButtonColor("#74461f")
            etcclickListner?.etcChangeButtonColor("#74461f")
        }

        //다음버튼 클릭시 Writing Second Activity
        binding.writefirstNextButton.setOnClickListener {
            startActivity(Intent(this, WritesecondActivity::class.java))
        }

    }


    //갤러리에서 이미지 선택 메소드
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == 200) {
            photoList.clear()

            if (data?.clipData != null) {
                val count = data.clipData!!.itemCount
                if (count > 5) {
                    Toast.makeText(applicationContext, "사진은 5장까지 선택 가능합니다.", Toast.LENGTH_LONG)
                    return
                }

                for (i in 0 until count){
                    val imageUri = data.clipData!!.getItemAt(i).uri
                    photoList.add(imageUri)
                    binding.writefirstPhotoDefaultImage1.visibility = View.GONE
                    binding.writefirstPhotoDefaultImage2.visibility = View.GONE
                }

            } else {
                data?.data?.let { uri ->
                    val imageUri : Uri? = data?.data
                    if (imageUri != null){
                        photoList.add(imageUri)
                        binding.writefirstPhotoDefaultImage1.visibility = View.GONE
                        binding.writefirstPhotoDefaultImage2.visibility = View.GONE
                    }
                }
            }
            photoRVAdapter.notifyDataSetChanged()
        }
    }


    //Color 버튼 Interface 작성 및 리스너 연결
    interface TopColorClickListner {
        fun topChangeButtonColor(color : String)
    }

    fun setTopColorClickListener(topcolorClickListener:WritefirstActivity.TopColorClickListner) {
        this.topclickListener = topcolorClickListener
    }

    interface BottomColorClickListner {
        fun bottomChangeButtonColor(color : String)
    }

    fun setBottomColorClickListner(bottomcolorClickListener:WritefirstActivity.BottomColorClickListner) {
        this.bottomclickListner = bottomcolorClickListener
    }

    interface ShoesColorClickListner {
        fun shoesChangeButtonColor(color : String)
    }

    fun setShoesColorClickListner(shoesColorClickListner:WritefirstActivity.ShoesColorClickListner) {
        this.shoesclickListner = shoesColorClickListner
    }

    interface EtcColorClickListner {
        fun etcChangeButtonColor(color : String)
    }

    fun setEtcColorClickListner(etcColorClickListner:WritefirstActivity.EtcColorClickListner) {
        this.etcclickListner = etcColorClickListner
    }

}