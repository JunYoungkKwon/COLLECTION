package com.eight.collection.ui.main.setting.infoedit.mail

import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.eight.collection.R
import com.eight.collection.data.entities.User
import com.eight.collection.data.remote.auth.Auth
import com.eight.collection.data.remote.auth.AuthService
import com.eight.collection.databinding.ActivityMailBinding
import com.eight.collection.databinding.ActivityNicknameEditBinding
import com.eight.collection.ui.BaseActivity
import com.eight.collection.utils.saveChangeNickName

class MailActivity: BaseActivity<ActivityMailBinding>(ActivityMailBinding::inflate), View.OnClickListener{

    override fun initAfterBinding() {
        binding.mailLinkTv.setOnClickListener(this)
        binding.mailBackBtnIv.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        if(v == null) return

        when(v) {
            binding.mailLinkTv -> {}
            binding.mailBackBtnIv -> finishActivity()

        }
    }
}