package com.eight.collection.ui.main.match.color

import android.util.Log
import androidx.fragment.app.Fragment
import com.eight.collection.databinding.*
import com.eight.collection.ui.BaseActivity
import com.eight.collection.ui.main.match.color.Etc.ColorSearchEtcFragment
import com.eight.collection.ui.main.match.color.Shoes.ColorSearchShoesFragment
import com.eight.collection.ui.main.match.color.bottom.ColorSearchBottomFragment
import com.eight.collection.ui.main.match.color.top.ColorSearchTopFragment
import com.eight.collection.ui.writing.RefreshDialogInterface
import com.eight.collection.ui.writing.first.*
import com.google.android.material.tabs.TabLayoutMediator

class ColorSearchActivity(): BaseActivity<ActivityMatchColorSearchBinding>(ActivityMatchColorSearchBinding::inflate),
    RefreshDialogInterface {

    lateinit var refreshDialog : RefreshDialog

    private var refreshtopdataListener : ColorSearchActivity.RefreshTopDataListener? = null
    private var refreshbottomdataListener : ColorSearchActivity.RefreshBottomDataListener? = null
    private var refreshshoesdataListener : ColorSearchActivity.RefreshShoesDataListener? = null
    private var refreshetcdataListener : ColorSearchActivity.RefreshEtcDataListener? = null


    val fragmentList = arrayListOf<Fragment>()
    val information = arrayListOf("TOP", "BOTTOM", "SHOES", "ETC")


    private var topclickListener: ColorSearchActivity.TopColorClickListener? = null
    private var bottomclickListener : ColorSearchActivity.BottomColorClickListener? = null
    private var shoesclickListener : ColorSearchActivity.ShoesColorClickListener? = null
    private var etcclickListener : ColorSearchActivity.EtcColorClickListener? = null


    private var gettopdataListener : ColorSearchActivity.GetTopDataListener? = null
    private var getbottomdataListener : ColorSearchActivity.GetBottomDataListener? = null
    private var getshoesdataListener : ColorSearchActivity.GetShoesDataListener? = null
    private var getetcdataListener : ColorSearchActivity.GetEtcDataListener? = null



    override fun initAfterBinding() {

        //초기화 기능 구현
        refreshDialog = RefreshDialog(this, this)
        binding.matchColorSearchRefreshTv.setOnClickListener{
            refreshDialog.show()
        }


        //Top,Bottom,Shoes,Etc 뷰페이저 연결
        fragmentList.apply {
            add(ColorSearchTopFragment())
            add(ColorSearchBottomFragment())
            add(ColorSearchShoesFragment())
            add(ColorSearchEtcFragment())
        }
        val writefirstAdapter = ColorSearchVPA(this, fragmentList)
        binding.matchColorSearchButtonVp.adapter = writefirstAdapter
        TabLayoutMediator(binding.matchColorSearchTb, binding.matchColorSearchButtonVp) { tab, position ->
            tab.text = information[position]
        }.attach()




        setTopColorClickListener((fragmentList[0] as ColorSearchTopFragment))
        setBottomColorClickListener((fragmentList[1] as ColorSearchBottomFragment))
        setShoesColorClickListener((fragmentList[2] as ColorSearchShoesFragment))
        setEtcColorClickListener((fragmentList[3] as ColorSearchEtcFragment))

        setGetTopDataClickListener((fragmentList[0] as ColorSearchTopFragment))
        setGetBottomDataClickListener(fragmentList[1] as ColorSearchBottomFragment)
        setGetShoesDataClickListener(fragmentList[2] as ColorSearchShoesFragment)
        setGetEtcDataClickListener(fragmentList[3] as ColorSearchEtcFragment)

        setRefreshTopDataClickListener((fragmentList[0] as ColorSearchTopFragment))
        setRefreshBottomDataClickListener(fragmentList[1] as ColorSearchBottomFragment)
        setRefreshShoesDataClickListener(fragmentList[2] as ColorSearchShoesFragment)
        setRefreshEtcDataClickListener(fragmentList[3] as ColorSearchEtcFragment)




        //Color블록 클릭시 데이터 전달
        binding.matchColorSearchPartSelectorRed.setOnClickListener{
            topclickListener?.topChangeButtonColor("#d60f0f")
            bottomclickListener?.bottomChangeButtonColor("#d60f0f")
            shoesclickListener?.shoesChangeButtonColor("#d60f0f")
            etcclickListener?.etcChangeButtonColor("#d60f0f")
        }

        binding.matchColorSearchPartSelectorPink.setOnClickListener{
            topclickListener?.topChangeButtonColor("#f59a9a")
            bottomclickListener?.bottomChangeButtonColor("#f59a9a")
            shoesclickListener?.shoesChangeButtonColor("#f59a9a")
            etcclickListener?.etcChangeButtonColor("#f59a9a")
        }

        binding.matchColorSearchPartSelectorYellow.setOnClickListener{
            topclickListener?.topChangeButtonColor("#ffb203")
            bottomclickListener?.bottomChangeButtonColor("#ffb203")
            shoesclickListener?.shoesChangeButtonColor("#ffb203")
            etcclickListener?.etcChangeButtonColor("#ffb203")
        }

        binding.matchColorSearchPartSelectorLightyellow.setOnClickListener{
            topclickListener?.topChangeButtonColor("#fde6b1")
            bottomclickListener?.bottomChangeButtonColor("#fde6b1")
            shoesclickListener?.shoesChangeButtonColor("#fde6b1")
            etcclickListener?.etcChangeButtonColor("#fde6b1")
        }

        binding.matchColorSearchPartSelectorGreen.setOnClickListener{
            topclickListener?.topChangeButtonColor("#71a238")
            bottomclickListener?.bottomChangeButtonColor("#71a238")
            shoesclickListener?.shoesChangeButtonColor("#71a238")
            etcclickListener?.etcChangeButtonColor("#71a238")
        }

        binding.matchColorSearchPartSelectorLightgreen.setOnClickListener{
            topclickListener?.topChangeButtonColor("#b7de89")
            bottomclickListener?.bottomChangeButtonColor("#b7de89")
            shoesclickListener?.shoesChangeButtonColor("#b7de89")
            etcclickListener?.etcChangeButtonColor("#b7de89")
        }

        binding.matchColorSearchPartSelectorOrange.setOnClickListener{
            topclickListener?.topChangeButtonColor("#ea7831")
            bottomclickListener?.bottomChangeButtonColor("#ea7831")
            shoesclickListener?.shoesChangeButtonColor("#ea7831")
            etcclickListener?.etcChangeButtonColor("#ea7831")
        }

        binding.matchColorSearchPartSelectorNavy.setOnClickListener{
            topclickListener?.topChangeButtonColor("#273e88")
            bottomclickListener?.bottomChangeButtonColor("#273e88")
            shoesclickListener?.shoesChangeButtonColor("#273e88")
            etcclickListener?.etcChangeButtonColor("#273e88")
        }

        binding.matchColorSearchPartSelectorBlue.setOnClickListener{
            topclickListener?.topChangeButtonColor("#4168e8")
            bottomclickListener?.bottomChangeButtonColor("#4168e8")
            shoesclickListener?.shoesChangeButtonColor("#4168e8")
            etcclickListener?.etcChangeButtonColor("#4168e8")
        }

        binding.matchColorSearchPartSelectorLightblue.setOnClickListener{
            topclickListener?.topChangeButtonColor("#a5b9fa")
            bottomclickListener?.bottomChangeButtonColor("#a5b9fa")
            shoesclickListener?.shoesChangeButtonColor("#a5b9fa")
            etcclickListener?.etcChangeButtonColor("#a5b9fa")
        }

        binding.matchColorSearchPartSelectorPurple.setOnClickListener{
            topclickListener?.topChangeButtonColor("#894ac7")
            bottomclickListener?.bottomChangeButtonColor("#894ac7")
            shoesclickListener?.shoesChangeButtonColor("#894ac7")
            etcclickListener?.etcChangeButtonColor("#894ac7")
        }

        binding.matchColorSearchPartSelectorLightpurple.setOnClickListener{
            topclickListener?.topChangeButtonColor("#dcacff")
            bottomclickListener?.bottomChangeButtonColor("#dcacff")
            shoesclickListener?.shoesChangeButtonColor("#dcacff")
            etcclickListener?.etcChangeButtonColor("#dcacff")
        }

        binding.matchColorSearchPartSelectorWhite.setOnClickListener{
            topclickListener?.topChangeButtonColor("#ffffff")
            bottomclickListener?.bottomChangeButtonColor("#ffffff")
            shoesclickListener?.shoesChangeButtonColor("#ffffff")
            etcclickListener?.etcChangeButtonColor("#ffffff")
        }

        binding.matchColorSearchPartSelectorGrey.setOnClickListener{
            topclickListener?.topChangeButtonColor("#888888")
            bottomclickListener?.bottomChangeButtonColor("#888888")
            shoesclickListener?.shoesChangeButtonColor("#888888")
            etcclickListener?.etcChangeButtonColor("#888888")
        }

        binding.matchColorSearchPartSelectorBlack.setOnClickListener{
            topclickListener?.topChangeButtonColor("#191919")
            bottomclickListener?.bottomChangeButtonColor("#191919")
            shoesclickListener?.shoesChangeButtonColor("#191919")
            etcclickListener?.etcChangeButtonColor("#191919")
        }

        binding.matchColorSearchPartSelectorLightpeach.setOnClickListener{
            topclickListener?.topChangeButtonColor("#e8dcd5")
            bottomclickListener?.bottomChangeButtonColor("#e8dcd5")
            shoesclickListener?.shoesChangeButtonColor("#e8dcd5")
            etcclickListener?.etcChangeButtonColor("#e8dcd5")
        }

        binding.matchColorSearchPartSelectorPinkishgrey.setOnClickListener{
            topclickListener?.topChangeButtonColor("#c3b5ac")
            bottomclickListener?.bottomChangeButtonColor("#c3b5ac")
            shoesclickListener?.shoesChangeButtonColor("#c3b5ac")
            etcclickListener?.etcChangeButtonColor("#c3b5ac")
        }

        binding.matchColorSearchPartSelectorBrown.setOnClickListener{
            topclickListener?.topChangeButtonColor("#74461f")
            bottomclickListener?.bottomChangeButtonColor("#74461f")
            shoesclickListener?.shoesChangeButtonColor("#74461f")
            etcclickListener?.etcChangeButtonColor("#74461f")
        }



        //검색 버튼 클릭시 Writing Second Activity
        binding.matchColorSearchBt.setOnClickListener {

            val matchClothes : ArrayList<MatchClothes> = ArrayList()
            matchClothes.addAll(gettopdataListener!!.getData())
            matchClothes.addAll(getbottomdataListener!!.getData())
            matchClothes.addAll(getshoesdataListener!!.getData())
            matchClothes.addAll(getetcdataListener!!.getData())


            // 데이터 전달

            intent.putExtra("matchClothes",matchClothes)
            setResult(RESULT_OK,intent)
            finish()
        }
    }





    override fun onOkButtonClicked() {
        //블럭 색 초기화
        refreshtopdataListener?.refreshData()
        refreshbottomdataListener?.refreshData()
        refreshshoesdataListener?.refreshData()
        refreshetcdataListener?.refreshData()
    }

    override fun onCancelButtonClicked() {
    }







    //Color 버튼 Interface 작성 및 리스너 연결
    interface TopColorClickListener {
        fun topChangeButtonColor(color : String)
    }

    fun setTopColorClickListener(topcolorClickListener: ColorSearchTopFragment) {
        this.topclickListener = topcolorClickListener
    }

    interface BottomColorClickListener {
        fun bottomChangeButtonColor(color : String)
    }

    fun setBottomColorClickListener(bottomcolorClickListener: ColorSearchBottomFragment) {
        this.bottomclickListener = bottomcolorClickListener
    }

    interface ShoesColorClickListener {
        fun shoesChangeButtonColor(color : String)
    }

    fun setShoesColorClickListener(shoesColorClickListener: ColorSearchShoesFragment) {
        this.shoesclickListener = shoesColorClickListener
    }

    interface EtcColorClickListener {
        fun etcChangeButtonColor(color : String)
    }

    fun setEtcColorClickListener(etcColorClickListener: ColorSearchEtcFragment) {
        this.etcclickListener = etcColorClickListener
    }






    interface GetTopDataListener {
        fun getData() : ArrayList<MatchClothes>
    }

    interface GetBottomDataListener {
        fun getData() : ArrayList<MatchClothes>
    }

    interface GetShoesDataListener {
        fun getData() : ArrayList<MatchClothes>
    }

    interface GetEtcDataListener {
        fun getData() : ArrayList<MatchClothes>
    }

    fun setGetTopDataClickListener(getTopDataListener : ColorSearchTopFragment){
        this.gettopdataListener = getTopDataListener
    }

    fun setGetBottomDataClickListener(getBottomDataListener : ColorSearchBottomFragment){
        this.getbottomdataListener = getBottomDataListener
    }

    fun setGetShoesDataClickListener(getShoesDataListener : ColorSearchShoesFragment){
        this.getshoesdataListener = getShoesDataListener
    }

    fun setGetEtcDataClickListener(getEtcDataListener : ColorSearchEtcFragment){
        this.getetcdataListener = getEtcDataListener
    }






    //색 초기화
    interface RefreshTopDataListener {
        fun refreshData()
    }

    interface RefreshBottomDataListener {
        fun refreshData()
    }

    interface RefreshShoesDataListener {
        fun refreshData()
    }

    interface RefreshEtcDataListener {
        fun refreshData()
    }

    fun setRefreshTopDataClickListener(refreshTopDataListener : ColorSearchTopFragment){
        this.refreshtopdataListener = refreshTopDataListener
    }

    fun setRefreshBottomDataClickListener(refreshBottomDataListener : ColorSearchBottomFragment){
        this.refreshbottomdataListener = refreshBottomDataListener
    }

    fun setRefreshShoesDataClickListener(refreshShoesDataListener : ColorSearchShoesFragment){
        this.refreshshoesdataListener = refreshShoesDataListener
    }

    fun setRefreshEtcDataClickListener(refreshEtcDataListener : ColorSearchEtcFragment){
        this.refreshetcdataListener = refreshEtcDataListener
    }



}