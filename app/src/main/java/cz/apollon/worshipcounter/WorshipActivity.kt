package cz.apollon.worshipcounter

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class WorshipActivity : AppCompatActivity() {

    var lblDate: TextView? = null
    var listDaySong: Array <Int> = arrayOf(0,0,0,0,0,0,0,0,0)


    var firstBeforeSchool: PartDayItem? = null
    var secondBeforeSchool: PartDayItem? = null
    var thirdBeforeSchool: PartDayItem? = null
    var fourthBeforeSchool: PartDayItem? = null
    var firstBeforeSermon: PartDayItem? = null
    var secondBeforeSermon: PartDayItem? = null
    var thirdBeforeSermon: PartDayItem? = null
    var fourthAfterSermon: PartDayItem? = null


    private val CHOICE_SONG = 1

    var dayList: WorshipDay? = null


    var btnSongPreview: Button? = null
    var btnSongBookPreview: Button? = null
    var btnAddSongSchool: Button? = null
    var btnAddSongSermon: Button? = null

    var choisePartDay: PartDayItem? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_worship)

        initItems()
        initListeners()
        resetWorshipDay()
        SongManager.createDefaultSongBook()
        Books.sortByLastDate()
        var list = Books.songBook

        var today = MyDate(DatePresenter.getCurrentDay(), DatePresenter. getCurrentMonth(), DatePresenter.getCurrentYear())
        dayList = WorshipDay(today)

        //  datePicker()
        //  openSongBookPreview()



        //     var jsonListBook = JsonParser.listBookToJson()
        //    var listBook = JsonParser.jsonToListBook(jsonListBook)

        //  listBook.remove(listBook[0])
        //  listBook[1].name = "kd fjas j"
        //  SongBook.listBook.remove(SongBook.listBook[3])
        //  SongBook.listBook[4].name = "dfaskjf k"

        //    var isSame = SongManager.compareListBooks(SongBook.listBook, listBook)
        //    var changingSongs = SongManager.getChangingSongs(SongBook.listBook, listBook, true)
        var hello = ""
    }

    fun initItems() {
        lblDate = findViewById(R.id.label_date)

        firstBeforeSchool = findViewById(R.id.first_before_school)
        firstBeforeSchool?.id = 1
        secondBeforeSchool= findViewById(R.id.second_before_school)
        secondBeforeSchool?.id = 2
        thirdBeforeSchool= findViewById(R.id.third_before_school)
        thirdBeforeSchool?.id = 3
        fourthBeforeSchool= findViewById(R.id.fourth_after_school)
        fourthBeforeSchool?.id = 4
        firstBeforeSermon= findViewById(R.id.first_before_sermon)
        firstBeforeSermon?.id = 5
        secondBeforeSermon= findViewById(R.id.second_before_sermon)
        secondBeforeSermon?.id = 6
        thirdBeforeSermon= findViewById(R.id.third_before_sermon)
        thirdBeforeSermon?.id = 7
        fourthAfterSermon= findViewById(R.id.fourth_after_sermon)
        fourthAfterSermon?.id = 8

        btnAddSongSchool = findViewById(R.id.btn_add_song_school)
        btnAddSongSermon = findViewById(R.id.btn_add_song_sermon)

        btnSongPreview = findViewById(R.id.btnSongPreview)
        btnSongBookPreview = findViewById(R.id.btnSongBookPreview)
        lblDate?.setText(DatePresenter.getCurrentDate())
    }

    fun initListeners() {
        val lblDateListener = lblDate?.setOnClickListener {
            datePicker()
        }
        val btnSongPreviewListener = btnSongPreview?.setOnClickListener {
            openSongPreview()
        }
        val btnSongBookPreviewListener = btnSongBookPreview?.setOnClickListener {
            openSongBookPreview()
        }
        firstBeforeSchool?.setOnClickListener {
            choiceSong(firstBeforeSchool!!)
        }
        secondBeforeSchool?.setOnClickListener {
            choiceSong(secondBeforeSchool!!)
        }
        thirdBeforeSchool?.setOnClickListener {
            choiceSong(thirdBeforeSchool!!)
        }
        fourthBeforeSchool?.setOnClickListener {
            choiceSong(fourthBeforeSchool!!)
        }
        firstBeforeSermon?.setOnClickListener {
            choiceSong(firstBeforeSermon!!)
        }
        secondBeforeSermon?.setOnClickListener {
            choiceSong(secondBeforeSermon!!)
        }
        thirdBeforeSermon?.setOnClickListener {
            choiceSong(thirdBeforeSermon!!)
        }
        fourthAfterSermon?.setOnClickListener {
            choiceSong(fourthAfterSermon!!)
        }

    }

    fun choiceSong (partDay: PartDayItem) {
        val intent = Intent(this, SongBookActivity::class.java)
        intent.putExtra("editable",false.toString())
        choisePartDay = partDay
        startActivityForResult(intent, CHOICE_SONG)
    }


    fun datePicker () {
        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, selectedYear, selectedMonth, selectedDay ->
            lblDate?.setText(DatePresenter.getSelectedDate(selectedYear, selectedMonth, selectedDay))
            var myDate = MyDate(selectedDay, selectedMonth, selectedYear)
            var daylistDate = dayList?.date
            if (dayList != null && daylistDate?.compareTo(myDate) != 0) {
                dayList = WorshipDay(myDate)
                resetWorshipDay()
            }

        }, DatePresenter.getCurrentYear(), DatePresenter.getCurrentMonth(), DatePresenter.getCurrentDay())
        dpd.show()
    }

    fun openSongPreview () {

    }

    fun resetWorshipDay() {
        firstBeforeSchool?.description?.setText("SŠ 1")
        secondBeforeSchool?.description?.setText("SŠ 2")
        thirdBeforeSchool?.description?.setText("SŠ 3")
        fourthBeforeSchool?.description?.setText("SŠ 4")
        firstBeforeSermon?.description?.setText("Kázání 1")
        secondBeforeSermon?.description?.setText("Kázání 2")
        thirdBeforeSermon?.description?.setText("Kázání 3")
        fourthAfterSermon?.description?.setText("Kázání 4")

        firstBeforeSchool?.name?.setText("---")
        secondBeforeSchool?.name?.setText("---")
        thirdBeforeSchool?.name?.setText("---")
        fourthBeforeSchool?.name?.setText("---")
        firstBeforeSermon?.name?.setText("---")
        secondBeforeSermon?.name?.setText("---")
        thirdBeforeSermon?.name?.setText("---")
        fourthAfterSermon?.name?.setText("---")

        firstBeforeSchool?.page?.setText("---")
        secondBeforeSchool?.page?.setText("---")
        thirdBeforeSchool?.page?.setText("---")
        fourthBeforeSchool?.page?.setText("---")
        firstBeforeSermon?.page?.setText("---")
        secondBeforeSermon?.page?.setText("---")
        thirdBeforeSermon?.page?.setText("---")
        fourthAfterSermon?.page?.setText("---")


    }


    fun openSongBookPreview () {
        val intent = Intent(this, SongBookActivity::class.java)
        intent.putExtra("editable",true.toString())
        startActivity(intent)
    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == CHOICE_SONG && resultCode == RESULT_OK) {
            val name = data?.getStringExtra(SongBookActivity.name)
            val ID = data?.getStringExtra(SongBookActivity.ID)?.toInt()
            if (ID != null) {
                val choiceSong: Song? = Books.getSongByID(ID)
                if (choiceSong != null) {
                    listDaySong[choisePartDay!!.id!!] = choiceSong.ID

                    Toast.makeText(this@WorshipActivity, name, Toast.LENGTH_SHORT).show()

                    choisePartDay?.name?.setText(choiceSong.name)
                    choisePartDay?.page?.setText(choiceSong.page.toString())
                }
            }
            choisePartDay = null
        }
    }
}

