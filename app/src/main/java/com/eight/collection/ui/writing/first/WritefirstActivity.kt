package com.eight.collection.ui.writing.first

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.R
import com.eight.collection.databinding.ActivityWritefirstBinding
import com.eight.collection.ui.writing.second.WritesecondActivity
import com.eight.collection.utils.*
import com.google.android.material.tabs.TabLayoutMediator
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.collections.ArrayList

class WritefirstActivity() : AppCompatActivity(){

    lateinit var binding: ActivityWritefirstBinding
    val information = arrayListOf("TOP", "BOTTOM", "SHOES", "ETC")
    val photoList = ArrayList<Uri>()
    val photoRVAdapter = PhotoRVAdapter(photoList, this)

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
        val writefirstAdapter = WritefirstVPA(this)
        binding.writefirstColorVp.adapter = writefirstAdapter
        TabLayoutMediator(binding.writefirstColorTb, binding.writefirstColorVp) { tab, position ->
            tab.text = information[position]
        }.attach()


        //Color블록 클릭시 데이터 전달
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
            setColor("lightgreen")
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
}