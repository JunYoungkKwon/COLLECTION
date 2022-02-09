package com.eight.collection.ui.main.setting.infoedit.nickname

import android.view.View
import android.widget.Toast
import com.eight.collection.databinding.ActivityNicknameEditBinding
import com.eight.collection.ui.BaseActivity

class NickNameEditActivity: BaseActivity<ActivityNicknameEditBinding>(ActivityNicknameEditBinding::inflate), View.OnClickListener{

    override fun initAfterBinding() {
        binding.nicknameEditIb.setOnClickListener(this)
        binding.nicknameBackBtnIv.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        if(v == null) return

        when(v) {
            binding.nicknameEditIb -> Toast(this).showCustomToast ("Hello! This is a custom Toast!", this)
            binding.nicknameBackBtnIv -> finishActivity()


        }
    }
}