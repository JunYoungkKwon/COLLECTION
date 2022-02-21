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
    private var clickListener: WritefirstActivity.ColorClickListner? = null

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

        setColorClickListener((fragmentList[0] as WritefirstTopFragment))



        //Color블록 클릭시 데이터 전달
        binding.writefirstColorTopSelectorRed.setOnClickListener{
            clickListener?.changeButtonColor("red")
        }

        binding.writefirstColorTopSelectorPink.setOnClickListener{
            clickListener?.changeButtonColor("pink")
        }

        binding.writefirstColorTopSelectorPink.setOnClickListener{
            clickListener?.changeButtonColor("yellow")
        }

        binding.writefirstColorTopSelectorLightyellow.setOnClickListener{
            clickListener?.changeButtonColor("lightyellow")
        }

        binding.writefirstColorTopSelectorGreen.setOnClickListener{
            clickListener?.changeButtonColor("green")
        }

        binding.writefirstColorTopSelectorLightgreen.setOnClickListener{
            clickListener?.changeButtonColor("lightgreen")
        }

        binding.writefirstColorTopSelectorOrange.setOnClickListener{
            clickListener?.changeButtonColor("orange")
        }

        binding.writefirstColorTopSelectorNavy.setOnClickListener{
            clickListener?.changeButtonColor("navy")
        }

        binding.writefirstColorTopSelectorBlue.setOnClickListener{
            clickListener?.changeButtonColor("blue")
        }

        binding.writefirstColorTopSelectorLightblue.setOnClickListener{
            clickListener?.changeButtonColor("lightblue")
        }

        binding.writefirstColorTopSelectorPurple.setOnClickListener{
            clickListener?.changeButtonColor("purple")
        }

        binding.writefirstColorTopSelectorLightpurple.setOnClickListener{
            clickListener?.changeButtonColor("lightpurple")
        }

        binding.writefirstColorTopSelectorWhite.setOnClickListener{
            clickListener?.changeButtonColor("white")
        }

        binding.writefirstColorTopSelectorGrey.setOnClickListener{
            clickListener?.changeButtonColor("grey")
        }

        binding.writefirstColorTopSelectorBlack.setOnClickListener{
            clickListener?.changeButtonColor("black")
        }

        binding.writefirstColorTopSelectorLightpeach.setOnClickListener{
            clickListener?.changeButtonColor("lightpeach")
        }

        binding.writefirstColorTopSelectorPinkishgrey.setOnClickListener{
            clickListener?.changeButtonColor("pinkishgrey")
        }

        binding.writefirstColorTopSelectorBrown.setOnClickListener{
            clickListener?.changeButtonColor("brown")
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
    interface ColorClickListner {
        fun changeButtonColor(color : String)
    }

    fun setColorClickListener(colorClickListener:WritefirstActivity.ColorClickListner) {
        this.clickListener = colorClickListener
    }

}