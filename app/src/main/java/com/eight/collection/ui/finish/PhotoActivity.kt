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

    override fun initAfterBinding() {

        val photo = intent.getStringArrayListExtra("photo")

        if (photo != null) {
            val photoAdapter = PhotoVPA(this)
            binding.finishPhotoVp.adapter = photoAdapter
            photoAdapter.addPhoto(photo)
        }
    }
}