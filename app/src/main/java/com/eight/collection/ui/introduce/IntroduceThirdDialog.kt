package com.eight.collection.ui.introduce

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.eight.collection.R
import com.eight.collection.data.entities.User
import com.eight.collection.data.entities.Write.Block
import com.eight.collection.data.remote.addblock.AddBlockService
import com.eight.collection.data.remote.auth.AuthService
import com.eight.collection.ui.writing.AddBlockView
import com.eight.collection.ui.writing.CustomDialogInterface
import com.eight.collection.ui.writing.first.top.WritefirstTopCustomDialog
import com.eight.collection.utils.saveIntroduceIs

class IntroduceThirdDialog(context: Context) : Dialog(context){

    private lateinit var closeImage : ImageView
    private lateinit var nextImage : ImageView
    private lateinit var previousImage : ImageView
    lateinit var previousDialog: IntroduceSecondDialog
    lateinit var nextDialog: IntroduceFourthDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_introduce_third)

        closeImage = findViewById(R.id.introduce_close_ic)
        nextImage = findViewById(R.id.introduce_next_ic)
        previousImage = findViewById(R.id.introduce_previous_ic)

        nextImage.setOnClickListener{
            nextDialog = IntroduceFourthDialog(context)
            nextDialog.show()
            dismiss()
        }

        previousImage.setOnClickListener{
            previousDialog = IntroduceSecondDialog(context)
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