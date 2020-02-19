package cz.apollon.worshipcounter

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*

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

    var layoutDayWorship: LinearLayout? = null


    private val CHOICE_SONG = 1

    var dayList: WorshipDay? = null


    var btnSongPreview: Button? = null
    var btnSongBookPreview: Button? = null

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

        layoutDayWorship = findViewById(R.id.layout_day_woship)

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.worship_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                Toast.makeText(applicationContext, "click on setting", Toast.LENGTH_LONG).show()
                true
            }

            R.id.add_part_day -> {
                addNewPartDialog()
                return true
            }

            R.id.preview_song -> {
                openSongBookPreview()
                return true
            }

            R.id.save_day -> {
                saveDay()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun addNewPartDialog () {
        val builder = AlertDialog.Builder(this@WorshipActivity)
        builder.setTitle("Přidání písně")
        builder.setMessage("Napište popis, kdy píseň byla hraná")
        val input = EditText(this@WorshipActivity)
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        input.layoutParams = lp
        input.setText("Nový popis")
        builder.setView(input)

        builder.setPositiveButton("ANO"){dialog, which ->
            if (input.text.toString().isEmpty()) {
                Toast.makeText(this@WorshipActivity, "Popis nesmí zůstat prázdný...", Toast.LENGTH_SHORT).show()
            } else {
                addNewPart(input.text.toString())
            }
        }

        builder.setNeutralButton("Zrušit"){_,_ ->
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    fun addNewPart (desc: String) {
        var newListDaySong = Array<Int> (listDaySong.size + 1) {i -> 0}
        for (i in listDaySong.indices) {
            newListDaySong[i] = listDaySong[i]
        }
        listDaySong = newListDaySong
        var newPart: PartDayItem = PartDayItem(this@WorshipActivity)
        newPart.description?.text = desc
        newPart.id = listDaySong.size - 1
        newPart.setOnClickListener {
            choiceSong(newPart)
        }
        layoutDayWorship?.addView(newPart)
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

    fun saveDay () {

    }
}

