package com.eight.collection.ui.main.setting.infoedit.account

import android.view.View
import android.widget.Toast
import com.eight.collection.databinding.ActivityAccountDeleteBinding
import com.eight.collection.databinding.ActivityAccountDeleteSecondBinding
import com.eight.collection.databinding.ActivityNicknameEditBinding
import com.eight.collection.ui.BaseActivity

class AccountDeleteSecondActivity: BaseActivity<ActivityAccountDeleteSecondBinding>(ActivityAccountDeleteSecondBinding::inflate), View.OnClickListener{




    override fun initAfterBinding() {
        binding.accountSecondBtnIb.setOnClickListener(this)
        binding.accountSecondBackBtnIv.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        if(v == null) return

        when(v) {
            binding.accountSecondBtnIb -> Toast(this).showCustomToast ("Hello! This is a custom Toast!", this)
            binding.accountSecondBackBtnIv -> finishActivity()
        }
    }
}