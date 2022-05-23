package com.eight.collection.ui.main.match.who

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.EditText
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.eight.collection.R
import com.eight.collection.data.entities.Diary
import com.eight.collection.data.entities.Suggest
import com.eight.collection.data.entities.Write.Content
import com.eight.collection.data.remote.match.MatchService
import com.eight.collection.databinding.ActivityMatchWhoBinding
import com.eight.collection.ui.BaseActivity
import com.eight.collection.ui.main.match.*
import com.eight.collection.ui.main.week.DiaryRVAdapter
import com.eight.collection.utils.savePWWC
import com.google.android.flexbox.FlexboxLayoutManager
import kotlin.collections.ArrayList

class WhoActivity: BaseActivity<ActivityMatchWhoBinding>(ActivityMatchWhoBinding::inflate), MatchView, LastTagView,
    DeleteTagView, MatchButtonRVAdapter.MyitemClickListener, SuggestTagView {

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
    private var suggestResult : Boolean = false

    override fun initAfterBinding() {
        savePWWC(2)
        // 검색창 눌렀을시 이벤트
        binding.matchWhoSearchBeforeView.setOnClickListener{
            searchViewClick()
        }

        // 모두삭제 눌렀을시 이벤트
        binding.matchAllDeleteTv.setOnClickListener{
            removeAllTag()
        }

        // 돌아가기 버튼 눌렀을시 이벤트
        binding.matchWhoSearchBackIc.setOnClickListener{
            backViewClick()
        }

        // 검색창 x버튼 눌렀을시 이벤트
        binding.matchWhoSearchDeleteIc.setOnClickListener{
            deleteButtonClick()
        }

        // 검색 버튼 눌렀을시 이벤트
        binding.matchWhoSearchBt.setOnClickListener{
            searchButtonClick()
            getSearchResult()
            getLastTag()
        }

        //최신순 버튼 눌렀을시 이벤트
        binding.matchWhoSearchRecentRl.setOnClickListener{
            latestButtonClick()
        }

        getLastTag()

        defaultTag.apply {
            add(LastTag("친구", "",1,true))
            add(LastTag("가족", "",2,true))
            add(LastTag("동료", "",3,true))
            add(LastTag("선생님", "",4,true))
            add(LastTag("애인", "",5,true))
            add(LastTag("혼자", "",6,true))
        }

        val flexboxLayoutManager = FlexboxLayoutManager(this)
        matchButtonRVAdapter = MatchButtonRVAdapter()
        binding.matchWhoDefaultRecyclerview.adapter = matchButtonRVAdapter
        binding.matchWhoDefaultRecyclerview.layoutManager = flexboxLayoutManager
        matchButtonRVAdapter.addButton(defaultTag)

        // 태그 버튼 Click 시
        matchButtonRVAdapter.setMyItemClickListener(this)

        // 검색 EditText 이용 시
        searchEditText = findViewById(R.id.match_who_search_et)

        val flexboxLayoutManager2 = FlexboxLayoutManager(this)
        suggestTagRVAdapter = MatchButtonRVAdapter()
        binding.matchWhoSearchButtonRecyclerview.adapter = suggestTagRVAdapter
        binding.matchWhoSearchButtonRecyclerview.layoutManager = flexboxLayoutManager2

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
                    binding.matchWhoSearchIv.visibility = View.GONE
                    binding.matchWhoSearchTv.visibility = View.GONE
                    binding.matchWhoSearchButtonLayout.visibility = View.VISIBLE
                    if(suggestResult == false) {
                        suggestTag.clear()
                        suggestTagRVAdapter = MatchButtonRVAdapter()
                        binding.matchWhoSearchButtonRecyclerview.adapter = suggestTagRVAdapter
                    }
                }
                else {
                    binding.matchWhoSearchIv.visibility = View.VISIBLE
                    binding.matchWhoSearchTv.visibility = View.VISIBLE
                    binding.matchWhoSearchButtonLayout.visibility = View.GONE
                    suggestTag.clear()
                    suggestTagRVAdapter.notifyDataSetChanged()
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // 입력 하기 전
            }
        })
    }




    // 디자인 이벤트
    fun searchViewClick() {
        binding.matchWhoSearchBeforeCl.visibility = View.INVISIBLE
        binding.matchWhoSearchAfterCl.visibility = View.VISIBLE
        binding.matchWhoSearchDefault.visibility = View.VISIBLE
        binding.matchWhoSearchResult.visibility = View.INVISIBLE
    }

    fun backViewClick(){
        binding.matchWhoSearchEt.setText("")
        binding.matchWhoSearchBeforeCl.visibility = View.VISIBLE
        binding.matchWhoSearchAfterCl.visibility = View.INVISIBLE
        binding.matchWhoSearchDefault.visibility = View.INVISIBLE
        binding.matchWhoSearchResult.visibility = View.INVISIBLE

        searchKeyword.clear()
        searchTagRVAdapter = SearchTagRVAdapter(searchKeyword)
        binding.matchWhoSearchButtonResultRecyclerview.adapter = searchTagRVAdapter
        searchTagRVAdapter.notifyDataSetChanged()
    }

    fun deleteButtonClick(){
        binding.matchWhoSearchEt.setText("")
        binding.matchWhoSearchBeforeCl.visibility = View.INVISIBLE
        binding.matchWhoSearchAfterCl.visibility = View.VISIBLE
        binding.matchWhoSearchDefault.visibility = View.VISIBLE
        binding.matchWhoSearchResult.visibility = View.INVISIBLE

        searchKeyword.clear()
        searchTagRVAdapter = SearchTagRVAdapter(searchKeyword)
        binding.matchWhoSearchButtonResultRecyclerview.adapter = searchTagRVAdapter
        searchTagRVAdapter.notifyDataSetChanged()
    }

    fun searchButtonClick(){
        binding.matchWhoSearchBeforeCl.visibility = View.INVISIBLE
        binding.matchWhoSearchAfterCl.visibility = View.VISIBLE
        binding.matchWhoSearchDefault.visibility = View.INVISIBLE
        binding.matchWhoSearchResult.visibility = View.VISIBLE
    }

    fun latestButtonClick(){

    }

    fun historyView(){
        binding.matchWhoLastTv.visibility = View.VISIBLE
        binding.matchAllDeleteTv.visibility = View.VISIBLE
        binding.matchWhoLastFl.visibility = View.VISIBLE
    }

    fun historyUnView(){
        binding.matchWhoLastTv.visibility = View.GONE
        binding.matchAllDeleteTv.visibility = View.GONE
        binding.matchWhoLastFl.visibility = View.GONE
    }







    // API 이벤트

    private fun getLastTag(){
        MatchService.getLastTag(this, 2)
    }

    override fun onLastTagLoading() {}

    override fun onLastTagSuccess(lastTag: ArrayList<LastTag>) {
        reallastTag.clear()
        reallastTag = lastTag
        if (reallastTag.isEmpty() == false){
            historyView()
            val staggeredGridLayoutManager = StaggeredGridLayoutManager(2,LinearLayoutManager.HORIZONTAL)
            lastTagRVAdapter = MatchButtonRVAdapter()
            binding.matchWhoLastRecyclerview.adapter = lastTagRVAdapter
            binding.matchWhoLastRecyclerview.layoutManager = staggeredGridLayoutManager
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
        MatchService.deleteTag(this, 1, 2, getContent("야호"))
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
        binding.matchWhoSearchButtonResultRecyclerview.adapter = searchTagRVAdapter
        searchTagRVAdapter.notifyDataSetChanged()
        searchViewClick()
        binding.matchWhoSearchEt.setText("")
    }

    private fun suggestTag(text : String) {
        MatchService.suggestTag(this,2,text)
    }

    override fun onSuggestTagLoading() {
    }

    override fun onSuggestTagSuccess(suggestion: ArrayList<Suggest>?) {
        if (suggestion != null) {
            for (i in suggestion){
                suggestTag.apply{
                    add(LastTag(i.who, isdefault = true))
                }
            }
            val flexboxLayoutManager2 = FlexboxLayoutManager(this)
            suggestTagRVAdapter = MatchButtonRVAdapter()
            binding.matchWhoSearchButtonRecyclerview.adapter = suggestTagRVAdapter
            binding.matchWhoSearchButtonRecyclerview.layoutManager = flexboxLayoutManager2
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
        MatchService.getMatch(this, 2, keyword1, keyword2, "", "", "", "")
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

}