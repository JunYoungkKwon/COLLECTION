package com.eight.collection.ui.finish

import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import com.eight.collection.R
import com.eight.collection.data.entities.Photo
import com.eight.collection.data.remote.setting.SettingService
import com.eight.collection.databinding.ActivityFinishPhotoBinding
import com.eight.collection.databinding.FragmentWeekBinding
import com.eight.collection.ui.BaseActivity
import com.eight.collection.ui.BaseFragment
import java.util.*
import kotlin.collections.ArrayList


class PhotoActivity() :BaseActivity<ActivityFinishPhotoBinding>(ActivityFinishPhotoBinding::inflate){
    private var image: java.util.ArrayList<String>? = null

    override fun initAfterBinding() {
        var photo = arrayListOf<String>(
            "https://collection8bucket.s3.ap-northeast-2.amazonaws.com/collectionImageDB/1647614445424_IMG_4065.JPG",
            "https://collection8bucket.s3.ap-northeast-2.amazonaws.com/collectionImageDB/1647614445424_IMG_4065.JPG"
        )

//        binding.finishPhotoVp.adapter = PhotoVPA(photo)

//        val photo = intent.getStringArrayListExtra("photo")
        Log.d("phototest1",photo.toString())
        if (photo != null) {
            val photoAdapter = PhotoVPA(this)
            binding.finishPhotoVp.adapter = photoAdapter
            photoAdapter.addPhoto(photo)
        }
    }
}