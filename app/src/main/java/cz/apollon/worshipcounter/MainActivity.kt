package cz.apollon.worshipcounter

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cz.apollon.worshipcounter.R.id.first_before_school


class MainActivity : AppCompatActivity() {

    var lblDate: TextView? = null


    var firstBeforeSchool: PartDayItem? = null
    var secondBeforeSchool: PartDayItem? = null
    var thirdBeforeSchool: PartDayItem? = null
    var fourthBeforeSchool: PartDayItem? = null
    var firstBeforeSermon: PartDayItem? = null
    var secondBeforeSermon: PartDayItem? = null
    var thirdBeforeSermon: PartDayItem? = null
    var fourthAfterSermon: PartDayItem? = null


    private val REQUEST_FORM = 1


    var btnSongPreview: Button? = null
    var btnSongBookPreview: Button? = null
    var btnAddSongSchool: Button? = null
    var btnAddSongSermon: Button? = null

    var choisePartDay: PartDayItem? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initItems()
        initListeners()
        SongManager.createDefaultSongBook()
        SongBook.sortByLastDate()
        var list = SongBook.listBook

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

        firstBeforeSchool = findViewById(first_before_school)
        secondBeforeSchool= findViewById(R.id.second_before_school)
        thirdBeforeSchool= findViewById(R.id.third_before_school)
        fourthBeforeSchool= findViewById(R.id.fourth_after_school)
        firstBeforeSermon= findViewById(R.id.first_before_sermon)
        secondBeforeSermon= findViewById(R.id.second_before_sermon)
        thirdBeforeSermon= findViewById(R.id.third_before_sermon)
        fourthAfterSermon= findViewById(R.id.fourth_after_sermon)


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
        choisePartDay = partDay
        startActivityForResult(intent, REQUEST_FORM)
    }


    fun datePicker () {
        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, selectedYear, selectedMonth, selectedDay ->
            lblDate?.setText(DatePresenter.getSelectedDate(selectedYear, selectedMonth, selectedDay))
        }, DatePresenter.getCurrentYear(), DatePresenter.getCurrentMonth(), DatePresenter.getCurrentDay())
        dpd.show()
    }

    fun openSongPreview () {

    }


    fun openSongBookPreview () {
        val intent = Intent(this, SongBookActivity::class.java)
        startActivity(intent)
    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == REQUEST_FORM && resultCode == RESULT_OK) {
            val name = data?.getStringExtra(SongBookActivity.name)
            Toast.makeText(this@MainActivity, name, Toast.LENGTH_SHORT).show()
            choisePartDay?.name?.setText(name)
            choisePartDay = null
        }
    }



}



