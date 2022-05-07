package com.eight.collection.ui.main.match.who

import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import com.aminography.primecalendar.civil.CivilCalendar
import com.aminography.primedatepicker.picker.PrimeDatePicker
import com.aminography.primedatepicker.picker.callback.RangeDaysPickCallback
import com.aminography.primedatepicker.picker.callback.SingleDayPickCallback
import com.eight.collection.R
import com.eight.collection.data.entities.Diary
import com.eight.collection.data.remote.match.MatchService
import com.eight.collection.databinding.ActivityMatchWhoBinding
import com.eight.collection.ui.BaseActivity
import com.eight.collection.ui.main.match.*
import com.eight.collection.ui.main.week.DiaryRVAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlin.collections.ArrayList

class WhoActivity: BaseActivity<ActivityMatchWhoBinding>(ActivityMatchWhoBinding::inflate), MatchView, LastTagView {


    private  lateinit var diaryRVAdapter: DiaryRVAdapter
    private  lateinit var matchButtonRVAdapter: MatchButtonRVAdapter
    private  lateinit var lastTagRVAdapter: MatchButtonRVAdapter
    private  var defaultTag = ArrayList<LastTag>()

    override fun initAfterBinding() {
//        getSearchResult()
        getLastTag()

        defaultTag.apply {
            add(LastTag("나", "",1))
            add(LastTag("친구", "",2))
            add(LastTag("애인", "",3))
            add(LastTag("가족", "",4))
            add(LastTag("동료", "",5))
            add(LastTag("애완동물", "",6))
        }

        matchButtonRVAdapter = MatchButtonRVAdapter()
        binding.matchWhoDefaultRecyclerview.adapter = matchButtonRVAdapter
        matchButtonRVAdapter.addButton(defaultTag)

//        val bottomSheetFragment = CalendarBSActivity(applicationContext)
//        val bottomSheetView = layoutInflater.inflate(R.layout.activity_match_calendar_bs, null)
//        val bottomSheetDialog = BottomSheetDialog(this)
//        bottomSheetDialog.setContentView(bottomSheetView)

        binding.matchAllDeleteTv.setOnClickListener {
//            bottomSheetDialog.show()
//            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)

            // To show a date picker with Civil dates, also today as the starting date
            val callback = RangeDaysPickCallback { startDay, endDay ->
            }

            val today = CivilCalendar()

            val datePicker = PrimeDatePicker.bottomSheetWith(today)
                .pickRangeDays(callback)
                .build()

            datePicker.show(supportFragmentManager, "SOME_TAG")


        }

    }

    private fun getSearchResult(){
        MatchService.getMatch(this, 0, "공원","", "", "", "", "")
    }

    private fun getLastTag(){
        MatchService.getLastTag(this, 0)
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
        lastTagRVAdapter = MatchButtonRVAdapter()
        binding.matchWhoLastRecyclerview.adapter = lastTagRVAdapter
        lastTagRVAdapter.addButton(lastTag)
    }

    override fun onLastTagFailure(code: Int, message: String) {
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


