package com.eight.collection.ui.main.setting.infoedit.password

import android.view.View
import android.widget.Toast
import com.eight.collection.databinding.ActivityNicknameEditBinding
import com.eight.collection.databinding.ActivityPhoneNumberEditBinding
import com.eight.collection.databinding.ActivityPwEditBinding
import com.eight.collection.ui.BaseActivity

class PwEditActivity: BaseActivity<ActivityPwEditBinding>(ActivityPwEditBinding::inflate), View.OnClickListener{

    override fun initAfterBinding() {
        binding.pwEditIb.setOnClickListener(this)
        binding.pwBackBtnIv.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        if(v == null) return

        when(v) {
            binding.pwEditIb -> Toast(this).showCustomToast ("Hello! This is a custom Toast!", this)
            binding.pwBackBtnIv -> finishActivity()


        }
    }
}