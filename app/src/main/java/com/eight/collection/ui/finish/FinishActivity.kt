package com.eight.collection.ui.finish

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.animation.AnimationUtils
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.eight.collection.R
import com.eight.collection.data.entities.Cloth
import com.eight.collection.data.entities.Photo
import com.eight.collection.data.remote.finish.Finish
import com.eight.collection.data.remote.finish.FinishService
import com.eight.collection.data.remote.setting.SettingService
import com.eight.collection.databinding.ActivityFinishBinding
import com.eight.collection.ui.BaseActivity
import com.eight.collection.ui.main.week.DeleteView
import com.eight.collection.ui.main.week.DiaryRVAdapter
import com.eight.collection.ui.writing.first.WritefirstActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN
import com.skydoves.powermenu.OnMenuItemClickListener
import com.skydoves.powermenu.PowerMenu
import com.skydoves.powermenu.PowerMenuItem
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


class FinishActivity :BaseActivity<ActivityFinishBinding>(ActivityFinishBinding::inflate), FinishView, DeleteView {

    private  lateinit var placeRVAdapter: PlaceRVAdapter
    private  lateinit var weatherRVAdapter: WeatherRVAdapter
    private  lateinit var whoRVAdapter: WhoRVAdapter
    private  lateinit var topRVAdapter: TopRVAdapter
    private  lateinit var bottomRVAdapter: BottomRVAdapter
    private  lateinit var shoesRVAdapter: ShoesRVAdapter
    private  lateinit var etcRVAdapter: EtcRVAdapter
    private  lateinit var photoRVAdapter: PhotoRVAdapter

    override fun initAfterBinding() {
        initRV()
        getFinish()
        clickSetting()
        scrollFinsh()
    }

    private fun scrollFinsh() {
        val bottomSheetBehavior = BottomSheetBehavior.from(binding.finishCl)

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == STATE_HIDDEN) {
                    finish()
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

        })
    }

    private fun initRV(){
        weatherRVAdapter = WeatherRVAdapter()
        binding.finishWeatherRecyclerView.adapter = weatherRVAdapter
        placeRVAdapter = PlaceRVAdapter()
        binding.finishPlaceRecyclerView.adapter = placeRVAdapter
        whoRVAdapter = WhoRVAdapter()
        binding.finishWhoRecyclerView.adapter = whoRVAdapter
        photoRVAdapter = PhotoRVAdapter(this)
        binding.finishPhotoRecyclerview.adapter = photoRVAdapter
        topRVAdapter = TopRVAdapter()
        binding.finishTopRecyclerView.adapter = topRVAdapter
        bottomRVAdapter = BottomRVAdapter()
        binding.finishBottomRecyclerView.adapter = bottomRVAdapter
        shoesRVAdapter = ShoesRVAdapter()
        binding.finishShoesRecyclerView.adapter = shoesRVAdapter
        etcRVAdapter = EtcRVAdapter()
        binding.finishEtcRecyclerView.adapter = etcRVAdapter
    }

    private fun clickSetting(){
        binding.finishEditIv.setOnClickListener {

            view ->
            val powerMenu = PowerMenu.Builder(this)
                .addItem(PowerMenuItem("수정하기", false))
                .addItem(PowerMenuItem("삭제하기", false))
                .setMenuRadius(15f)
                .setDivider(ColorDrawable(ContextCompat.getColor(this, R.color.pinkish_grey)))
                .setDividerHeight(1)
                .setShowBackground(false)
                .setMenuShadow(20f)
                .setTextColor(ContextCompat.getColor(this, R.color.dark_taupe))
                .setTextGravity(Gravity.CENTER)
                .setTextTypeface(Typeface.create("suit_regular", Typeface.NORMAL))
                .setMenuColor(Color.WHITE)
                .build()

            val onMenuItemClickListener = OnMenuItemClickListener<PowerMenuItem> { position1, item ->
                when(item.title){
                    "수정하기" -> {
                        val dateIntent = intent
                        val date = dateIntent.getStringExtra("date")
                        val intent2 = Intent(this, WritefirstActivity::class.java)
                        intent2.putExtra("date", date)
                        startActivity(intent2)
                        finish()
                    }
                    "삭제하기" -> {deleteOOTD()}
                }
                powerMenu.dismiss()
            }
            powerMenu.onMenuItemClickListener = onMenuItemClickListener
            powerMenu.showAsDropDown(view, -327, -30)
        }
    }

    private fun deleteOOTD() {
        val dateIntent = intent
        val date = dateIntent.getStringExtra("date")
        if (date != null) {
            SettingService.deleteOOTD(this, date)
            finish()
        }
    }


    private fun getFinish() {
        val dateIntent = intent
        val date = dateIntent.getStringExtra("date")
        if (date != null) {
            FinishService.getFinish(this, date)
        }
    }

    override fun onFinishLoading() {
        binding.loginLoadingInIv.visibility = View.VISIBLE
        binding.loginLoadingCircleIv.visibility = View.VISIBLE
        binding.loginLoadingBackgroundIv.visibility = View.VISIBLE
        val animation = AnimationUtils.loadAnimation(this, R.anim.rotate)
        binding.loginLoadingCircleIv.startAnimation(animation)
        binding.loginDimBackground.visibility = View.VISIBLE
    }

    override fun onFinishSuccess(finish: Finish) {
        Log.d("Photo/data", finish.image?.toString())
        binding.loginLoadingCircleIv.visibility = View.GONE
        binding.loginLoadingInIv.visibility = View.GONE
        binding.loginLoadingBackgroundIv.visibility = View.GONE
        binding.loginLoadingCircleIv.clearAnimation()
        binding.loginDimBackground.visibility = View.INVISIBLE

        when(finish.lookpoint){
            1 -> binding.finishRankPointIv.setImageResource(R.drawable.ic_diary_point_1)
            2 -> binding.finishRankPointIv.setImageResource(R.drawable.ic_diary_point_2)
            3 -> binding.finishRankPointIv.setImageResource(R.drawable.ic_diary_point_3)
            4 -> binding.finishRankPointIv.setImageResource(R.drawable.ic_diary_point_4)
            5 -> binding.finishRankPointIv.setImageResource(R.drawable.ic_diary_point_5)
            else -> binding.finishRankPointIv.setImageResource(R.drawable.ic_diary_point_5)
        }

        val getdate: Date = finish.date
        val localdate:LocalDate = getdate.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
        val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
        val convertDate: String = localdate.format(formatter)

        if(finish.image.isNullOrEmpty()){
            finish.image.add(Photo("nill", 1))
        }

        if(finish.Top.isNullOrEmpty()){
            finish.Top.add(Cloth("해당 항목 없음", ""))
        }

        if(finish.Bottom.isNullOrEmpty()){
            finish.Bottom.add(Cloth("해당 항목 없음", ""))
        }

        if(finish.Shoes.isNullOrEmpty()){
            finish.Shoes.add(Cloth("해당 항목 없음", ""))
        }

        if(finish.Etc.isNullOrEmpty()){
            finish.Etc.add(Cloth("해당 항목 없음", ""))
        }

        if(finish.weather.isNullOrEmpty()){
            // weather 비고 place 있는상태
            if(finish.place.isNotEmpty()){
                binding.finishWeatherRecyclerView.visibility = View.GONE
                binding.finishWeatherTv.visibility = View.GONE

                val constraints = ConstraintSet()
                constraints.clone(binding.finishCl)
                constraints.connect(
                    binding.finishPlaceTv.id,
                    ConstraintSet.TOP,
                    binding.finishHorizonFourthView.id,
                    ConstraintSet.BOTTOM,
                    convertDpToPixel(16f, this)
                )
                constraints.applyTo(binding.finishCl)
                // weather 비고 place 있고 who 없는상태
                if(finish.who.isNullOrEmpty()){
                    binding.finishWhoRecyclerView.visibility = View.GONE
                    binding.finishWhoTv.visibility = View.GONE

                    val constraints = ConstraintSet()
                    constraints.clone(binding.finishCl)
                    constraints.connect(
                        binding.finishTopTv.id,
                        ConstraintSet.TOP,
                        binding.finishWhoTv.id,
                        ConstraintSet.BOTTOM,
                    )
                    constraints.applyTo(binding.finishCl)

                }
            }
            else{
                if(finish.who.isNullOrEmpty()){
                    binding.finishWeatherRecyclerView.visibility = View.GONE
                    binding.finishWeatherTv.visibility = View.GONE
                    binding.finishPlaceRecyclerView.visibility = View.GONE
                    binding.finishPlaceTv.visibility = View.GONE
                    binding.finishWhoRecyclerView.visibility = View.GONE
                    binding.finishWhoTv.visibility = View.GONE

                    val constraints = ConstraintSet()
                    constraints.clone(binding.finishCl)
                    constraints.connect(
                        binding.finishTopTv.id,
                        ConstraintSet.TOP,
                        binding.finishHorizonFourthView.id,
                        ConstraintSet.BOTTOM,
                    )
                    constraints.applyTo(binding.finishCl)

                }else{
                    binding.finishWeatherRecyclerView.visibility = View.GONE
                    binding.finishWeatherTv.visibility = View.GONE
                    binding.finishPlaceRecyclerView.visibility = View.GONE
                    binding.finishPlaceTv.visibility = View.GONE

                    val constraints = ConstraintSet()
                    constraints.clone(binding.finishCl)
                    constraints.connect(
                        binding.finishWhoTv.id,
                        ConstraintSet.TOP,
                        binding.finishHorizonFourthView.id,
                        ConstraintSet.BOTTOM,
                        convertDpToPixel(16f, this)
                    )
                    constraints.applyTo(binding.finishCl)
                }

            }
        }else{
            if(finish.place.isNotEmpty()){

                if(finish.who.isNullOrEmpty()){
                    binding.finishWhoRecyclerView.visibility = View.GONE
                    binding.finishWhoTv.visibility = View.GONE

                    val constraints = ConstraintSet()
                    constraints.clone(binding.finishCl)
                    constraints.connect(
                        binding.finishTopTv.id,
                        ConstraintSet.TOP,
                        binding.finishPlaceTv.id,
                        ConstraintSet.BOTTOM,
                    )
                    constraints.applyTo(binding.finishCl)

                }

            }
            else {
                binding.finishPlaceRecyclerView.visibility = View.GONE
                binding.finishPlaceTv.visibility = View.GONE

                if(finish.who.isNullOrEmpty()){

                    binding.finishWhoRecyclerView.visibility = View.GONE
                    binding.finishWhoTv.visibility = View.GONE

                    val constraints = ConstraintSet()
                    constraints.clone(binding.finishCl)
                    constraints.connect(
                        binding.finishTopTv.id,
                        ConstraintSet.TOP,
                        binding.finishWeatherTv.id,
                        ConstraintSet.BOTTOM,
                    )
                    constraints.applyTo(binding.finishCl)

                }else{
                    val constraints = ConstraintSet()
                    constraints.clone(binding.finishCl)
                    constraints.connect(
                        binding.finishWhoTv.id,
                        ConstraintSet.TOP,
                        binding.finishWeatherTv.id,
                        ConstraintSet.BOTTOM,
                    )
                    constraints.applyTo(binding.finishCl)

                }
            }
        }



        binding.finishOotdTitileTv.text = finish.lookname
        binding.finishDateTv.text = convertDate
        binding.finishCommentTv.text = finish.comment
        weatherRVAdapter.addWheeather(finish.weather)
        whoRVAdapter.addWho(finish.who)
        placeRVAdapter.addPlace(finish.place)
        topRVAdapter.addTop(finish.Top)
        bottomRVAdapter.addBottom(finish.Bottom)
        shoesRVAdapter.addShoes(finish.Shoes)
        etcRVAdapter.addEtc(finish.Etc)
        photoRVAdapter.addPhoto(finish.image)



    }

    override fun onFinishFailure(code: Int, message: String) {
        binding.loginLoadingCircleIv.visibility = View.GONE
        binding.loginLoadingInIv.visibility = View.GONE
        binding.loginLoadingBackgroundIv.visibility = View.GONE
        binding.loginLoadingCircleIv.clearAnimation()
        binding.loginDimBackground.visibility = View.INVISIBLE

        when (code) {
            2000, 2001, 2002 -> {
                Log.d("Finish/Jwt/ERROR", "error")
            }
            3026, 3044, 3021-> {
                Log.d("Finish/Data/ERROR", "error")
            }

            else -> {
                Log.d("Finish/SEVER/ERROR", message)
            }
        }
    }

    override fun onDeleteLoading() {
        binding.loginLoadingInIv.visibility = View.VISIBLE
        binding.loginLoadingCircleIv.visibility = View.VISIBLE
        binding.loginLoadingBackgroundIv.visibility = View.VISIBLE
        val animation = AnimationUtils.loadAnimation(this, R.anim.rotate)
        binding.loginLoadingCircleIv.startAnimation(animation)
        binding.loginDimBackground.visibility = View.VISIBLE
    }

    override fun onDeleteSuccess() {
        binding.loginLoadingCircleIv.visibility = View.GONE
        binding.loginLoadingInIv.visibility = View.GONE
        binding.loginLoadingBackgroundIv.visibility = View.GONE
        binding.loginLoadingCircleIv.clearAnimation()
        binding.loginDimBackground.visibility = View.INVISIBLE
    }

    override fun onDeleteFailure(code: Int, message: String) {
        binding.loginLoadingCircleIv.visibility = View.GONE
        binding.loginLoadingInIv.visibility = View.GONE
        binding.loginLoadingBackgroundIv.visibility = View.GONE
        binding.loginLoadingCircleIv.clearAnimation()
        binding.loginDimBackground.visibility = View.INVISIBLE

        when (code) {
            2000, 2001, 2002 -> {
                Log.d("Finish/Jwt/ERROR", "error")
            }
            3022, 3023, 3025, 3026, 3044-> {
                Log.d("Finish/Data/ERROR", "error")
            }

            4001, 4008-> {
                Log.d("Finish/Date/ERROR", "error")
            }

            else -> {
                Log.d("Finish/SEVER/ERROR", "error")
            }
        }
    }

    fun convertDpToPixel(dp: Float, context: Context): Int {
        return (dp * (context.resources
            .displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
    }

}