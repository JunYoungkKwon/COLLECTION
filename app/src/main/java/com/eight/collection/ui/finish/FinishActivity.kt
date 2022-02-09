package com.eight.collection.ui.finish

import com.eight.collection.R
import com.eight.collection.databinding.ActivityFinishBinding
import com.eight.collection.ui.BaseActivity
import java.util.ArrayList

class FinishActivity :BaseActivity<ActivityFinishBinding>(ActivityFinishBinding::inflate) {
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
        //Ootd 데이터 삽입
       val ootd = Ootd("2022/02/02",R.drawable.ic_diary_point,"ABCDEFGHIJKLMNOPQRSTUVWXYZABC", "날씨가 너무 좋았다")
        setInit(ootd)

        //더미 데이터
        weatherDatas.apply {
            add(Weather("맑음"))
            add(Weather("화창"))
            add(Weather("맑음"))
            add(Weather("구름"))
            add(Weather("추움"))
        }
        placeDatas.apply {
            add(Place("맑음2"))
            add(Place("화창2"))
            add(Place("맑음2"))
            add(Place("추움2"))
        }
        whoDatas.apply {
            add(Who("맑음3"))
            add(Who("화창3"))
            add(Who("맑음3"))
            add(Who("구름3"))
            add(Who("추움3"))
        }
        photoDatas.apply {
            add(Photo(R.drawable.example2))
            add(Photo(R.drawable.example3))
            add(Photo(R.drawable.example1))
            add(Photo(R.drawable.example3))
        }
        topDatas.apply {
            add(Top("티셔츠", "#FFFFFF"))
            add(Top("티셔츠", "#FFBB86FC"))
            add(Top("티셔츠", "#FF03DAC5"))

        }
        bottomDatas.apply {
            add(Bottom("티셔츠", "#FFFFFF"))
        }
        shoesDatas.apply {
            add(Shoes("와이셔츠", "#FFBB86FC"))
            add(Shoes("갃낛닭띿", "#FF03DAC5"))
        }
        etcDatas.apply {
            add(Etc("와이셔츠", "#FFBB86FC"))
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

}