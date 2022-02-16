package com.eight.collection.ui.writing.first

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    private var colortextpost: ColorTextPost? = null
    val list = ArrayList<Uri>()
    val photoRVAdapter = PhotoRVAdapter(list, this)




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWritefirstBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var getImage_btn = findViewById<ImageView>(R.id.writefirst_add_photo_iv)
        var recyclerview = findViewById<RecyclerView>(R.id.writefirst_photo_recyclerview)

        getImage_btn.setOnClickListener{
            var intent = Intent(Intent.ACTION_PICK)
            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT
            binding.writefirstAddPhotoIv.visibility = View.GONE

            startActivityForResult(intent, 200)
        }

        recyclerview.layoutManager =
            LinearLayoutManager(baseContext, LinearLayoutManager.HORIZONTAL, false)
        recyclerview.adapter = photoRVAdapter


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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == 200) {
            list.clear()

            if (data?.clipData != null) {
                val count = data.clipData!!.itemCount
                if (count > 5) {
                    Toast.makeText(applicationContext, "사진은 5장까지 선택 가능합니다.", Toast.LENGTH_LONG)
                    return
                }

                for (i in 0 until count){
                    val imageUri = data.clipData!!.getItemAt(i).uri
                    list.add(imageUri)
                }

            } else {
                data?.data?.let { uri ->
                    val imageUri : Uri? = data?.data
                    if (imageUri != null){
                        list.add(imageUri)
                    }
                }
            }
            photoRVAdapter.notifyDataSetChanged()
        }
    }

}