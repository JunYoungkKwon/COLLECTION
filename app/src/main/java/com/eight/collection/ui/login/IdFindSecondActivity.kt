package com.eight.collection.ui.login

import android.view.View
import com.eight.collection.databinding.ActivityIdFindFirstBinding
import com.eight.collection.databinding.ActivityIdFindSecondBinding
import com.eight.collection.databinding.ActivityLoginFirstBinding
import com.eight.collection.ui.BaseActivity
import com.eight.collection.ui.signup.SignupFirstActivity
import com.eight.collection.utils.getUserId

class IdFindSecondActivity: BaseActivity<ActivityIdFindSecondBinding>(ActivityIdFindSecondBinding::inflate), View.OnClickListener {


    override fun initAfterBinding() {
        binding.idFindSecondBackBtnIv.setOnClickListener(this)
        binding.idFindSecondLoginIb.setOnClickListener(this)
        binding.idFindSecondPwFindTv.setOnClickListener(this)
        binding.idFindFirstNameTv.text = getUserId()

    }

    override fun onClick(v: View?) {
        if(v == null) return

        when(v) {
            binding.idFindSecondBackBtnIv -> {
                finishActivity()
            }
            binding.idFindSecondLoginIb -> {
                startActivityWithClear(LoginSecondActivity::class.java)
                slideRight()
            }

            binding.idFindSecondPwFindTv -> {
                startNextActivity(PwFindFirstActivity::class.java)
                slideRight()
            }
        }
    }
}