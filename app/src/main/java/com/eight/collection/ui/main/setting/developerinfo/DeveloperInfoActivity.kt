package com.eight.collection.ui.main.setting.developerinfo

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.eight.collection.R
import com.eight.collection.data.entities.User
import com.eight.collection.data.remote.auth.Auth
import com.eight.collection.data.remote.auth.AuthService
import com.eight.collection.databinding.ActivityDeveloperInfoBinding
import com.eight.collection.databinding.ActivityMailBinding
import com.eight.collection.databinding.ActivityNicknameEditBinding
import com.eight.collection.ui.BaseActivity
import com.eight.collection.utils.saveChangeNickName

class DeveloperInfoActivity: BaseActivity<ActivityDeveloperInfoBinding>(ActivityDeveloperInfoBinding::inflate), View.OnClickListener{

    override fun initAfterBinding() {
        binding.developerInfoBtnIv.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        if(v == null) return

        when(v) {
            binding.developerInfoBtnIv -> finishActivity()

        }
    }
}