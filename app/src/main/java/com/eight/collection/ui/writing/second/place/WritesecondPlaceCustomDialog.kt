package com.eight.collection.ui.writing.second.place

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.eight.collection.R
import com.eight.collection.data.entities.Write.Block
import com.eight.collection.data.remote.addblock.AddBlockService
import com.eight.collection.ui.signup.SignupThirdActivity
import com.eight.collection.ui.writing.AddBlockView
import com.eight.collection.ui.writing.CustomDialogInterface

class WritesecondPlaceCustomDialog(context: Context, anInterface: CustomDialogInterface) : Dialog(context),
    AddBlockView {

    private var customDialogInterface : CustomDialogInterface = anInterface
    private lateinit var addButton : Button
    private lateinit var cancelButton : Button
    private lateinit var addEditText : EditText



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_placetag)

        addButton = findViewById(R.id.add_placetag_confirm_button)
        cancelButton = findViewById(R.id.add_placetag_cancel_button)
        addEditText = findViewById(R.id.add_placetag_et)

        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        addButton.setOnClickListener {
            addBlock()
            dismiss()
        }

        cancelButton.setOnClickListener {
            customDialogInterface.onCancelButtonClicked()
            dismiss()
        }
    }

    private fun getBlock() : Block {
        addEditText = findViewById(R.id.add_placetag_et)
        val clothes : Int = -1
        val pww : Int = 0
        val content : String = addEditText.text.toString()
        return Block(clothes,pww,content)
    }

    private fun addBlock() {
        AddBlockService.addBlock(this, getBlock())
    }

    override fun onAddBlockLoading() {
    }

    override fun onAddBlockSuccess(content:String) {
        customDialogInterface.onAddButtonClicked(content)
    }

    override fun onAddBlockFailure(code: Int, message: String) {
        when(code) {
            3029,3049,4003,4004,4014 -> {
                var layoutInflater = LayoutInflater.from(context).inflate(R.layout.toast_signup,null)
                var text : TextView = layoutInflater.findViewById(R.id.toast_signup_text)
                text.text = message
                var toast = Toast(context)
                toast.view = layoutInflater
                toast.setGravity(Gravity.BOTTOM, 0, 270)
                toast.show()
            }
            else -> {
                var layoutInflater = LayoutInflater.from(context).inflate(R.layout.toast_signup,null)
                var text : TextView = layoutInflater.findViewById(R.id.toast_signup_text)
                text.text = "SERVER ERROR"
                var toast = Toast(context)
                toast.view = layoutInflater
                toast.setGravity(Gravity.BOTTOM, 0, 270)
                toast.show()
            }
        }
    }
}