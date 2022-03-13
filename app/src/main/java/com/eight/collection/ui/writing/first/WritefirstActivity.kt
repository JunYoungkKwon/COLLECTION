package com.eight.collection.ui.writing.first

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.InputType
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.R
import com.eight.collection.data.entities.Cloth
import com.eight.collection.data.remote.getaddedblock.GetAddedBlockService
import com.eight.collection.data.remote.modi.ModiResult
import com.eight.collection.data.remote.modi.ModiService
import com.eight.collection.databinding.ActivityWritefirstBinding
import com.eight.collection.ui.introduce.IntroduceFirstDialog
import com.eight.collection.ui.writing.CustomDialogInterface
import com.eight.collection.ui.writing.ModiView
import com.eight.collection.ui.writing.RefreshDialogInterface
import com.eight.collection.ui.writing.first.bottom.WritefirstBottomFragment
import com.eight.collection.ui.writing.first.etc.WritefirstEtcFragment
import com.eight.collection.ui.writing.first.shoes.WritefirstShoesFragment
import com.eight.collection.ui.writing.first.top.WritefirstTopFragment
import com.eight.collection.ui.writing.second.WritesecondActivity
import com.google.android.material.tabs.TabLayoutMediator
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.collections.ArrayList

class WritefirstActivity() : AppCompatActivity(), RefreshDialogInterface, ModiView {
    lateinit var binding: ActivityWritefirstBinding
    lateinit var refreshDialog : RefreshDialog
    val photoList = ArrayList<Uri>()
    val imageList = ArrayList<Image>()
    var reviseimageList = ArrayList<Image>()
    val photoRVAdapter = PhotoRVAdapter(photoList, this)
    val fragmentList = arrayListOf<Fragment>()
    val information = arrayListOf("TOP", "BOTTOM", "SHOES", "ETC")
    private val GALLERY = 1
    private var topclickListener: WritefirstActivity.TopColorClickListener? = null
    private var bottomclickListener : WritefirstActivity.BottomColorClickListener? = null
    private var shoesclickListener : WritefirstActivity.ShoesColorClickListener? = null
    private var etcclickListener : WritefirstActivity.EtcColorClickListener? = null
    private var gettopdataListener : WritefirstActivity.GetTopDataListener? = null
    private var getbottomdataListener : WritefirstActivity.GetBottomDataListener? = null
    private var getshoesdataListener : WritefirstActivity.GetShoesDataListener? = null
    private var getetcdataListener : WritefirstActivity.GetEtcDataListener? = null
    private var refreshtopdataListener : WritefirstActivity.RefreshTopDataListener? = null
    private var refreshbottomdataListener : WritefirstActivity.RefreshBottomDataListener? = null
    private var refreshshoesdataListener : WritefirstActivity.RefreshShoesDataListener? = null
    private var refreshetcdataListener : WritefirstActivity.RefreshEtcDataListener? = null


    var photoIs : Int = -1
    var mode : Int = 1
    var modidate : String? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWritefirstBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //날짜 데이터 삽입
        var date = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
        val formatted = date.format(formatter)

        val formatterpost = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formattedpost = date.format(formatterpost)

        binding.writefirstDateTv.text = formatted


        //(수정하기시) 날짜 데이터 삽입
        var getDate = intent.getStringExtra("date")
        if(getDate != null){
            val date2 = LocalDate.parse(getDate, DateTimeFormatter.ISO_DATE)
            val formatted2 = date2.format(formatter)
            binding.writefirstDateTv.text = formatted2
            mode = 2
            modidate = getDate
            modi()
        }


        //초기화 기능 구현
        refreshDialog = RefreshDialog(this, this)
        binding.writefirstRefreshIv.setOnClickListener{
            refreshDialog.show()
        }

        //EditText 자동완성 끄기
        binding.writefirstLookstyleTv.setRawInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS)


        //이미지 리사이클러뷰 및 갤러리에서 이미지 불러오기
        var getImage_btn = findViewById<ImageView>(R.id.writefirst_add_photo_iv)
        var recyclerview = findViewById<RecyclerView>(R.id.writefirst_photo_recyclerview)

        getImage_btn.setOnClickListener{
            var intent = Intent(Intent.ACTION_PICK)
            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.type = "image/*"
            intent.action = Intent.ACTION_OPEN_DOCUMENT

            startActivityForResult(intent, GALLERY)
        }

        recyclerview.layoutManager =
            LinearLayoutManager(baseContext, LinearLayoutManager.HORIZONTAL, false)
        recyclerview.adapter = photoRVAdapter



        //Top,Bottom,Shoes,Etc 뷰페이저 연결
        fragmentList.apply {
            add(WritefirstTopFragment())
            add(WritefirstBottomFragment())
            add(WritefirstShoesFragment())
            add(WritefirstEtcFragment())
        }

        val writefirstAdapter = WritefirstVPA(this, fragmentList)
        binding.writefirstColorVp.adapter = writefirstAdapter
        TabLayoutMediator(binding.writefirstColorTb, binding.writefirstColorVp) { tab, position ->
            tab.text = information[position]
        }.attach()


        setTopColorClickListener((fragmentList[0] as WritefirstTopFragment))
        setBottomColorClickListener((fragmentList[1] as WritefirstBottomFragment))
        setShoesColorClickListener((fragmentList[2] as WritefirstShoesFragment))
        setEtcColorClickListener((fragmentList[3] as WritefirstEtcFragment))

        setGetTopDataClickListener((fragmentList[0] as WritefirstTopFragment))
        setGetBottomDataClickListener(fragmentList[1] as WritefirstBottomFragment)
        setGetShoesDataClickListener(fragmentList[2] as WritefirstShoesFragment)
        setGetEtcDataClickListener(fragmentList[3] as WritefirstEtcFragment)

        setRefreshTopDataClickListener((fragmentList[0] as WritefirstTopFragment))
        setRefreshBottomDataClickListener(fragmentList[1] as WritefirstBottomFragment)
        setRefreshShoesDataClickListener(fragmentList[2] as WritefirstShoesFragment)
        setRefreshEtcDataClickListener(fragmentList[3] as WritefirstEtcFragment)




        //Color블록 클릭시 데이터 전달
        binding.writefirstColorTopSelectorRed.setOnClickListener{
            topclickListener?.topChangeButtonColor("#d60f0f")
            bottomclickListener?.bottomChangeButtonColor("#d60f0f")
            shoesclickListener?.shoesChangeButtonColor("#d60f0f")
            etcclickListener?.etcChangeButtonColor("#d60f0f")
        }

        binding.writefirstColorTopSelectorPink.setOnClickListener{
            topclickListener?.topChangeButtonColor("#f59a9a")
            bottomclickListener?.bottomChangeButtonColor("#f59a9a")
            shoesclickListener?.shoesChangeButtonColor("#f59a9a")
            etcclickListener?.etcChangeButtonColor("#f59a9a")
        }

        binding.writefirstColorTopSelectorYellow.setOnClickListener{
            topclickListener?.topChangeButtonColor("#ffb203")
            bottomclickListener?.bottomChangeButtonColor("#ffb203")
            shoesclickListener?.shoesChangeButtonColor("#ffb203")
            etcclickListener?.etcChangeButtonColor("#ffb203")
        }

        binding.writefirstColorTopSelectorLightyellow.setOnClickListener{
            topclickListener?.topChangeButtonColor("#fde6b1")
            bottomclickListener?.bottomChangeButtonColor("#fde6b1")
            shoesclickListener?.shoesChangeButtonColor("#fde6b1")
            etcclickListener?.etcChangeButtonColor("#fde6b1")
        }

        binding.writefirstColorTopSelectorGreen.setOnClickListener{
            topclickListener?.topChangeButtonColor("#71a238")
            bottomclickListener?.bottomChangeButtonColor("#71a238")
            shoesclickListener?.shoesChangeButtonColor("#71a238")
            etcclickListener?.etcChangeButtonColor("#71a238")
        }

        binding.writefirstColorTopSelectorLightgreen.setOnClickListener{
            topclickListener?.topChangeButtonColor("#b7de89")
            bottomclickListener?.bottomChangeButtonColor("#b7de89")
            shoesclickListener?.shoesChangeButtonColor("#b7de89")
            etcclickListener?.etcChangeButtonColor("#b7de89")
        }

        binding.writefirstColorTopSelectorOrange.setOnClickListener{
            topclickListener?.topChangeButtonColor("#ea7831")
            bottomclickListener?.bottomChangeButtonColor("#ea7831")
            shoesclickListener?.shoesChangeButtonColor("#ea7831")
            etcclickListener?.etcChangeButtonColor("#ea7831")
        }

        binding.writefirstColorTopSelectorNavy.setOnClickListener{
            topclickListener?.topChangeButtonColor("#273e88")
            bottomclickListener?.bottomChangeButtonColor("#273e88")
            shoesclickListener?.shoesChangeButtonColor("#273e88")
            etcclickListener?.etcChangeButtonColor("#273e88")
        }

        binding.writefirstColorTopSelectorBlue.setOnClickListener{
            topclickListener?.topChangeButtonColor("#4168e8")
            bottomclickListener?.bottomChangeButtonColor("#4168e8")
            shoesclickListener?.shoesChangeButtonColor("#4168e8")
            etcclickListener?.etcChangeButtonColor("#4168e8")
        }

        binding.writefirstColorTopSelectorLightblue.setOnClickListener{
            topclickListener?.topChangeButtonColor("#a5b9fa")
            bottomclickListener?.bottomChangeButtonColor("#a5b9fa")
            shoesclickListener?.shoesChangeButtonColor("#a5b9fa")
            etcclickListener?.etcChangeButtonColor("#a5b9fa")
        }

        binding.writefirstColorTopSelectorPurple.setOnClickListener{
            topclickListener?.topChangeButtonColor("#894ac7")
            bottomclickListener?.bottomChangeButtonColor("#894ac7")
            shoesclickListener?.shoesChangeButtonColor("#894ac7")
            etcclickListener?.etcChangeButtonColor("#894ac7")
        }

        binding.writefirstColorTopSelectorLightpurple.setOnClickListener{
            topclickListener?.topChangeButtonColor("#dcacff")
            bottomclickListener?.bottomChangeButtonColor("#dcacff")
            shoesclickListener?.shoesChangeButtonColor("#dcacff")
            etcclickListener?.etcChangeButtonColor("#dcacff")
        }

        binding.writefirstColorTopSelectorWhite.setOnClickListener{
            topclickListener?.topChangeButtonColor("#ffffff")
            bottomclickListener?.bottomChangeButtonColor("#ffffff")
            shoesclickListener?.shoesChangeButtonColor("#ffffff")
            etcclickListener?.etcChangeButtonColor("#ffffff")
        }

        binding.writefirstColorTopSelectorGrey.setOnClickListener{
            topclickListener?.topChangeButtonColor("#888888")
            bottomclickListener?.bottomChangeButtonColor("#888888")
            shoesclickListener?.shoesChangeButtonColor("#888888")
            etcclickListener?.etcChangeButtonColor("#888888")
        }

        binding.writefirstColorTopSelectorBlack.setOnClickListener{
            topclickListener?.topChangeButtonColor("#191919")
            bottomclickListener?.bottomChangeButtonColor("#191919")
            shoesclickListener?.shoesChangeButtonColor("#191919")
            etcclickListener?.etcChangeButtonColor("#191919")
        }

        binding.writefirstColorTopSelectorLightpeach.setOnClickListener{
            topclickListener?.topChangeButtonColor("#e8dcd5")
            bottomclickListener?.bottomChangeButtonColor("#e8dcd5")
            shoesclickListener?.shoesChangeButtonColor("#e8dcd5")
            etcclickListener?.etcChangeButtonColor("#e8dcd5")
        }

        binding.writefirstColorTopSelectorPinkishgrey.setOnClickListener{
            topclickListener?.topChangeButtonColor("#c3b5ac")
            bottomclickListener?.bottomChangeButtonColor("#c3b5ac")
            shoesclickListener?.shoesChangeButtonColor("#c3b5ac")
            etcclickListener?.etcChangeButtonColor("#c3b5ac")
        }

        binding.writefirstColorTopSelectorBrown.setOnClickListener{
            topclickListener?.topChangeButtonColor("#74461f")
            bottomclickListener?.bottomChangeButtonColor("#74461f")
            shoesclickListener?.shoesChangeButtonColor("#74461f")
            etcclickListener?.etcChangeButtonColor("#74461f")
        }



        //다음버튼 클릭시 Writing Second Activity
        binding.writefirstNextButton.setOnClickListener {
            // Clothes 필수선택
            val fixedClothes : ArrayList<FixedClothes> = ArrayList()
            val addedClothes : ArrayList<AddedClothes> = ArrayList()
            fixedClothes.addAll(gettopdataListener!!.getFixedData())
            fixedClothes.addAll(getbottomdataListener!!.getFixedData())
            fixedClothes.addAll(getshoesdataListener!!.getFixedData())
            fixedClothes.addAll(getetcdataListener!!.getFixedData())
            addedClothes.addAll(gettopdataListener!!.getAddedData())
            addedClothes.addAll(getbottomdataListener!!.getAddedData())
            addedClothes.addAll(getshoesdataListener!!.getAddedData())
            addedClothes.addAll(getetcdataListener!!.getAddedData())

            if(fixedClothes.isEmpty() == true && addedClothes.isEmpty() == true ){
                var layoutInflater = LayoutInflater.from(this).inflate(R.layout.toast_custom,null)
                var text : TextView = layoutInflater.findViewById(R.id.toast_text_tv)
                text.text = "옷을 한 개 이상 선택해주세요."
                var toast = Toast(this)
                toast.view = layoutInflater
                toast.setGravity(Gravity.BOTTOM, 0, 270)
                toast.show()
            }

            else if(binding.writefirstLookstyleTv.text.toString().isEmpty() == true){
                var layoutInflater = LayoutInflater.from(this).inflate(R.layout.toast_custom,null)
                var text : TextView = layoutInflater.findViewById(R.id.toast_text_tv)
                text.text = "LOOK NAME을 입력해주세요."
                var toast = Toast(this)
                toast.view = layoutInflater
                toast.setGravity(Gravity.BOTTOM, 0, 270)
                toast.show()
                binding.writefirstLookstyleTv.setHintTextColor(Color.parseColor("red"))
            }

            else {
                val intent = Intent(this, WritesecondActivity::class.java)
                intent.putExtra("lookname", binding.writefirstLookstyleTv.text.toString())
                intent.putExtra("photoIs", photoIs)
                intent.putExtra("fixed", fixedClothes)
                intent.putExtra("added", addedClothes)

                if(mode == 2){
                    intent.putExtra("image", reviseimageList)
                    intent.putExtra("date", getDate)
                    intent.putExtra("mode", mode)
                }
                else {
                    intent.putExtra("image", imageList)
                    intent.putExtra("date", formattedpost)
                    intent.putExtra("mode", mode)
                }

                startActivity(intent)
                finish()
            }
        }
    }


    //갤러리에서 이미지 선택 메소드
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == GALLERY) {
            photoList.clear()
            imageList.clear()
            if (data?.clipData != null) {
                val count = data.clipData!!.itemCount
                if (count > 5) {
                    var layoutInflater = LayoutInflater.from(this).inflate(R.layout.toast_custom,null)
                    var text : TextView = layoutInflater.findViewById(R.id.toast_text_tv)
                    text.text = "사진은 5장까지 선택 가능합니다."
                    var toast = Toast(this)
                    toast.view = layoutInflater
                    toast.setGravity(Gravity.BOTTOM, 0, 270)
                    toast.show()
                    return
                }

                for (i in 0 until count){
                    val imageUri = data.clipData!!.getItemAt(i).uri
                    photoList.add(imageUri)
                    binding.writefirstPhotoDefaultImage1.visibility = View.GONE
                    binding.writefirstPhotoDefaultImage2.visibility = View.GONE
                }

            }
            else {
                data?.data?.let { uri ->
                    val imageUri : Uri? = data?.data
                    if (imageUri != null){
                        photoList.add(imageUri)
                        binding.writefirstPhotoDefaultImage1.visibility = View.GONE
                        binding.writefirstPhotoDefaultImage2.visibility = View.GONE
                    }
                }
            }
            photoIs = 0
            photoRVAdapter.notifyDataSetChanged()
            var b : Int = 0
            for(a in photoList) {
                contentResolver.takePersistableUriPermission(a,Intent.FLAG_GRANT_READ_URI_PERMISSION)
                var c : String = a.toString()
                imageList.apply {
                    add(Image(c, b))
                }
                b=-1
            }
            reviseimageList = imageList
        }
    }



    //Color 버튼 Interface 작성 및 리스너 연결
    interface TopColorClickListener {
        fun topChangeButtonColor(color : String)
    }

    fun setTopColorClickListener(topcolorClickListener: WritefirstTopFragment) {
        this.topclickListener = topcolorClickListener
    }

    interface BottomColorClickListener {
        fun bottomChangeButtonColor(color : String)
    }

    fun setBottomColorClickListener(bottomcolorClickListener: WritefirstBottomFragment) {
        this.bottomclickListener = bottomcolorClickListener
    }

    interface ShoesColorClickListener {
        fun shoesChangeButtonColor(color : String)
    }

    fun setShoesColorClickListener(shoesColorClickListener: WritefirstShoesFragment) {
        this.shoesclickListener = shoesColorClickListener
    }

    interface EtcColorClickListener {
        fun etcChangeButtonColor(color : String)
    }

    fun setEtcColorClickListener(etcColorClickListener: WritefirstEtcFragment) {
        this.etcclickListener = etcColorClickListener
    }

    interface GetTopDataListener {
        fun getFixedData() : ArrayList<FixedClothes>
        fun getAddedData() : ArrayList<AddedClothes>
    }

    interface GetBottomDataListener {
        fun getFixedData() : ArrayList<FixedClothes>
        fun getAddedData() : ArrayList<AddedClothes>
    }

    interface GetShoesDataListener {
        fun getFixedData() : ArrayList<FixedClothes>
        fun getAddedData() : ArrayList<AddedClothes>
    }

    interface GetEtcDataListener {
        fun getFixedData() : ArrayList<FixedClothes>
        fun getAddedData() : ArrayList<AddedClothes>
    }

    fun setGetTopDataClickListener(getTopDataListener : WritefirstTopFragment){
        this.gettopdataListener = getTopDataListener
    }

    fun setGetBottomDataClickListener(getBottomDataListener : WritefirstBottomFragment){
        this.getbottomdataListener = getBottomDataListener
    }

    fun setGetShoesDataClickListener(getShoesDataListener : WritefirstShoesFragment){
        this.getshoesdataListener = getShoesDataListener
    }

    fun setGetEtcDataClickListener(getEtcDataListener : WritefirstEtcFragment){
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

    fun setRefreshTopDataClickListener(refreshTopDataListener : WritefirstTopFragment){
        this.refreshtopdataListener = refreshTopDataListener
    }

    fun setRefreshBottomDataClickListener(refreshBottomDataListener : WritefirstBottomFragment){
        this.refreshbottomdataListener = refreshBottomDataListener
    }

    fun setRefreshShoesDataClickListener(refreshShoesDataListener : WritefirstShoesFragment){
        this.refreshshoesdataListener = refreshShoesDataListener
    }

    fun setRefreshEtcDataClickListener(refreshEtcDataListener : WritefirstEtcFragment){
        this.refreshetcdataListener = refreshEtcDataListener
    }



    override fun onOkButtonClicked() {
        photoIs = -1
        photoList.clear()
        imageList.clear()
        photoRVAdapter.notifyDataSetChanged()
        binding.writefirstPhotoDefaultImage1.visibility = View.VISIBLE
        binding.writefirstPhotoDefaultImage2.visibility = View.VISIBLE
        binding.writefirstLookstyleTv.setText("")
        //블럭 색 초기화
        refreshtopdataListener?.refreshData()
        refreshbottomdataListener?.refreshData()
        refreshshoesdataListener?.refreshData()
        refreshetcdataListener?.refreshData()


    }
    override fun onCancelButtonClicked() {
    }


    //수정하기시 블럭 색 셋팅해놓기
    private fun modi(){
        ModiService.modi(this, modidate!!)
    }
    override fun onModiLoading() {
    }
    override fun onModiSuccess(modiresult: ModiResult) {
        Log.d("modiresult","${modiresult}")
        //수정하기시, 기존 LookName 불러오기
        if(modiresult.selected?.lookname != null){
            binding.writefirstLookstyleTv.setText(modiresult.selected?.lookname)
        }
        //수정하기시, 기존 이미지 불러오기
        if(modiresult.selected?.image.isNullOrEmpty() == false){
            for(i in modiresult.selected?.image!!){
                photoList.apply {
                    add(i.imageurl!!.toUri())
                }
            }
            photoRVAdapter.notifyDataSetChanged()
            photoIs = 0
            binding.writefirstPhotoDefaultImage1.visibility = View.GONE
            binding.writefirstPhotoDefaultImage2.visibility = View.GONE
            var b : Int = 0
            for(a in photoList) {
                contentResolver.takePersistableUriPermission(a,Intent.FLAG_GRANT_READ_URI_PERMISSION)
                var c : String = a.toString()
                imageList.apply {
                    add(Image(c, b))
                }
                b=-1
            }
            reviseimageList = imageList
        }
        else {
            photoIs = -1
            photoList.clear()
            imageList.clear()
            photoRVAdapter.notifyDataSetChanged()
            binding.writefirstPhotoDefaultImage1.visibility = View.VISIBLE
            binding.writefirstPhotoDefaultImage2.visibility = View.VISIBLE
        }

    }
    override fun onModiFailure(code: Int, message: String) {
    }
}