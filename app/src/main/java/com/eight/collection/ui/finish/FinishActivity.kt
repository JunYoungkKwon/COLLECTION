package com.eight.collection.ui.finish

import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import com.eight.collection.R
import com.eight.collection.data.remote.finish.Finish
import com.eight.collection.data.remote.finish.FinishService
import com.eight.collection.databinding.ActivityFinishBinding
import com.eight.collection.ui.BaseActivity
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity :BaseActivity<ActivityFinishBinding>(ActivityFinishBinding::inflate), FinishView {
    private  var weatherDatas = ArrayList<Weather>()
    private  var placeDatas = ArrayList<Place>()
    private  var whoDatas = ArrayList<Who>()
    private  var photoDatas = ArrayList<Photo>()
    private  var topDatas = ArrayList<Top>()
    private  var bottomDatas = ArrayList<Bottom>()
    private  var shoesDatas = ArrayList<Shoes>()
    private  var etcDatas = ArrayList<Etc>()
    private  var ootdDatas = ArrayList<Ootd>()

    override fun initAfterBinding() {
        getFinish()
        //Ootd 데이터 삽입
       val ootd = Ootd("2022/02/12",R.drawable.ic_diary_point_5,"캐주얼룩", "오늘 옷 잘어울린다는 칭찬을 받았다.")
        setInit(ootd)

        //더미 데이터
        weatherDatas.apply {
            add(Weather("매우추움"))
        }
        placeDatas.apply {
            add(Place("핫플레이스"))
        }
        whoDatas.apply {
            add(Who("애인"))
        }
        photoDatas.apply {
            add(Photo("",null,R.drawable.example_0213_1))
        }
        topDatas.apply {
            add(Top("크롭티", "#FFFFFF"))

        }
        bottomDatas.apply {
            add(Bottom("데님팬츠", "#273e88"))
        }
        shoesDatas.apply {
            add(Shoes("스니커즈", "#FFFFFF"))
        }
        etcDatas.apply {
            add(Etc("주얼리", "#888888"))
        }

        //더미데이터와 어댑터 연결
        val weatherRVAdapter = WeatherRVAdapter(weatherDatas)
        val placeRVAdapter = PlaceRVAdapter(placeDatas)
        val whoVAdapter = WhoRVAdapter(whoDatas)
        val photoVAdapter = PhotoRVAdapter(photoDatas)
        val topRVAdapter = TopRVAdapter(topDatas)
        val bottomRVAdapter = BottomRVAdapter(bottomDatas)
        val shoesRVAdapter = ShoesRVAdapter(shoesDatas)
        val etcRVAdapter = EtcRVAdapter(etcDatas)

        //리사이클러뷰와 어댑터 연결
        binding.finishWeatherRecyclerView.adapter = weatherRVAdapter
        binding.finishPlaceRecyclerView.adapter = placeRVAdapter
        binding.finishWhoRecyclerView.adapter = whoVAdapter
        binding.finishPhotoRecyclerview.adapter = photoVAdapter
        binding.finishTopRecyclerView.adapter = topRVAdapter
        binding.finishBottomRecyclerView.adapter = bottomRVAdapter
        binding.finishShoesRecyclerView.adapter = shoesRVAdapter
        binding.finishEtcRecyclerView.adapter = etcRVAdapter

    }
    private fun setInit(ootd: Ootd) {
        binding.finishRankPointIv.setImageResource(ootd.point!!)
        binding.finishOotdTitileTv.text =ootd.title.toString()
        binding.finishDateTv.text = ootd.date.toString()
        binding.finishCommentTv.text = ootd.comment.toString()
    }

    private fun getFinish() {
        val string = "2022-01-02"
        val date: Date = SimpleDateFormat("yyyy-MM-dd").parse(string)
        FinishService.getFinish(this, date)
    }

    override fun onFinishLoading() {
        binding.loginLoadingInIv.visibility = View.VISIBLE
        binding.loginLoadingCircleIv.visibility = View.VISIBLE
        binding.loginLoadingBackgroundIv.visibility = View.VISIBLE
        //로딩이미지 애니메이션
        val animation = AnimationUtils.loadAnimation(this, R.anim.rotate)
        binding.loginLoadingCircleIv.startAnimation(animation)
        binding.loginDimBackground.visibility = View.VISIBLE
        Log.d("Load","Test")
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
            else -> binding.finishRankPointIv.setImageResource(0)
        }
        binding.finishOotdTitileTv.text = finish.lookname
        binding.finishDateTv.text = finish.date.toString()
        binding.finishCommentTv.text = finish.comment
        Log.d("Success","Test")



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
            3026,3044, 3021 -> {
                Log.d("Finish/Data/ERROR", "error")
            }
            4001 -> {
                Log.d("Finish/Date/ERROR", "error")
            }
            else -> {
                Log.d("Finish/Sever/ERROR", "error")
            }
        }
        Log.d("Fail","Test")
    }

}