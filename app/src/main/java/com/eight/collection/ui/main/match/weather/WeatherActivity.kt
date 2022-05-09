package com.eight.collection.ui.main.match.weather

import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import com.eight.collection.R
import com.eight.collection.data.entities.Diary
import com.eight.collection.data.remote.match.MatchService
import com.eight.collection.databinding.ActivityMatchWeatherBinding
import com.eight.collection.ui.BaseActivity
import com.eight.collection.ui.main.match.LastTag
import com.eight.collection.ui.main.match.LastTagView
import com.eight.collection.ui.main.match.MatchButtonRVAdapter
import com.eight.collection.ui.main.match.MatchView
import com.eight.collection.ui.main.week.DiaryRVAdapter
import com.google.android.flexbox.FlexboxLayoutManager

class WeatherActivity: BaseActivity<ActivityMatchWeatherBinding>(ActivityMatchWeatherBinding::inflate),
    MatchView, LastTagView {
    private  lateinit var diaryRVAdapter: DiaryRVAdapter
    private  lateinit var matchButtonRVAdapter: MatchButtonRVAdapter
    private  lateinit var lastTagRVAdapter: MatchButtonRVAdapter
    private  var defaultTag = ArrayList<LastTag>()


    override fun initAfterBinding() {
        // 검색창 눌렀을시 이벤트
        binding.matchWeatherSearchBeforeView.setOnClickListener{
            searchViewClick()
        }

        // 돌아가기 버튼 눌렀을시 이벤트
        binding.matchWeatherSearchBackIc.setOnClickListener{
           backViewClick()
        }

        // 검색창 x버튼 눌렀을시 이벤트
        binding.matchWeatherSearchDeleteIc.setOnClickListener{
            deleteButtonClick()
        }

        // 검색 버튼 눌렀을시 이벤트
        binding.matchWeatherSearchBt.setOnClickListener{
            searchButtonClick()
        }

        //최신순 버튼 눌렀을시 이벤트
        binding.matchWeatherSearchRecentRl.setOnClickListener{
            latestButtonClick()
        }


        //      getSearchResult()
        getLastTag()

        defaultTag.apply {
            add(LastTag("매우추움", "",1,true))
            add(LastTag("매우더움", "",2,true))
            add(LastTag("추움", "",3,true))
            add(LastTag("더움", "",4,true))
            add(LastTag("적당함", "",5,true))
            add(LastTag("눈", "",6,true))
            add(LastTag("비", "",7,true))
            add(LastTag("우박", "",8,true))
        }

        val flexboxLayoutManager = FlexboxLayoutManager(this)
        matchButtonRVAdapter = MatchButtonRVAdapter()
        binding.matchWeatherDefaultRecyclerview.adapter = matchButtonRVAdapter
        binding.matchWeatherDefaultRecyclerview.layoutManager = flexboxLayoutManager
        matchButtonRVAdapter.addButton(defaultTag)
    }

    fun searchViewClick() {
        binding.matchWeatherSearchBeforeCl.visibility = View.INVISIBLE
        binding.matchWeatherSearchAfterCl.visibility = View.VISIBLE
        binding.matchWeatherSearchDefault.visibility = View.VISIBLE
        binding.matchWeatherSearchResult.visibility = View.INVISIBLE
    }

    fun backViewClick(){
        binding.matchWeatherSearchEt.setText("")
        binding.matchWeatherSearchBeforeCl.visibility = View.VISIBLE
        binding.matchWeatherSearchAfterCl.visibility = View.INVISIBLE
        binding.matchWeatherSearchDefault.visibility = View.INVISIBLE
        binding.matchWeatherSearchResult.visibility = View.INVISIBLE
    }

    fun deleteButtonClick(){
        binding.matchWeatherSearchEt.setText("")
        binding.matchWeatherSearchBeforeCl.visibility = View.INVISIBLE
        binding.matchWeatherSearchAfterCl.visibility = View.VISIBLE
        binding.matchWeatherSearchDefault.visibility = View.VISIBLE
        binding.matchWeatherSearchResult.visibility = View.INVISIBLE
    }

    fun searchButtonClick(){
        binding.matchWeatherSearchBeforeCl.visibility = View.INVISIBLE
        binding.matchWeatherSearchAfterCl.visibility = View.VISIBLE
        binding.matchWeatherSearchDefault.visibility = View.INVISIBLE
        binding.matchWeatherSearchResult.visibility = View.VISIBLE
    }

    fun latestButtonClick(){

    }

    fun historyView(){
        binding.matchWeatherLastTv.visibility = View.VISIBLE
        binding.matchAllDeleteTv.visibility = View.VISIBLE
        binding.matchWeatherLastFl.visibility = View.VISIBLE
    }

    fun historyUnView(){
        binding.matchWeatherLastTv.visibility = View.GONE
        binding.matchAllDeleteTv.visibility = View.GONE
        binding.matchWeatherLastFl.visibility = View.GONE
    }



    // API 이벤트
    private fun getSearchResult(){
        MatchService.getMatch(this, 1, "공원","", "", "", "", "")
    }

    private fun getLastTag(){
        MatchService.getLastTag(this, 1)
    }

    override fun onMatchLoading() {
        binding.loginLoadingInIv.visibility = View.VISIBLE
        binding.loginLoadingCircleIv.visibility = View.VISIBLE
        binding.loginLoadingBackgroundIv.visibility = View.VISIBLE
        val animation = AnimationUtils.loadAnimation(this, R.anim.rotate)
        binding.loginLoadingCircleIv.startAnimation(animation)
        binding.loginDimBackground.visibility = View.VISIBLE
    }

    override fun onMatchSuccess(match: MutableList<Diary>) {
        binding.loginLoadingCircleIv.visibility = View.GONE
        binding.loginLoadingInIv.visibility = View.GONE
        binding.loginLoadingBackgroundIv.visibility = View.GONE
        binding.loginLoadingCircleIv.clearAnimation()
        binding.loginDimBackground.visibility = View.INVISIBLE

        diaryRVAdapter = DiaryRVAdapter(this)
        binding.matchWeatherSearchResultRv.adapter = diaryRVAdapter

        diaryRVAdapter.addWeekly(match)
//        if( match.size != 0){
//
//
//            binding.matchDefault2Text.visibility= View.GONE
//            binding.matchDefault1Text.visibility= View.GONE
//            binding.matchDefaultIv.visibility= View.GONE
//            binding.itemTopLine1View.visibility= View.GONE
//            binding.itemTopLine2View.visibility= View.GONE
//        }
//        else{
//            diaryRVAdapter.removeWeekly()
//            binding.matchDefault2Text.visibility= View.VISIBLE
//            binding.matchDefault1Text.visibility= View.VISIBLE
//            binding.matchDefaultIv.visibility= View.VISIBLE
//            binding.itemTopLine1View.visibility= View.VISIBLE
//            binding.itemTopLine2View.visibility= View.VISIBLE
//        }
    }

    override fun onMatchFailure(code: Int, message: String) {
        binding.loginLoadingCircleIv.visibility = View.GONE
        binding.loginLoadingInIv.visibility = View.GONE
        binding.loginLoadingBackgroundIv.visibility = View.GONE
        binding.loginLoadingCircleIv.clearAnimation()
        binding.loginDimBackground.visibility = View.INVISIBLE
        when (code) {
            2000,2001, 2002 -> {
                Log.d("Match/JWT/ERROR", "error")
            }
            3101,3048, 3036 -> {
                Log.d("Match/PWWC/ERROR", "error")
            }
            3038,3113, -> {
                Log.d("Match/Keword/ERROR", "error")
            }
            3112, 3061, 3114, 3115, 3118 -> {
                Log.d("Match/Color/ERROR", "error")
            }
            3039, 3106, 3107, 3040, 3108, 3109 -> {
                Log.d("Match/Date/ERROR", "error")
            }
            3110, 3050 -> {
                Log.d("Match/Text/ERROR", "error")
            }
            4018, 4001 -> {
                Log.d("Match/Response/ERROR", "error")
            }
            else -> {
                Log.d("Month/DB/ERROR", "error")
            }
        }
    }

    override fun onLastTagLoading() {}

    override fun onLastTagSuccess(lastTag: ArrayList<LastTag>) {
        if (lastTag.isEmpty() == false){
            historyView()
            val flexboxLayoutManager = FlexboxLayoutManager(this)
            lastTagRVAdapter = MatchButtonRVAdapter()
            binding.matchWeatherLastRecyclerview.adapter = lastTagRVAdapter
            binding.matchWeatherLastRecyclerview.layoutManager = flexboxLayoutManager
            lastTagRVAdapter.addButton(lastTag)
        }
        else {
            historyUnView()
        }

    }

    override fun onLastTagFailure(code: Int, message: String) {
        historyUnView()
        Log.d("LastTag1", "error")
        when (code) {
            2000,2001, 2002 -> {
                Log.d("LastTag/JWT/ERROR", "error")
            }
            3101,3048, 3036 -> {
                Log.d("LastTag/PWWC/ERROR", "error")
            }
            else -> {
                Log.d("LastTag/DB/ERROR", "error")
            }
        }
    }



}