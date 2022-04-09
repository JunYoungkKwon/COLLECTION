package com.eight.collection.ui.finish

import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.viewpager2.widget.ViewPager2
import com.eight.collection.R
import com.eight.collection.data.entities.Diary
import com.eight.collection.data.entities.Photo
import com.eight.collection.data.remote.setting.SettingService
import com.eight.collection.databinding.ActivityFinishPhotoBinding
import com.eight.collection.databinding.FragmentWeekBinding
import com.eight.collection.ui.BaseActivity
import com.eight.collection.ui.BaseFragment
import com.eight.collection.ui.main.week.DiaryRVAdapter
import java.util.*
import kotlin.collections.ArrayList


class PhotoActivity() :BaseActivity<ActivityFinishPhotoBinding>(ActivityFinishPhotoBinding::inflate){

    private  lateinit var photoVPA: PhotoVPA
    private var nowPos = 0
    private var photo: ArrayList<String>? = null

    override fun onResume() {
        super.onResume()

    }

    override fun initAfterBinding() {
        nowPos = binding.finishPhotoVp.currentItem


        binding.finishPhotoVp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                nowPos = binding.finishPhotoVp.currentItem
            }
        })


        photo = intent.getStringArrayListExtra("photo")

        if (photo != null) {
            photoVPA = PhotoVPA(this)
            binding.finishPhotoVp.adapter = photoVPA
            photoVPA.addPhoto(photo as java.util.ArrayList<String>)
        }
        photoVPA.setMyitemClickListener(object : PhotoVPA.MyitemClickListener{
            override fun onBack() {
                finishActivity()
            }

        })

        binding.finishNextBtnIv.setOnClickListener {
            movePhoto(+1)
        }
        binding.finishPreviousBtnIv.setOnClickListener {
            movePhoto(-1)
        }

    }

    private fun movePhoto(direct: Int){
        if (nowPos + direct < 0){
            Toast.makeText(this,"첫번째 사진", Toast.LENGTH_SHORT).show()
            return
        }
        if (nowPos + direct >= photo!!.size){
            Toast.makeText(this,"마지막 사진", Toast.LENGTH_SHORT).show()
            return
        }
        nowPos += direct
        binding.finishPhotoVp.setCurrentItem(nowPos,true)

    }
}

