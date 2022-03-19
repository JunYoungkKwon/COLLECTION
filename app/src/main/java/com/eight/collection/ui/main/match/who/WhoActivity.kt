package com.eight.collection.ui.main.match.who

import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import com.eight.collection.R
import com.eight.collection.data.entities.Diary
import com.eight.collection.data.remote.match.MatchService
import com.eight.collection.databinding.ActivityMatchWhoBinding
import com.eight.collection.ui.BaseActivity
import com.eight.collection.ui.main.match.MatchView
import com.eight.collection.ui.main.week.DiaryRVAdapter

class WhoActivity: BaseActivity<ActivityMatchWhoBinding>(ActivityMatchWhoBinding::inflate), MatchView {


    private  lateinit var diaryRVAdapter: DiaryRVAdapter

    override fun initAfterBinding() {
        getSearchResult()
    }

    private fun getSearchResult(){
        MatchService.getMatch(this, 0, "공원","", "", "", "", "")
    }

    override fun onMatchLoading() {
        Log.d("Match/Load","test")
        binding.loginLoadingInIv.visibility = View.VISIBLE
        binding.loginLoadingCircleIv.visibility = View.VISIBLE
        binding.loginLoadingBackgroundIv.visibility = View.VISIBLE
        val animation = AnimationUtils.loadAnimation(this, R.anim.rotate)
        binding.loginLoadingCircleIv.startAnimation(animation)
        binding.loginDimBackground.visibility = View.VISIBLE
    }

    override fun onMatchSuccess(match: MutableList<Diary>) {
        Log.d("Match/Succ","test")
        binding.loginLoadingCircleIv.visibility = View.GONE
        binding.loginLoadingInIv.visibility = View.GONE
        binding.loginLoadingBackgroundIv.visibility = View.GONE
        binding.loginLoadingCircleIv.clearAnimation()
        binding.loginDimBackground.visibility = View.INVISIBLE

        diaryRVAdapter = DiaryRVAdapter(this)
        binding.matchWhoSearchResultRv.adapter = diaryRVAdapter

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
        Log.d("Match/Load","test")
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
}