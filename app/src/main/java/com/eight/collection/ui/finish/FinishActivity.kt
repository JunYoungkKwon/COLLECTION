package com.eight.collection.ui.finish

import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.content.ContentProviderCompat.requireContext
import com.eight.collection.R
import com.eight.collection.data.remote.finish.Finish
import com.eight.collection.data.remote.finish.FinishService
import com.eight.collection.databinding.ActivityFinishBinding
import com.eight.collection.ui.BaseActivity
import com.eight.collection.ui.main.week.DiaryRVAdapter
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


class FinishActivity :BaseActivity<ActivityFinishBinding>(ActivityFinishBinding::inflate), FinishView {

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


    private fun getFinish() {
        val string = "2022-01-07"
        FinishService.getFinish(this, string)
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

        binding.finishOotdTitileTv.text = finish.lookname
        binding.finishDateTv.text = finish.date.toString()
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
                Log.d("Finish/SEVER/ERROR", "error")
            }
        }
    }

}