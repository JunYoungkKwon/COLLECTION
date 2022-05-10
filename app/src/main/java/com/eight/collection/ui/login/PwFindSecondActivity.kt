package com.eight.collection.ui.login

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import com.eight.collection.R
import com.eight.collection.data.entities.User
import com.eight.collection.data.remote.auth.AuthService
import com.eight.collection.databinding.ActivityLoginFirstBinding
import com.eight.collection.databinding.ActivityPwFindFirstBinding
import com.eight.collection.databinding.ActivityPwFindSecondBinding
import com.eight.collection.ui.BaseActivity
import com.eight.collection.ui.signup.SignupFirstActivity

class PwFindSecondActivity: BaseActivity<ActivityPwFindSecondBinding>(ActivityPwFindSecondBinding::inflate), View.OnClickListener , PwFindView {


    override fun initAfterBinding() {
        binding.pwFindSecondBackBtnIv.setOnClickListener(this)
        binding.pwFindSecondEditIb.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        if(v == null) return

        when(v) {
            binding.pwFindSecondBackBtnIv -> finishActivity()
            binding.pwFindSecondEditIb -> idFind()
        }
    }

    private fun idFind() {
        val newPw = binding.pwFindSecondNewPwEt.text.toString()
        val newPwCheck = binding.pwFindSecondNewPwCheckEt.text.toString()
        val user = User("", "", "", "", "","",newPw,newPwCheck)

        if (newPw.isNotEmpty() && newPwCheck.isNotEmpty()) {
            if (!newPw.equals(newPwCheck)) {
                binding.pwFindSecondNewPwCheckHighlightView.setBackgroundColor(Color.parseColor("#c77a4a"))
                binding.pwFindSecondNewPwCheckFailTv.visibility = View.VISIBLE
                binding.pwFindSecondNewPwCheckFailTv.text= "새 비밀번호와 일치하지 않습니다."

                binding.pwFindSecondNewPwEtHighlightView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.pwFindSecondNewPwFailTv.visibility = View.GONE
                return
            }
        }

        AuthService.resetPw(this, user)
    }

    override fun onPwFindLoading() {}

    override fun onPwFindSuccess() {
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_pw_change_custom, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setCancelable(false)
        val  mAlertDialog = mBuilder.show()
        mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val noButton = mDialogView.findViewById<ImageButton>(R.id.dialog_cancle_ib)
        noButton.setOnClickListener {
            startActivityWithClear(LoginSecondActivity::class.java)
            mAlertDialog.dismiss()
        }
    }

    override fun onPwFindFailure(code: Int, message: String) {
        when(code) {

            3015, 3116 -> {
                binding.pwFindSecondNewPwEtHighlightView.setBackgroundColor(Color.parseColor("#c77a4a"))
                binding.pwFindSecondNewPwFailTv.visibility = View.VISIBLE
                binding.pwFindSecondNewPwFailTv.text= message

                binding.pwFindSecondNewPwCheckHighlightView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.pwFindSecondNewPwCheckFailTv.visibility = View.GONE
            }
            3016, 3117 -> {
                binding.pwFindSecondNewPwCheckHighlightView.setBackgroundColor(Color.parseColor("#c77a4a"))
                binding.pwFindSecondNewPwCheckFailTv.visibility = View.VISIBLE
                binding.pwFindSecondNewPwCheckFailTv.text= message

                binding.pwFindSecondNewPwEtHighlightView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.pwFindSecondNewPwFailTv.visibility = View.GONE
            }

            else -> {
                Log.d("PwFindSecond/Error", "error")
            }
        }
    }
}