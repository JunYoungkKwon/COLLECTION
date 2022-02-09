package com.eight.collection.ui.main.setting.infoedit.phonenumber

import android.view.View
import android.widget.Toast
import com.eight.collection.databinding.ActivityNicknameEditBinding
import com.eight.collection.databinding.ActivityPhoneNumberEditBinding
import com.eight.collection.ui.BaseActivity

class PhoneNumberEditActivity: BaseActivity<ActivityPhoneNumberEditBinding>(ActivityPhoneNumberEditBinding::inflate), View.OnClickListener{




    override fun initAfterBinding() {
        binding.phoneNumberEditIb.setOnClickListener(this)
        binding.phoneNumberBackBtnIv.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        if(v == null) return

        when(v) {
            binding.phoneNumberEditIb -> Toast(this).showCustomToast ("Hello! This is a custom Toast!", this)
            binding.phoneNumberBackBtnIv -> finishActivity()


        }
    }
}