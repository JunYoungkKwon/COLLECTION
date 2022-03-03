package com.eight.collection.ui.writing.first.top

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.eight.collection.R
import com.eight.collection.data.entities.User
import com.eight.collection.data.entities.Write.Block
import com.eight.collection.data.remote.addblock.AddBlockService
import com.eight.collection.data.remote.auth.AuthService
import com.eight.collection.ui.writing.AddBlockView
import com.eight.collection.ui.writing.CustomDialogInterface

class WritefirstTopCustomDialog(context: Context, anInterface: CustomDialogInterface) : Dialog(context), AddBlockView {

    private var customDialogInterface : CustomDialogInterface = anInterface
    private lateinit var addButton : Button
    private lateinit var cancelButton : Button
    private lateinit var addEditText : EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_toptag)

        addButton = findViewById(R.id.add_toptag_confirm_button)
        cancelButton = findViewById(R.id.add_toptag_cancel_button)
        addEditText = findViewById(R.id.add_toptag_et)

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
        addEditText = findViewById(R.id.add_toptag_et)
        val clothes : Int = 0
        val pww : Int = -1
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
                var layoutInflater = LayoutInflater.from(context).inflate(R.layout.toast_custom,null)
                var text : TextView = layoutInflater.findViewById(R.id.toast_text_tv)
                text.text = message
                var toast = Toast(context)
                toast.view = layoutInflater
                toast.setGravity(Gravity.BOTTOM, 0, 270)
                toast.show()
            }
            else -> {
                var layoutInflater = LayoutInflater.from(context).inflate(R.layout.toast_custom,null)
                var text : TextView = layoutInflater.findViewById(R.id.toast_text_tv)
                text.text = "SERVER ERROR"
                var toast = Toast(context)
                toast.view = layoutInflater
                toast.setGravity(Gravity.BOTTOM, 0, 270)
                toast.show()
            }
        }
    }
}