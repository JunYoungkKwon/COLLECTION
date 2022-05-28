package com.eight.collection.ui.main.match.color

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.eight.collection.R
import com.eight.collection.data.entities.Diary
import com.eight.collection.data.entities.Suggest
import com.eight.collection.data.entities.Write.Content
import com.eight.collection.data.remote.match.MatchService
import com.eight.collection.databinding.*
import com.eight.collection.ui.BaseActivity
import com.eight.collection.ui.main.match.*
import com.eight.collection.ui.main.week.DiaryRVAdapter
import com.eight.collection.utils.getContent
import com.eight.collection.utils.saveContent
import com.eight.collection.utils.savePWWC
import com.google.android.flexbox.FlexboxLayoutManager

class ColorActivity(): BaseActivity<ActivityMatchColorBinding>(ActivityMatchColorBinding::inflate),
    MatchView, LastTagView, DeleteTagView, MatchButtonRVAdapter.MyitemClickListener, MatchColorButtonRVAdapter.MyitemClickListener, SuggestTagView, BottomSheetInterface {
    private  lateinit var diaryRVAdapter: DiaryRVAdapter
    private  lateinit var matchButtonRVAdapter: MatchButtonRVAdapter
    private  lateinit var lastTagRVAdapter: MatchButtonRVAdapter
    private  lateinit var suggestTagRVAdapter : MatchColorButtonRVAdapter
    private  lateinit var searchTagRVAdapter: SearchColorTagRVAdapter
    private  var reallastTag = ArrayList<LastTag>()
    private var suggestTag = ArrayList<LastTag>()
    private lateinit var searchEditText : EditText
    private var searchKeyword = ArrayList<LastTag>()
    private var suggestResult : Boolean = false
    private var suggestContent : String? = ""

    lateinit var bottomSheet : BottomSheet

    override fun initAfterBinding() {
        savePWWC(3)

        bottomSheet = BottomSheet(this,this)

        // 검색창 눌렀을시 이벤트
        binding.matchColorSearchBeforeView.setOnClickListener{
            searchViewClick()
        }

        // 모두삭제 눌렀을시 이벤트
        binding.matchAllDeleteTv.setOnClickListener{
            removeAllTag()
        }

        // 돌아가기 버튼 눌렀을시 이벤트
        binding.matchColorSearchBackIc.setOnClickListener{
            backViewClick()
        }

        // 검색창 x버튼 눌렀을시 이벤트
        binding.matchColorSearchDeleteIc.setOnClickListener{
            deleteButtonClick()
        }

        // 검색 버튼 눌렀을시 이벤트
        binding.matchColorSearchBt.setOnClickListener{
            searchButtonClick()
            getSearchResult()
            getLastTag()
        }

        // 최신순 버튼 눌렀을시 이벤트
        binding.matchColorSearchRecentRl.setOnClickListener{
            latestButtonClick()
        }

        // Color 특수 버튼 눌렀을시 이벤트
        binding.matchColorMatchBeforeBt.setOnClickListener{
            openColorUI()
        }
        binding.matchColorMatchAfterBt.setOnClickListener{
            openColorUI()
        }

        getLastTag()

        matchButtonRVAdapter = MatchButtonRVAdapter()

        // 태그 버튼 Click 시
        matchButtonRVAdapter.setMyItemClickListener(this)

        // 검색 EditText 이용 시
        searchEditText = findViewById(R.id.match_color_search_et)

        val flexboxLayoutManager2 = FlexboxLayoutManager(this)
        suggestTagRVAdapter = MatchColorButtonRVAdapter()
        binding.matchColorSearchButtonRecyclerview.adapter = suggestTagRVAdapter
        binding.matchColorSearchButtonRecyclerview.layoutManager = flexboxLayoutManager2

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // 입력난 변화 있을 시
                suggestTag.clear()
                suggestTagRVAdapter.notifyDataSetChanged()

            }
            override fun afterTextChanged(p0: Editable?) {
                // 입력 끝났을 때
                var edtext : String = searchEditText.text.toString()
                if(edtext != "") {
                    suggestTag(edtext)
                    binding.matchColorSearchButtonLayout.visibility = View.VISIBLE
                    if(suggestResult == false) {
                        suggestTag.clear()
                        suggestTagRVAdapter = MatchColorButtonRVAdapter()
                        binding.matchColorSearchButtonRecyclerview.adapter = suggestTagRVAdapter
                    }
                }
                else {
                    binding.matchColorSearchButtonLayout.visibility = View.GONE
                    suggestTag.clear()
                    suggestTagRVAdapter.notifyDataSetChanged()
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // 입력 하기 전
            }
        })
        searchTagRVAdapter = SearchColorTagRVAdapter(searchKeyword)
        binding.matchColorSearchButtonResultRecyclerview.adapter = searchTagRVAdapter
    }

    fun searchViewClick() {
        binding.matchColorSearchBeforeCl.visibility = View.INVISIBLE
        binding.matchColorSearchAfterCl.visibility = View.VISIBLE
        binding.matchColorSearchDefault.visibility = View.VISIBLE
        binding.matchColorSearchResult.visibility = View.INVISIBLE
    }

    fun backViewClick(){
        binding.matchColorSearchEt.setText("")
        binding.matchColorSearchBeforeCl.visibility = View.VISIBLE
        binding.matchColorSearchAfterCl.visibility = View.INVISIBLE
        binding.matchColorSearchDefault.visibility = View.INVISIBLE
        binding.matchColorSearchResult.visibility = View.INVISIBLE

        searchKeyword.clear()
        searchTagRVAdapter = SearchColorTagRVAdapter(searchKeyword)
        binding.matchColorSearchButtonResultRecyclerview.adapter = searchTagRVAdapter
        searchTagRVAdapter.notifyDataSetChanged()

        binding.matchColorSearchEt.visibility = View.VISIBLE
    }

    fun deleteButtonClick(){
        binding.matchColorSearchEt.setText("")
        binding.matchColorSearchBeforeCl.visibility = View.INVISIBLE
        binding.matchColorSearchAfterCl.visibility = View.VISIBLE
        binding.matchColorSearchDefault.visibility = View.VISIBLE
        binding.matchColorSearchResult.visibility = View.INVISIBLE

        searchKeyword.clear()
        searchTagRVAdapter = SearchColorTagRVAdapter(searchKeyword)
        binding.matchColorSearchButtonResultRecyclerview.adapter = searchTagRVAdapter
        searchTagRVAdapter.notifyDataSetChanged()

        binding.matchColorSearchEt.visibility = View.VISIBLE
    }

    fun searchButtonClick(){
        binding.matchColorSearchBeforeCl.visibility = View.INVISIBLE
        binding.matchColorSearchAfterCl.visibility = View.VISIBLE
        binding.matchColorSearchDefault.visibility = View.INVISIBLE
        binding.matchColorSearchResult.visibility = View.VISIBLE
    }

    fun latestButtonClick(){

    }

    fun historyView(){
        binding.matchColorLastTv.visibility = View.VISIBLE
        binding.matchAllDeleteTv.visibility = View.VISIBLE
        binding.matchColorLastFl.visibility = View.VISIBLE
    }

    fun historyUnView(){
        binding.matchColorLastTv.visibility = View.GONE
        binding.matchAllDeleteTv.visibility = View.GONE
        binding.matchColorLastFl.visibility = View.GONE
    }



    // API 이벤트

    private fun getLastTag(){
        MatchService.getLastTag(this, 3)
    }

    override fun onLastTagLoading() {}

    override fun onLastTagSuccess(lastTag: ArrayList<LastTag>) {
        reallastTag.clear()
        reallastTag = lastTag
        if (reallastTag.isEmpty() == false) {
            historyView()
            val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.HORIZONTAL)
            lastTagRVAdapter = MatchButtonRVAdapter()
            binding.matchColorLastRecyclerview.adapter = lastTagRVAdapter
            binding.matchColorLastRecyclerview.layoutManager = staggeredGridLayoutManager
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

    fun removeAllTag(){
        deleteAllTag()
        reallastTag.clear()
        lastTagRVAdapter.notifyDataSetChanged()
    }

    private fun getContent(content : String) : Content {
        return Content(content)
    }

    private fun deleteAllTag(){
        MatchService.deleteTag(this, 3, 2, getContent("야호"))
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
        var count : Int = 0
        searchKeyword.apply{
            add(LastTag(lastTag.text,lastTag.color))
        }
        searchTagRVAdapter = SearchColorTagRVAdapter(searchKeyword)
        binding.matchColorSearchButtonResultRecyclerview.adapter = searchTagRVAdapter
        searchTagRVAdapter.notifyDataSetChanged()
        searchViewClick()
        binding.matchColorSearchEt.setText("")
        if(searchKeyword.size > 1){
            binding.matchColorSearchEt.visibility = View.GONE
        }
    }

    private fun suggestTag(text : String) {
        MatchService.suggestTag(this,3, text)
    }

    override fun onSuggestTagLoading() {
    }

    override fun onSuggestTagSuccess(suggestion: ArrayList<Suggest>?) {
        if (suggestion != null) {
            for (i in suggestion){
                suggestTag.apply{
                    add(LastTag(i.smallclass, isdefault = true))
                }
            }
            val flexboxLayoutManager2 = FlexboxLayoutManager(this)
            suggestTagRVAdapter = MatchColorButtonRVAdapter()
            binding.matchColorSearchButtonRecyclerview.adapter = suggestTagRVAdapter
            binding.matchColorSearchButtonRecyclerview.layoutManager = flexboxLayoutManager2
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
        var keyword1 : String = ""
        var keyword2 : String = ""
        var color1 : String = ""
        var color2 : String = ""
        var count : Int = 1
        for(i in searchKeyword){
            if(count == 1){
                keyword1 = i.text.toString()
                color1 = i.color.toString()
                count = count + 1
            }
            else if (count == 2){
                keyword2 = i.text.toString()
                color2 = i.color.toString()
                count = count + 1
            }
        }
        MatchService.getMatch(this, 3, keyword1, keyword2, color1, color2, "", "")
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
        binding.matchColorSearchResultRv.adapter = diaryRVAdapter

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
            2000, 2001, 2002 -> {
                Log.d("Match/JWT/ERROR", "error")
            }
            3101, 3048, 3036 -> {
                Log.d("Match/PWWC/ERROR", "error")
            }
            3038, 3113, -> {
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


    private fun openColorUI(){
        val intent = Intent(this, ColorSearchActivity::class.java)
        startActivityForResult(intent,0)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var count : Int = 0
        if (resultCode == RESULT_OK) {
            var matchClothes = data?.getParcelableArrayListExtra<MatchClothes>("matchClothes")!!
            for(i in matchClothes){
                searchKeyword.apply{
                    add(LastTag(i.name,i.color))
                }
            }
            Log.d("searchKeyword","${searchKeyword}")
            searchTagRVAdapter = SearchColorTagRVAdapter(searchKeyword)
            binding.matchColorSearchButtonResultRecyclerview.adapter = searchTagRVAdapter
            searchTagRVAdapter.notifyDataSetChanged()
            searchViewClick()
            binding.matchColorSearchEt.setText("")
            if(searchKeyword.size > 1){
                binding.matchColorSearchEt.visibility = View.GONE
            }
        }


    }

    override fun openDialog(content : String) {
        Log.d("content","${content}")
        saveContent(content)
        bottomSheet.show()
    }


    override fun onPostColor(color: String) {
        suggestContent = getContent()
        searchKeyword.apply{
            add(LastTag(suggestContent,color))
        }
        Log.d("searchKeyword","${searchKeyword}")
        searchTagRVAdapter = SearchColorTagRVAdapter(searchKeyword)
        binding.matchColorSearchButtonResultRecyclerview.adapter = searchTagRVAdapter
        searchTagRVAdapter.notifyDataSetChanged()
        searchViewClick()
        binding.matchColorSearchEt.setText("")
        if(searchKeyword.size > 1){
            binding.matchColorSearchEt.visibility = View.GONE
        }
    }


}