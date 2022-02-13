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
            add(Photo(R.drawable.example_0213_1))
            add(Photo(R.drawable.example_0213_2))
            add(Photo(R.drawable.example_0213_3))
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

}