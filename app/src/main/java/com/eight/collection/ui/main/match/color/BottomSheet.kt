package com.eight.collection.ui.main.match.color

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.widget.AppCompatImageButton
import com.eight.collection.R
import com.eight.collection.databinding.ActivityMatchColorBinding
import com.eight.collection.databinding.FragmentWritefirstTopBinding
import com.eight.collection.ui.main.match.color.top.ColorSearchTopFragment
import com.eight.collection.ui.writing.RefreshDialogInterface
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheet(context: Context, anInterface: BottomSheetInterface)  : BottomSheetDialog(context) {

    private var bottomSheetInterface : BottomSheetInterface = anInterface

    private lateinit var redButton : ImageButton
    private lateinit var pinkButton : ImageButton
    private lateinit var yellowButton : ImageButton
    private lateinit var lightyellowButton : ImageButton
    private lateinit var greenButton : ImageButton
    private lateinit var lightgreenButton : ImageButton
    private lateinit var orangeButton : ImageButton
    private lateinit var navyButton : ImageButton
    private lateinit var blueButton : ImageButton
    private lateinit var lightblueButton : ImageButton
    private lateinit var purpleButton : ImageButton
    private lateinit var lightpurpleButton : ImageButton
    private lateinit var whiteButton : ImageButton
    private lateinit var greyButton : ImageButton
    private lateinit var blackButton : ImageButton
    private lateinit var lightpeachButton : ImageButton
    private lateinit var pinkishgreyButton : ImageButton
    private lateinit var brownButton : ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_bottom_sheet_match_color)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        redButton = findViewById(R.id.match_color_search_bottom_sheet_part_selector_red)!!
        pinkButton = findViewById(R.id.match_color_search_bottom_sheet_part_selector_pink)!!
        yellowButton = findViewById(R.id.match_color_search_bottom_sheet_part_selector_yellow)!!
        lightyellowButton = findViewById(R.id.match_color_search_bottom_sheet_part_selector_lightyellow)!!
        greenButton = findViewById(R.id.match_color_search_bottom_sheet_part_selector_green)!!
        lightgreenButton = findViewById(R.id.match_color_search_bottom_sheet_part_selector_lightgreen)!!
        orangeButton = findViewById(R.id.match_color_search_bottom_sheet_part_selector_orange)!!
        navyButton = findViewById(R.id.match_color_search_bottom_sheet_part_selector_navy)!!
        blueButton = findViewById(R.id.match_color_search_bottom_sheet_part_selector_blue)!!
        lightblueButton = findViewById(R.id.match_color_search_bottom_sheet_part_selector_lightblue)!!
        purpleButton = findViewById(R.id.match_color_search_bottom_sheet_part_selector_purple)!!
        lightpurpleButton = findViewById(R.id.match_color_search_bottom_sheet_part_selector_lightpurple)!!
        whiteButton = findViewById(R.id.match_color_search_bottom_sheet_part_selector_white)!!
        greyButton = findViewById(R.id.match_color_search_bottom_sheet_part_selector_grey)!!
        blackButton = findViewById(R.id.match_color_search_bottom_sheet_part_selector_black)!!
        lightpeachButton = findViewById(R.id.match_color_search_bottom_sheet_part_selector_lightpeach)!!
        pinkishgreyButton = findViewById(R.id.match_color_search_bottom_sheet_part_selector_pinkishgrey)!!
        brownButton = findViewById(R.id.match_color_search_bottom_sheet_part_selector_brown)!!



        redButton.setOnClickListener {
            bottomSheetInterface.onPostColor("#d60f0f")
            dismiss()
        }

        pinkButton.setOnClickListener {
            bottomSheetInterface.onPostColor("#f59a9a")
            dismiss()
        }

        yellowButton.setOnClickListener {
            bottomSheetInterface.onPostColor("#ffb203")
            dismiss()
        }

        lightyellowButton.setOnClickListener {
            bottomSheetInterface.onPostColor("#fde6b1")
            dismiss()
        }

        greenButton.setOnClickListener {
            bottomSheetInterface.onPostColor("#71a238")
            dismiss()
        }

        lightgreenButton.setOnClickListener {
            bottomSheetInterface.onPostColor("#b7de89")
            dismiss()
        }

        orangeButton.setOnClickListener {
            bottomSheetInterface.onPostColor("#ea7831")
            dismiss()
        }

        navyButton.setOnClickListener {
            bottomSheetInterface.onPostColor("#273e88")
            dismiss()
        }

        blueButton.setOnClickListener {
            bottomSheetInterface.onPostColor("#4168e8")
            dismiss()
        }

        lightblueButton.setOnClickListener {
            bottomSheetInterface.onPostColor("#a5b9fa")
            dismiss()
        }

        purpleButton.setOnClickListener {
            bottomSheetInterface.onPostColor("#894ac7")
            dismiss()
        }

        lightpurpleButton.setOnClickListener {
            bottomSheetInterface.onPostColor("#dcacff")
            dismiss()
        }

        whiteButton.setOnClickListener {
            bottomSheetInterface.onPostColor("#ffffff")
            dismiss()
        }

        greyButton.setOnClickListener {
            bottomSheetInterface.onPostColor("#888888")
            dismiss()
        }

        blackButton.setOnClickListener {
            bottomSheetInterface.onPostColor("#191919")
            dismiss()
        }

        lightpeachButton.setOnClickListener {
            bottomSheetInterface.onPostColor("#e8dcd5")
            dismiss()
        }

        pinkishgreyButton.setOnClickListener {
            bottomSheetInterface.onPostColor("#c3b5ac")
            dismiss()
        }

        brownButton.setOnClickListener {
            bottomSheetInterface.onPostColor("#74461f")
            dismiss()
        }

    }


    /*private var bottomSheetDialogListener : BottomSheet.BottomSheetDialogListener? = null

    interface BottomSheetDialogListener {
        fun postColor(color : String)
    }

    fun setBottomSheetDialogListener(bottomsheetDialogListener: ColorActivity) {
        this.bottomSheetDialogListener = bottomsheetDialogListener
    }
*/
    /*override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.dialog_bottom_sheet_match_color , container, false)
    }*/

    /*override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setBottomSheetDialogListener(ColorActivity())

        view?.findViewById<AppCompatImageButton>(R.id.match_color_search_bottom_sheet_part_selector_red)?.setOnClickListener {
            bottomSheetDialogListener?.postColor("#d60f0f")
            dismiss()
        }
        view?.findViewById<AppCompatImageButton>(R.id.match_color_search_bottom_sheet_part_selector_pink)?.setOnClickListener {
            bottomSheetDialogListener?.postColor("#f59a9a")
            dismiss()
        }
        view?.findViewById<AppCompatImageButton>(R.id.match_color_search_bottom_sheet_part_selector_yellow)?.setOnClickListener {
            bottomSheetDialogListener?.postColor("#ffb203")
            dismiss()
        }
        view?.findViewById<AppCompatImageButton>(R.id.match_color_search_bottom_sheet_part_selector_lightyellow)?.setOnClickListener {
            bottomSheetDialogListener?.postColor("#fde6b1")
            dismiss()
        }
        view?.findViewById<AppCompatImageButton>(R.id.match_color_search_bottom_sheet_part_selector_green)?.setOnClickListener {
            bottomSheetDialogListener?.postColor("#71a238")
            dismiss()
        }
        view?.findViewById<AppCompatImageButton>(R.id.match_color_search_bottom_sheet_part_selector_lightgreen)?.setOnClickListener {
            bottomSheetDialogListener?.postColor("#b7de89")
            dismiss()
        }
        view?.findViewById<AppCompatImageButton>(R.id.match_color_search_bottom_sheet_part_selector_orange)?.setOnClickListener {
            bottomSheetDialogListener?.postColor("#ea7831")
            dismiss()
        }
        view?.findViewById<AppCompatImageButton>(R.id.match_color_search_bottom_sheet_part_selector_navy)?.setOnClickListener {
            bottomSheetDialogListener?.postColor("#273e88")
            dismiss()
        }
        view?.findViewById<AppCompatImageButton>(R.id.match_color_search_bottom_sheet_part_selector_blue)?.setOnClickListener {
            bottomSheetDialogListener?.postColor("#4168e8")
            dismiss()
        }
        view?.findViewById<AppCompatImageButton>(R.id.match_color_search_bottom_sheet_part_selector_lightblue)?.setOnClickListener {
            bottomSheetDialogListener?.postColor("#a5b9fa")
            dismiss()
        }
        view?.findViewById<AppCompatImageButton>(R.id.match_color_search_bottom_sheet_part_selector_purple)?.setOnClickListener {
            bottomSheetDialogListener?.postColor("#894ac7")
            dismiss()
        }
        view?.findViewById<AppCompatImageButton>(R.id.match_color_search_bottom_sheet_part_selector_lightpurple)?.setOnClickListener {
            bottomSheetDialogListener?.postColor("#dcacff")
            dismiss()
        }
        view?.findViewById<AppCompatImageButton>(R.id.match_color_search_bottom_sheet_part_selector_white)?.setOnClickListener {
            bottomSheetDialogListener?.postColor("#ffffff")
            dismiss()
        }
        view?.findViewById<AppCompatImageButton>(R.id.match_color_search_bottom_sheet_part_selector_grey)?.setOnClickListener {
            bottomSheetDialogListener?.postColor("#888888")
            dismiss()
        }
        view?.findViewById<AppCompatImageButton>(R.id.match_color_search_bottom_sheet_part_selector_black)?.setOnClickListener {
            bottomSheetDialogListener?.postColor("#191919")
            dismiss()
        }
        view?.findViewById<AppCompatImageButton>(R.id.match_color_search_bottom_sheet_part_selector_lightpeach)?.setOnClickListener {
            bottomSheetDialogListener?.postColor("#e8dcd5")
            dismiss()
        }
        view?.findViewById<AppCompatImageButton>(R.id.match_color_search_bottom_sheet_part_selector_pinkishgrey)?.setOnClickListener {
            bottomSheetDialogListener?.postColor("#c3b5ac")
            dismiss()
        }
        view?.findViewById<AppCompatImageButton>(R.id.match_color_search_bottom_sheet_part_selector_brown)?.setOnClickListener {
            bottomSheetDialogListener?.postColor("#74461f")
            dismiss()
        }
    }*/
}