package com.eight.collection.ui.introduce

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.*
import com.eight.collection.R
import com.eight.collection.ui.login.LoginFirstActivity
import com.eight.collection.ui.splash.SplashActivity
import com.eight.collection.utils.saveIntroduceIs

class IntroduceFirstDialog(context: Context) : Dialog(context){

    private lateinit var closeImage : ImageView
    private lateinit var nextImage : ImageView
    private lateinit var previousImage : ImageView
    lateinit var previousDialog: IntroduceFourthDialog
    lateinit var nextDialog: IntroduceSecondDialog
    lateinit var loginfirstactivity : LoginFirstActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_introduce_first)

        closeImage = findViewById(R.id.introduce_close_ic)
        nextImage = findViewById(R.id.introduce_next_ic)
        previousImage = findViewById(R.id.introduce_previous_ic)

        nextImage.setOnClickListener{
            nextDialog = IntroduceSecondDialog(context)
            nextDialog.show()
            dismiss()
        }

        previousImage.setOnClickListener{
            previousDialog = IntroduceFourthDialog(context)
            previousDialog.show()
            dismiss()
        }

        closeImage.setOnClickListener{
            dismiss()
            saveIntroduceIs(true)
        }

        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}