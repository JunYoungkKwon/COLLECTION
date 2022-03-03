package com.eight.collection.ui.main.setting.infoedit

import android.view.View
import com.eight.collection.databinding.*
import com.eight.collection.ui.BaseActivity
import com.eight.collection.ui.login.LoginSecondActivity
import com.eight.collection.ui.main.setting.infoedit.account.AccountDeleteActivity
import com.eight.collection.ui.main.setting.infoedit.nickname.NickNameEditActivity
import com.eight.collection.ui.main.setting.infoedit.password.PwEditActivity
import com.eight.collection.ui.main.setting.infoedit.phonenumber.PhoneNumberEditActivity
import com.eight.collection.utils.getName
import com.eight.collection.utils.getNickName
import com.eight.collection.utils.getUserId

class InfoEditActivity: BaseActivity<ActivityInfoEditBinding>(ActivityInfoEditBinding::inflate), View.OnClickListener{

    override fun initAfterBinding() {
        binding.infoEditNicknameLy.setOnClickListener(this)
        binding.infoEditPwLy.setOnClickListener(this)
        binding.infoEditAccountLy.setOnClickListener(this)
        binding.infoEditPhoneNumberLy.setOnClickListener(this)
        binding.infoEditBackBtnIv.setOnClickListener(this)

        binding.infoEditNicknameTv.text = getName()
        binding.infoEditIdTv.text = getUserId()

    }

    override fun onClick(v: View?) {
        if(v == null) return

        when(v) {
            binding.infoEditNicknameLy ->  {startNextActivity(NickNameEditActivity::class.java)
                slideRight()}
            binding.infoEditPwLy ->  {startNextActivity(PwEditActivity::class.java)
                slideRight()}
            binding.infoEditAccountLy ->  {startNextActivity(AccountDeleteActivity::class.java)
                slideRight()}
            binding.infoEditPhoneNumberLy ->  {startNextActivity(PhoneNumberEditActivity::class.java)
                slideRight()}
            binding.infoEditBackBtnIv -> finishActivity()
        }
    }
}