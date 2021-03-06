package com.eight.collection.ui.writing.first

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Insets.add
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
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.R
import com.eight.collection.data.remote.modi.ModiResult
import com.eight.collection.data.remote.modi.ModiService
import com.eight.collection.databinding.ActivityWritefirstBinding
import com.eight.collection.ui.writing.ModiView
import com.eight.collection.ui.writing.RefreshDialogInterface
import com.eight.collection.ui.writing.first.bottom.WritefirstBottomFragment
import com.eight.collection.ui.writing.first.etc.WritefirstEtcFragment
import com.eight.collection.ui.writing.first.shoes.WritefirstShoesFragment
import com.eight.collection.ui.writing.first.top.WritefirstTopFragment
import com.eight.collection.ui.writing.second.WritesecondActivity
import com.google.android.material.tabs.TabLayoutMediator
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.collections.ArrayList

class WritefirstActivity() : AppCompatActivity(), RefreshDialogInterface, ModiView {
    lateinit var binding: ActivityWritefirstBinding
    lateinit var refreshDialog : RefreshDialog
    var photoList = ArrayList<String>()
    var photoListsecond = ArrayList<String>()
    var imageList = ArrayList<Image>()
    var reviseimageList = ArrayList<Image>()
    var bitmapList = ArrayList<Bitmap>()
    var imageFileList = ArrayList<String>()
    val photoRVAdapter = PhotoRVAdapter(photoList, this)
    val fragmentList = arrayListOf<Fragment>()
    val information = arrayListOf("TOP", "BOTTOM", "SHOES", "ETC")
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
    private var topselectIsListener : WritefirstActivity.TopSelectIsListener? = null
    private var bottomselectIsListener : WritefirstActivity.BottomSelectIsListener? = null
    private var shoesselectIsListener : WritefirstActivity.ShoesSelectIsListener? = null
    private var etcselectIsListener : WritefirstActivity.EtcSelectIsListener? = null

    var photoIs : Int = -1
    var mode : Int = 1
    var modidate : String? = null
    var filepath : String? = null
    var count : Int = 0
    var imagechange : Boolean = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWritefirstBinding.inflate(layoutInflater)
        setContentView(binding.root)



        //?????? ????????? ??????
        var date = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
        val formatted = date.format(formatter)

        val formatterpost = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formattedpost = date.format(formatterpost)

        binding.writefirstDateTv.text = formatted


        //(???????????????) ?????? ????????? ??????
        var getDate = intent.getStringExtra("date")
        if(getDate != null){
            val date2 = LocalDate.parse(getDate, DateTimeFormatter.ISO_DATE)
            val formatted2 = date2.format(formatter)
            binding.writefirstDateTv.text = formatted2
            mode = 2
            modidate = getDate
            modi()
        }


        //????????? ?????? ??????
        refreshDialog = RefreshDialog(this, this)
        binding.writefirstRefreshIv.setOnClickListener{
            refreshDialog.show()
        }

        //EditText ???????????? ??????
        binding.writefirstLookstyleTv.setRawInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS)


        //????????? ?????????????????? ??? ??????????????? ????????? ????????????
        var getImage_btn = findViewById<ImageView>(R.id.writefirst_add_photo_iv)
        var recyclerview = findViewById<RecyclerView>(R.id.writefirst_photo_recyclerview)

        getImage_btn.setOnClickListener{
            var intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.type = "image/*"
            intent.action = Intent.ACTION_OPEN_DOCUMENT

            startActivityForResult(intent, 200)
        }


        recyclerview.layoutManager =
            LinearLayoutManager(baseContext, LinearLayoutManager.HORIZONTAL, false)
        recyclerview.adapter = photoRVAdapter



        //Top,Bottom,Shoes,Etc ???????????? ??????
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

        setTopSelectIsListener((fragmentList[0] as WritefirstTopFragment))
        setBottomSelectIsListener(fragmentList[1] as WritefirstBottomFragment)
        setShoesSelectIsListener(fragmentList[2] as WritefirstShoesFragment)
        setEtcSelectIsListener(fragmentList[3] as WritefirstEtcFragment)




        //Color?????? ????????? ????????? ??????
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



        //???????????? ????????? Writing Second Activity
        binding.writefirstNextButton.setOnClickListener {
            // Clothes ????????????
            val fixedClothes : ArrayList<FixedClothes> = ArrayList()
            val addedClothes : ArrayList<AddedClothes> = ArrayList()
            var topSelectIs : Int = 0
            var bottomSelectIs : Int = 0
            var shoesSelectIs : Int = 0
            var etcSelectIs : Int = 0
            fixedClothes.addAll(gettopdataListener!!.getFixedData())
            fixedClothes.addAll(getbottomdataListener!!.getFixedData())
            fixedClothes.addAll(getshoesdataListener!!.getFixedData())
            fixedClothes.addAll(getetcdataListener!!.getFixedData())
            addedClothes.addAll(gettopdataListener!!.getAddedData())
            addedClothes.addAll(getbottomdataListener!!.getAddedData())
            addedClothes.addAll(getshoesdataListener!!.getAddedData())
            addedClothes.addAll(getetcdataListener!!.getAddedData())
            topSelectIs = topselectIsListener!!.getIs()
            bottomSelectIs = bottomselectIsListener!!.getIs()
            shoesSelectIs = shoesselectIsListener!!.getIs()
            etcSelectIs = etcselectIsListener!!.getIs()


            if(binding.writefirstLookstyleTv.text.toString().isEmpty() == true){
                var layoutInflater = LayoutInflater.from(this).inflate(R.layout.toast_custom,null)
                var text : TextView = layoutInflater.findViewById(R.id.toast_text_tv)
                text.text = "LOOK NAME??? ??????????????????."
                var toast = Toast(this)
                toast.view = layoutInflater
                toast.setGravity(Gravity.BOTTOM, 0, 270)
                toast.show()
                binding.writefirstLookstyleTv.setHintTextColor(Color.parseColor("#c77a4a"))
            }

            else if(fixedClothes.isEmpty() == true && addedClothes.isEmpty() == true ){
                var layoutInflater = LayoutInflater.from(this).inflate(R.layout.toast_custom,null)
                var text : TextView = layoutInflater.findViewById(R.id.toast_text_tv)
                text.text = "?????? ??? ??? ?????? ??????????????????."
                var toast = Toast(this)
                toast.view = layoutInflater
                toast.setGravity(Gravity.BOTTOM, 0, 270)
                toast.show()
            }

            else if(topSelectIs == 1) {
                var layoutInflater = LayoutInflater.from(this).inflate(R.layout.toast_custom,null)
                var text : TextView = layoutInflater.findViewById(R.id.toast_text_tv)
                text.text = "Top ?????? ?????? ?????? ????????? ????????????."
                var toast = Toast(this)
                toast.view = layoutInflater
                toast.setGravity(Gravity.BOTTOM, 0, 270)
                toast.show()
            }

            else if(bottomSelectIs == 1) {
                var layoutInflater = LayoutInflater.from(this).inflate(R.layout.toast_custom,null)
                var text : TextView = layoutInflater.findViewById(R.id.toast_text_tv)
                text.text = "Bottom ?????? ?????? ?????? ????????? ????????????."
                var toast = Toast(this)
                toast.view = layoutInflater
                toast.setGravity(Gravity.BOTTOM, 0, 270)
                toast.show()
            }

            else if(shoesSelectIs == 1) {
                var layoutInflater = LayoutInflater.from(this).inflate(R.layout.toast_custom,null)
                var text : TextView = layoutInflater.findViewById(R.id.toast_text_tv)
                text.text = "Shoes ?????? ?????? ?????? ????????? ????????????."
                var toast = Toast(this)
                toast.view = layoutInflater
                toast.setGravity(Gravity.BOTTOM, 0, 270)
                toast.show()
            }

            else if(etcSelectIs == 1) {
                var layoutInflater = LayoutInflater.from(this).inflate(R.layout.toast_custom,null)
                var text : TextView = layoutInflater.findViewById(R.id.toast_text_tv)
                text.text = "Etc ?????? ?????? ?????? ????????? ????????????."
                var toast = Toast(this)
                toast.view = layoutInflater
                toast.setGravity(Gravity.BOTTOM, 0, 270)
                toast.show()
            }


            else {
                val intent = Intent(this, WritesecondActivity::class.java)
                intent.putExtra("lookname", binding.writefirstLookstyleTv.text.toString())
                intent.putExtra("photoIs", photoIs)
                intent.putExtra("fixed", fixedClothes)
                intent.putExtra("added", addedClothes)
                intent.putExtra("photo", photoList)
                intent.putExtra("file", imageFileList)
                intent.putExtra("imageChange",imagechange)

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

                Log.d("???????????? ??????","${imageFileList}")

                finish()
                startActivity(intent)
            }
        }
    }


    //??????????????? ????????? ?????? ?????????
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 200) {
            photoList.clear()
            imageList.clear()
            bitmapList.clear()
            imageFileList.clear()
            reviseimageList.clear()
            if (data?.clipData != null) {
                val count = data.clipData!!.itemCount
                if (count > 5) {
                    var layoutInflater = LayoutInflater.from(this).inflate(R.layout.toast_custom,null)
                    var text : TextView = layoutInflater.findViewById(R.id.toast_text_tv)
                    text.text = "????????? 5????????? ?????? ???????????????."
                    var toast = Toast(this)
                    toast.view = layoutInflater
                    toast.setGravity(Gravity.BOTTOM, 0, 270)
                    toast.show()
                    return
                }

                for (i in 0 until count){
                    val imageUri = data.clipData!!.getItemAt(i).uri
                    contentResolver.takePersistableUriPermission(imageUri,Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    photoList.add(imageUri.toString())
                    var bitmap : Bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
                    saveBitmapAsPNGFile(bitmap)
                    bitmapList.add(bitmap)
                    imageFileList.apply{
                        add(filepath!!)
                    }
                    binding.writefirstPhotoDefaultImage1.visibility = View.GONE
                    binding.writefirstPhotoDefaultImage2.visibility = View.GONE
                    imagechange = true
                }

            }
            else {
                data?.data?.let { uri ->
                    val imageUri : Uri? = data?.data
                    if (imageUri != null){
                        contentResolver.takePersistableUriPermission(imageUri,Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        var bitmap : Bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
                        photoList.add(imageUri.toString())
                        saveBitmapAsPNGFile(bitmap)
                        bitmapList.add(bitmap)
                        imageFileList.apply{
                            add(filepath!!)
                        }
                        binding.writefirstPhotoDefaultImage1.visibility = View.GONE
                        binding.writefirstPhotoDefaultImage2.visibility = View.GONE
                        imagechange = true
                    }
                }
            }


            photoIs = 0
            photoRVAdapter.notifyDataSetChanged()
            var b : Int = 0
            for(a in photoList) {
                var c : String = a
                imageList.apply {
                    add(Image(c, b))
                }
                b=-1
            }
            reviseimageList = imageList
        }
    }



    //Color ?????? Interface ?????? ??? ????????? ??????
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

    //??? ?????????
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


    //??? ?????????
    interface TopSelectIsListener {
        fun getIs() : Int
    }

    interface BottomSelectIsListener {
        fun getIs() : Int
    }

    interface ShoesSelectIsListener {
        fun getIs() : Int
    }

    interface EtcSelectIsListener {
        fun getIs() : Int
    }


    fun setTopSelectIsListener(topselectIsListener : WritefirstTopFragment){
        this.topselectIsListener = topselectIsListener
    }

    fun setBottomSelectIsListener(bottomselectIsListener : WritefirstBottomFragment){
        this.bottomselectIsListener = bottomselectIsListener
    }

    fun setShoesSelectIsListener(shoesselectIsListener : WritefirstShoesFragment){
        this.shoesselectIsListener = shoesselectIsListener
    }

    fun setEtcSelectIsListener(etcselectIsListener : WritefirstEtcFragment){
        this.etcselectIsListener = etcselectIsListener
    }



    override fun onOkButtonClicked() {
        photoIs = -1
        imagechange = true
        photoList.clear()
        imageList.clear()
        photoRVAdapter.notifyDataSetChanged()
        binding.writefirstPhotoDefaultImage1.visibility = View.VISIBLE
        binding.writefirstPhotoDefaultImage2.visibility = View.VISIBLE
        binding.writefirstLookstyleTv.setText("")
        //?????? ??? ?????????
        refreshtopdataListener?.refreshData()
        refreshbottomdataListener?.refreshData()
        refreshshoesdataListener?.refreshData()
        refreshetcdataListener?.refreshData()


    }
    override fun onCancelButtonClicked() {
    }


    //??????????????? ?????? ??? ???????????????
    private fun modi(){
        ModiService.modi(this, modidate!!)
    }
    override fun onModiLoading() {
    }
    override fun onModiSuccess(modiresult: ModiResult) {
        //???????????????, ?????? LookName ????????????
        if(modiresult.selected?.lookname != null){
            binding.writefirstLookstyleTv.setText(modiresult.selected?.lookname)
        }
        //???????????????, ?????? ????????? ????????????
        if(modiresult.selected?.image.isNullOrEmpty() == false){
            for(i in modiresult.selected?.image!!){
                photoList.apply {
                    add(i.imageurl!!)
                }
            }
            photoRVAdapter.notifyDataSetChanged()
            photoIs = 0
            binding.writefirstPhotoDefaultImage1.visibility = View.GONE
            binding.writefirstPhotoDefaultImage2.visibility = View.GONE
            var b : Int = 0
            for(a in photoList) {
                var c : String = a
                imageList.apply {
                    add(Image(c, b))
                }
                b=-1
            }
            reviseimageList = imageList
            Log.d("???????????????, ??????????????? ?????????","${imageFileList}")
            Log.d("???????????????, ???????????? ????????? ?????????","${reviseimageList}")
        }
        else {
            photoIs = -1
            photoList.clear()
            imageList.clear()
            imageFileList.clear()
            reviseimageList.clear()
            photoRVAdapter.notifyDataSetChanged()
            binding.writefirstPhotoDefaultImage1.visibility = View.VISIBLE
            binding.writefirstPhotoDefaultImage2.visibility = View.VISIBLE
        }

    }
    override fun onModiFailure(code: Int, message: String) {
    }


    private fun newPngFileName() : String {
        val sdf = SimpleDateFormat("yyyyMMdd__HHmmss")
        val filename = sdf.format(System.currentTimeMillis())
        count=count+1
        return "${filename}${count}.png"
    }

    private fun saveBitmapAsPNGFile(bitmap: Bitmap){
        val path = File(filesDir, "image")
        if(!path.exists()){
            path.mkdirs()
        }
        val photoName = newPngFileName()
        val file = File(path,photoName)
        var imageFile : OutputStream? = null
        try {
            file.createNewFile()
            imageFile = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 60, imageFile)
            imageFile.close()

            filepath = file.absolutePath.toString()
        }catch (e: Exception){
        }
    }
}