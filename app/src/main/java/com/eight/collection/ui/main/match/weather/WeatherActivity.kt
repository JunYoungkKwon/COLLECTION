package com.eight.collection.ui.main.match.weather

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.SparseIntArray
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.aminography.primecalendar.civil.CivilCalendar
import com.aminography.primedatepicker.calendarview.PrimeCalendarView
import com.aminography.primedatepicker.common.BackgroundShapeType
import com.aminography.primedatepicker.common.LabelFormatter
import com.aminography.primedatepicker.picker.PrimeDatePicker
import com.aminography.primedatepicker.picker.callback.RangeDaysPickCallback
import com.aminography.primedatepicker.picker.theme.LightThemeFactory
import com.eight.collection.R
import com.eight.collection.data.entities.Diary
import com.eight.collection.data.entities.Suggest
import com.eight.collection.data.entities.Write.Content
import com.eight.collection.data.remote.match.MatchService
import com.eight.collection.data.remote.setting.SettingService
import com.eight.collection.databinding.ActivityMatchWeatherBinding
import com.eight.collection.ui.BaseActivity
import com.eight.collection.ui.finish.FinishActivity
import com.eight.collection.ui.main.match.*
import com.eight.collection.ui.main.match.place.PlaceActivity
import com.eight.collection.ui.main.week.DeleteView
import com.eight.collection.ui.main.week.DiaryRVAdapter
import com.eight.collection.ui.writing.first.WritefirstActivity
import com.eight.collection.utils.savePWWC
import com.google.android.flexbox.FlexboxLayoutManager
import com.skydoves.powermenu.OnMenuItemClickListener
import com.skydoves.powermenu.PowerMenu
import com.skydoves.powermenu.PowerMenuItem
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class WeatherActivity: BaseActivity<ActivityMatchWeatherBinding>(ActivityMatchWeatherBinding::inflate),
    MatchView, LastTagView, DeleteTagView, MatchButtonRVAdapter.MyitemClickListener, SuggestTagView,
    DeleteView {
    private  lateinit var diaryRVAdapter: DiaryRVAdapter
    private  lateinit var matchButtonRVAdapter: MatchButtonRVAdapter
    private  lateinit var lastTagRVAdapter: MatchButtonRVAdapter
    private  lateinit var suggestTagRVAdapter : MatchButtonRVAdapter
    private  lateinit var searchTagRVAdapter: SearchTagRVAdapter

    private  var defaultTag = ArrayList<LastTag>()
    private  var reallastTag = ArrayList<LastTag>()
    private var suggestTag = ArrayList<LastTag>()
    private lateinit var searchEditText : EditText
    private var searchKeyword = ArrayList<LastTag>()
    private var keywordList = ArrayList<String>()

    private var suggestResult : Boolean = false
    private var clicked : Boolean = false
    private var startDate: String = ""
    private var endDate : String = ""
    private var keyword1 : String = ""
    private var keyword2 : String = ""
    private var moveToDate: LocalDate? = null


    override fun initAfterBinding() {
        savePWWC(1)
        // ????????? ???????????? ?????????
        binding.matchWeatherSearchBeforeView.setOnClickListener{
            searchViewClick()
        }

        // ???????????? ???????????? ?????????
        binding.matchAllDeleteTv.setOnClickListener{
            removeAllTag()
        }

        // ???????????? ?????? ???????????? ?????????
        binding.matchWeatherSearchBackIc.setOnClickListener{
           backViewClick()
        }

        // ????????? x?????? ???????????? ?????????
        binding.matchWeatherSearchDeleteIc.setOnClickListener{
            deleteButtonClick()
        }

        // ?????? ?????? ???????????? ?????????
        binding.matchWeatherSearchBt.setOnClickListener{
            startDate =""
            endDate = ""
            keyword1 =""
            keyword2 = ""
            keywordList.clear()
            searchButtonClick()
            getSearchResult()
            getLastTag()
        }

        // ???????????? ?????? ???????????? ?????????
        binding.matchWeatherSearchResultDayIb.setOnClickListener{
            initCalendar()
        }

        // ????????? ?????? ???????????? ?????????
        binding.matchWeatherSearchResultLastIb.setOnClickListener{
            latestButtonClick()
        }


        // ?????? ??? ?????? ?????? ??????
        getLastTag()

        defaultTag.apply {
            add(LastTag("????????????", "",1,true))
            add(LastTag("????????????", "",2,true))
            add(LastTag("??????", "",3,true))
            add(LastTag("??????", "",4,true))
            add(LastTag("?????????", "",5,true))
            add(LastTag("???", "",6,true))
            add(LastTag("???", "",7,true))
            add(LastTag("??????", "",8,true))
        }

        val flexboxLayoutManager = FlexboxLayoutManager(this)
        matchButtonRVAdapter = MatchButtonRVAdapter()
        binding.matchWeatherDefaultRecyclerview.adapter = matchButtonRVAdapter
        binding.matchWeatherDefaultRecyclerview.layoutManager = flexboxLayoutManager
        matchButtonRVAdapter.addButton(defaultTag)

        // ?????? ?????? Click ???
        matchButtonRVAdapter.setMyItemClickListener(this)

        // ?????? EditText ?????? ???
        searchEditText = findViewById(R.id.match_weather_search_et)

        val flexboxLayoutManager2 = FlexboxLayoutManager(this)
        suggestTagRVAdapter = MatchButtonRVAdapter()
        binding.matchWeatherSearchButtonRecyclerview.adapter = suggestTagRVAdapter
        binding.matchWeatherSearchButtonRecyclerview.layoutManager = flexboxLayoutManager2

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            // ????????? ?????? ?????? ???
                suggestTag.clear()
                suggestTagRVAdapter.notifyDataSetChanged()
            }
            override fun afterTextChanged(p0: Editable?) {
            // ?????? ????????? ???
                var edtext : String = searchEditText.text.toString()
                if(edtext != "") {
                    suggestTag(edtext)
                    binding.matchWeatherSearchClothIv.visibility = View.GONE
                    binding.matchWeatherSearchTv.visibility = View.GONE
                    binding.matchWeatherSearchButtonLayout.visibility = View.VISIBLE
                    if(suggestResult == false) {
                        suggestTag.clear()
                        suggestTagRVAdapter = MatchButtonRVAdapter()
                        binding.matchWeatherSearchButtonRecyclerview.adapter = suggestTagRVAdapter
                    }
                }
                else {
                    binding.matchWeatherSearchClothIv.visibility = View.VISIBLE
                    binding.matchWeatherSearchTv.visibility = View.VISIBLE
                    binding.matchWeatherSearchButtonLayout.visibility = View.GONE
                    suggestTag.clear()
                    suggestTagRVAdapter.notifyDataSetChanged()
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            // ?????? ?????? ???
            }
        })
    }

    fun searchViewClick() {
        binding.matchWeatherSearchBeforeCl.visibility = View.INVISIBLE
        binding.matchWeatherSearchAfterCl.visibility = View.VISIBLE
        binding.matchWeatherSearchDefault.visibility = View.VISIBLE
        binding.matchWeatherSearchResult.visibility = View.INVISIBLE


    }

    fun backViewClick(){
        binding.matchWeatherSearchEt.setText("")
        binding.matchWeatherSearchEt.visibility = View.VISIBLE
        binding.matchWeatherSearchBeforeCl.visibility = View.VISIBLE
        binding.matchWeatherSearchAfterCl.visibility = View.INVISIBLE
        binding.matchWeatherSearchDefault.visibility = View.INVISIBLE
        binding.matchWeatherSearchResult.visibility = View.INVISIBLE

        searchKeyword.clear()
        searchTagRVAdapter = SearchTagRVAdapter(searchKeyword)
        binding.matchWeatherSearchButtonResultRecyclerview.adapter = searchTagRVAdapter
        searchTagRVAdapter.notifyDataSetChanged()
    }

    fun deleteButtonClick(){
        binding.matchWeatherSearchEt.setText("")
        binding.matchWeatherSearchEt.visibility = View.VISIBLE
        binding.matchWeatherSearchBeforeCl.visibility = View.INVISIBLE
        binding.matchWeatherSearchAfterCl.visibility = View.VISIBLE
        binding.matchWeatherSearchDefault.visibility = View.VISIBLE
        binding.matchWeatherSearchResult.visibility = View.INVISIBLE

        searchKeyword.clear()
        searchTagRVAdapter = SearchTagRVAdapter(searchKeyword)
        binding.matchWeatherSearchButtonResultRecyclerview.adapter = searchTagRVAdapter
        searchTagRVAdapter.notifyDataSetChanged()
    }

    fun searchButtonClick(){
        binding.matchWeatherSearchBeforeCl.visibility = View.INVISIBLE
        binding.matchWeatherSearchAfterCl.visibility = View.VISIBLE
        binding.matchWeatherSearchDefault.visibility = View.INVISIBLE
        binding.matchWeatherSearchResult.visibility = View.VISIBLE
        binding.matchWeatherSearchEt.visibility = View.INVISIBLE
    }

    fun latestButtonClick(){
        if(clicked == true){
            if(startDate == "" && endDate == ""){
                MatchService.getMatch(this, 0, keyword1, keyword2, "", "", "", "")
            }else{

                MatchService.getMatch(this, 0, keyword1, keyword2, "", "", startDate,endDate)
            }
            binding.matchWeatherSearchResultLastIb.setImageResource(R.drawable.button_search_last)
            clicked = false
        }
        else{
            if(startDate == "" && endDate == ""){
                MatchService.getMatch(this, 0, keyword1, keyword2, "", "", "", "")
            }else{

                MatchService.getMatch(this, 0, keyword1, keyword2, "", "", startDate,endDate)
            }
            binding.matchWeatherSearchResultLastIb.setImageResource(R.drawable.button_search_old)
            clicked = true
        }
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


    // API ?????????

    private fun getLastTag(){
        MatchService.getLastTag(this, 1)
    }

    override fun onLastTagLoading() {}

    override fun onLastTagSuccess(lastTag: ArrayList<LastTag>) {
        reallastTag.clear()
        reallastTag = lastTag
        if (reallastTag.isEmpty() == false){
            historyView()
            val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.HORIZONTAL)
            lastTagRVAdapter = MatchButtonRVAdapter()
            binding.matchWeatherLastRecyclerview.adapter = lastTagRVAdapter
            binding.matchWeatherLastRecyclerview.layoutManager = staggeredGridLayoutManager
            lastTagRVAdapter.addButton(reallastTag)
            lastTagRVAdapter.setMyItemClickListener(this)
            lastTagRVAdapter.notifyDataSetChanged()
        }
        else {
            historyUnView()
        }
    }

    override fun onLastTagFailure(code: Int, message: String) {
        historyUnView()
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


    fun removeAllTag(){
        deleteAllTag()
        reallastTag.clear()
        lastTagRVAdapter.notifyDataSetChanged()
    }

    private fun getContent(content : String) : Content {
        return Content(content)
    }

    private fun deleteAllTag(){
        MatchService.deleteTag(this, 1, 2, getContent("??????"))
    }

    override fun onDeleteTagLoading() {
    }

    override fun onDeleteTagSuccess() {
        Log.d("message","Delete Success")
    }

    override fun onDeleteTagFailure(code: Int, message: String) {
        when(code) {
            4016 -> {
                Log.d("message",message)
            }
            else -> {
                Log.d("message",message)
            }
        }
    }

    override fun onItemClick(lastTag: LastTag, position: Int) {
        searchKeyword.apply{
            add(LastTag(lastTag.text))
        }
        searchTagRVAdapter = SearchTagRVAdapter(searchKeyword)
        binding.matchWeatherSearchButtonResultRecyclerview.adapter = searchTagRVAdapter
        searchTagRVAdapter.notifyDataSetChanged()
        searchViewClick()
        binding.matchWeatherSearchEt.setText("")
    }

    private fun suggestTag(text : String) {
        MatchService.suggestTag(this,1,text)
    }

    override fun onSuggestTagLoading() {
    }

    override fun onSuggestTagSuccess(suggestion: ArrayList<Suggest>?) {
        if (suggestion != null) {
            for (i in suggestion){
                suggestTag.apply{
                    add(LastTag(i.weather, isdefault = true))
                }
            }
            val flexboxLayoutManager2 = FlexboxLayoutManager(this)
            suggestTagRVAdapter = MatchButtonRVAdapter()
            binding.matchWeatherSearchButtonRecyclerview.adapter = suggestTagRVAdapter
            binding.matchWeatherSearchButtonRecyclerview.layoutManager = flexboxLayoutManager2
            suggestTagRVAdapter.addButton(suggestTag)
            suggestTagRVAdapter.setMyItemClickListener(this)
            suggestResult = true
        }
    }

    override fun onSuggestTagFailure(code: Int, message: String) {
        when(code) {
            4019 -> {
                Log.d("message",message)
                suggestTag.clear()
                suggestTagRVAdapter.notifyDataSetChanged()
            }
            else -> {
                Log.d("message",message)
            }
        }
        suggestResult = false
    }

    private fun getSearchResult(){
        var count : Int = 1
        for(i in searchKeyword){
            if(count == 1){
                keyword1 = i.text.toString()
                count = count + 1
            }
            else if (count == 2){
                keyword2 = i.text.toString()
                count = count + 1
            }
        }
        if(keyword1 != "" ){
            keywordList.add(keyword1)
            Log.d("test", keyword1)
        }
        if(keyword2 != "" ){
            keywordList.add(keyword2)
            Log.d("test", keyword2)
        }
        if(keywordList.size != 0){

        }
        if(startDate == "" && endDate == ""){
            MatchService.getMatch(this, 1, keyword1, keyword2, "", "", "", "")
        }else{

            MatchService.getMatch(this, 1, keyword1, keyword2, "", "", startDate,endDate)
        }

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

        binding.matchWeatherSearchResultRv.visibility = View.VISIBLE
        binding.matchWeatherSearchResultTv.text = match.size.toString()
        binding.matchDefaultIv.visibility = View.INVISIBLE
        binding.matchDefault1Text.visibility = View.INVISIBLE
        binding.matchDefault2Text.visibility = View.INVISIBLE
        binding.itemTopLine1View.visibility = View.INVISIBLE
        binding.itemTopLine2View.visibility = View.INVISIBLE

        diaryRVAdapter = DiaryRVAdapter(this)
        binding.matchWeatherSearchResultRv.adapter = diaryRVAdapter
        diaryRVAdapter.addKeyword(keywordList)

        if( match.size == 0) {diaryRVAdapter.removeWeekly()}

        if(clicked == true){
            match.reverse()
            diaryRVAdapter.addWeekly(match)
        }
        else{
            diaryRVAdapter.addWeekly(match)
        }



        diaryRVAdapter.setMyitemClickListener(object : DiaryRVAdapter.MyitemClickListener {
            override fun onRemoveDiary(view: View, position: Int) {

                val powerMenu = PowerMenu.Builder(this@WeatherActivity)
                    .addItem(PowerMenuItem("????????????", false))
                    .addItem(PowerMenuItem("????????????", false))
                    .setMenuRadius(15f)
                    .setDivider(
                        ColorDrawable(
                            ContextCompat.getColor(
                                this@WeatherActivity,
                                R.color.pinkish_grey
                            )
                        )
                    )
                    .setDividerHeight(1)
                    .setShowBackground(false)
                    .setMenuShadow(20f)
                    .setTextColor(ContextCompat.getColor(this@WeatherActivity, R.color.dark_taupe))
                    .setTextGravity(Gravity.CENTER)
                    .setTextTypeface(Typeface.create("@font/suit_regular", Typeface.NORMAL))
                    .setMenuColor(Color.WHITE)
                    .build()

                val onMenuItemClickListener =
                    OnMenuItemClickListener<PowerMenuItem> { position1, item ->
                        when (item.title) {
                            "????????????" -> {
                                val date = match?.get(position)?.date
                                var localdate: LocalDate? = date?.toInstant()
                                    ?.atZone(ZoneId.systemDefault())
                                    ?.toLocalDate()
                                moveToDate = localdate
                                val formatters = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                                val deleteDate: String? = localdate?.format(formatters)
                                match?.removeAt(position)
                                val intent2 = Intent(this@WeatherActivity, WritefirstActivity::class.java)
                                intent2.putExtra("date", deleteDate)
                                startActivity(intent2)
                            }
                            "????????????" -> {
                                diaryRVAdapter.removeItem(position)
                                val date = match?.get(position)?.date
                                var localdate: LocalDate? = date?.toInstant()
                                    ?.atZone(ZoneId.systemDefault())
                                    ?.toLocalDate()
                                moveToDate = localdate
                                val formatters = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                                val deleteDate: String? = localdate?.format(formatters)
                                match?.removeAt(position)
                                if (deleteDate != null) {
                                    deleteOOTD(deleteDate)
                                }
                            }
                        }
                        powerMenu.selectedPosition = position
                        powerMenu.dismiss()
                    }
                powerMenu.onMenuItemClickListener = onMenuItemClickListener
                powerMenu.showAsDropDown(view, -30, -30)
            }

            override fun onStartFinish(position: Int) {
                val date = match?.get(position)?.date
                var localdate: LocalDate? = date?.toInstant()
                    ?.atZone(ZoneId.systemDefault())
                    ?.toLocalDate()
                moveToDate = localdate
                val formatters = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val moveDate: String? = localdate?.format(formatters)

                val intent = Intent(this@WeatherActivity, FinishActivity::class.java)
                intent.apply {
                    this.putExtra("date",moveDate)
                }
                startActivity(intent)
            }
        })
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
            3038 -> {
                binding.matchWeatherSearchResultTv.text = "0"
                binding.matchWeatherSearchResultRv.visibility = View.INVISIBLE

                binding.matchDefaultIv.visibility = View.VISIBLE
                binding.matchDefault1Text.text = "???????????? ????????? ?????????."
                binding.matchDefault2Text.visibility = View.INVISIBLE
                binding.matchDefault1Text.visibility = View.VISIBLE
                binding.itemTopLine1View.visibility = View.VISIBLE
                binding.itemTopLine2View.visibility = View.VISIBLE
                Log.d("Match/EmptyKeyWord/ERROR", "error")
            }
            3113 -> {
                Log.d("Match/KeyWord2Color/ERROR", "error")
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
            4001 -> {
                binding.matchWeatherSearchResultTv.text = "0"
                binding.matchWeatherSearchResultRv.visibility = View.INVISIBLE

                binding.matchDefaultIv.visibility = View.VISIBLE
                binding.matchDefault1Text.visibility = View.VISIBLE
                binding.matchDefault2Text.visibility = View.VISIBLE
                binding.itemTopLine1View.visibility = View.VISIBLE
                binding.itemTopLine2View.visibility = View.VISIBLE
                Log.d("Match/DateNoFind/ERROR", "error")

            }
            4018 -> {
                binding.matchWeatherSearchResultTv.text = "0"
                binding.matchWeatherSearchResultRv.visibility = View.INVISIBLE

                val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_keyword_no_find_custom, null)
                val mBuilder = AlertDialog.Builder(this)
                    .setView(mDialogView)
                    .setCancelable(false)
                val  mAlertDialog = mBuilder.show()
                mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                val noButton = mDialogView.findViewById<ImageButton>(R.id.dialog_cancle_ib)
                noButton.setOnClickListener {
                    finishActivity()
                    startActivity(Intent(this, WeatherActivity::class.java))
                    mAlertDialog.dismiss()
                }

                Log.d("Match/KeywordNoFind/ERROR", "error")
            }
            5000 -> {
                Log.d("Match/DB1/ERROR", "error")
            }
            6000 -> {
                Log.d("Match/DB2/ERROR", "error")
            }
            else -> {
                Log.d("Match/DB3/ERROR", "error")
            }
        }
    }

    fun initCalendar() {

        val themeFactory = object : LightThemeFactory() {

            override val typefacePath: String?
                get() = "roboto.ttf"

            override val calendarViewFlingOrientation: PrimeCalendarView.FlingOrientation
                get() = PrimeCalendarView.FlingOrientation.HORIZONTAL

            override val calendarViewDayLabelVerticalPadding: Int
                get() = getDimension(R.dimen.defaultElementPaddingBottom)


            override val actionBarTodayTextColor: Int
                get() = getColor(R.color.dark_taupe)

            override val actionBarNegativeTextColor: Int
                get() = getColor(R.color.pinkish_grey)

            override val actionBarPositiveTextColor: Int
                get() = getColor(R.color.terracota)

            override val dialogBackgroundColor: Int
                get() = getColor(R.color.white)

            override val calendarViewBackgroundColor: Int
                get() = getColor(R.color.white)

            override val pickedDayBackgroundShapeType: BackgroundShapeType
                get() = BackgroundShapeType.CIRCLE

            override val calendarViewPickedDayBackgroundColor: Int
                get() = getColor(R.color.terracota)

            override val calendarViewPickedDayInRangeBackgroundColor: Int
                get() = getColor(R.color.bottom_navi)

            override val calendarViewPickedDayInRangeLabelTextColor: Int
                get() = getColor(R.color.black)

            override val calendarViewTodayLabelTextColor: Int
                get() = getColor(R.color.terracota)

            override val calendarViewMonthLabelTextColor: Int
                get() = getColor(R.color.terracota)

            override val calendarViewWeekLabelFormatter: LabelFormatter
                get() = { primeCalendar ->
                    when (primeCalendar[Calendar.DAY_OF_WEEK]) {
                        Calendar.SATURDAY,
                        Calendar.SUNDAY -> String.format("S")
                        Calendar.MONDAY -> String.format("M")
                        Calendar.TUESDAY -> String.format("T")
                        Calendar.WEDNESDAY -> String.format("W")
                        Calendar.THURSDAY -> String.format("T")
                        Calendar.FRIDAY -> String.format("F")
                        else -> String.format("Error")
                    }
                }

            override val calendarViewMonthLabelFormatter: LabelFormatter
                get() = { primeCalendar ->
                    String.format("%s", primeCalendar.year)
                    when (primeCalendar[Calendar.MONTH]) {
                        Calendar.JANUARY -> String.format("%s.1", primeCalendar.year)
                        Calendar.FEBRUARY -> String.format("%s.2", primeCalendar.year)
                        Calendar.MARCH -> String.format("%s.3", primeCalendar.year)
                        Calendar.APRIL -> String.format("%s.4", primeCalendar.year)
                        Calendar.MAY -> String.format("%s.5", primeCalendar.year)
                        Calendar.JUNE -> String.format("%s.6", primeCalendar.year)
                        Calendar.JULY -> String.format("%s.7", primeCalendar.year)
                        Calendar.AUGUST -> String.format("%s.8", primeCalendar.year)
                        Calendar.SEPTEMBER -> String.format("%s.9", primeCalendar.year)
                        Calendar.OCTOBER -> String.format("%s.10", primeCalendar.year)
                        Calendar.NOVEMBER -> String.format("%s.11", primeCalendar.year)
                        Calendar.DECEMBER -> String.format("%s.12", primeCalendar.year)
                        else -> String.format("Error")
                    }

                }

            override val calendarViewWeekLabelTextColors: SparseIntArray
                get() = SparseIntArray(7).apply {
                    val orange = getColor(R.color.terracota)
                    put(Calendar.SATURDAY, orange)
                    put(Calendar.SUNDAY, orange)
                    put(Calendar.MONDAY, orange)
                    put(Calendar.TUESDAY, orange)
                    put(Calendar.WEDNESDAY, orange)
                    put(Calendar.THURSDAY, orange)
                    put(Calendar.FRIDAY, orange)
                }

            override val selectionBarBackgroundColor: Int
                get() = getColor(R.color.pinkish_grey)

            override val selectionBarRangeDaysItemBackgroundColor: Int
                get() = getColor(R.color.terracota)

            override val calendarViewDividerColor: Int
                get() = getColor(R.color.transparent)

            override val gotoViewBackgroundColor: Int
                get() = getColor(R.color.background_bs)

            override val gotoViewTextColor: Int
                get() = getColor(R.color.white)

            override val gotoViewDividerColor: Int
                get() = getColor(R.color.terracota)
        }

        val today = CivilCalendar()

        val callback = RangeDaysPickCallback { startDay, endDay ->
            startDate = startDay.shortDateString.replace("/","-")
            endDate = endDay.shortDateString.replace("/","-")
            getSearchResult()

        }

        val datePicker = PrimeDatePicker.bottomSheetWith(today)
            .pickRangeDays(callback)
            .applyTheme(themeFactory)
            .build()

        datePicker.show(supportFragmentManager, "SOME_TAG")

    }

    private fun deleteOOTD(date : String) {
        if (date != null) {
            SettingService.deleteOOTD(this, date)
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
        getSearchResult()
    }

    override fun onDeleteFailure(code: Int, message: String) {
        binding.loginLoadingCircleIv.visibility = View.GONE
        binding.loginLoadingInIv.visibility = View.GONE
        binding.loginLoadingBackgroundIv.visibility = View.GONE
        binding.loginLoadingCircleIv.clearAnimation()
        binding.loginDimBackground.visibility = View.INVISIBLE

        when (code) {
            2000, 2001, 2002 -> {
                Log.d("Place/Jwt/ERROR", "error")
            }
            3022, 3023, 3025, 3026, 3044-> {
                Log.d("Place/Data/ERROR", "error")
            }
            4001, 4008-> {
                Log.d("Place/Date/ERROR", "error")
            }
            else -> {
                Log.d("Place/SEVER/ERROR", "error")
            }
        }
    }


}