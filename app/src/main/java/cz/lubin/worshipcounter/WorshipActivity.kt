package cz.lubin.worshipcounter

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_worship.*
import kotlinx.android.synthetic.main.part_day_item.view.*

class WorshipActivity : AppCompatActivity() {

    var listDaySong: Array <Int> = arrayOf(0,0,0,0,0,0,0,0)
    var listPartDayItem: ArrayList <PartDayItem> = arrayListOf()
    private val CHOICE_SONG = 1
    var dayList: WorshipDay? = null
    var choisePartDay: PartDayItem? = null
    var currentDate: MyDate? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_worship)
        FtpWorshipClient.setDefaultFtpPreferences(this@WorshipActivity)
        MailManager.setDefaultMailPreferences(this@WorshipActivity)

        initItems()
        initListeners()
        Books.initItems(this@WorshipActivity)
        currentDate = MyDate(DatePresenter.getCurrentDay(), DatePresenter.getCurrentMonth() + 1, DatePresenter.getCurrentYear())

        dayList = BooksManager.getWorshipDayByDate(currentDate!!)
        if (dayList == null) {
            dayList = WorshipDay(currentDate!!)
        }
        BooksManager.saveWorshipDayToBook(dayList!!)
        redrawDayItems()

        val control: TypeDialog = BooksManager.controlData()
        showMyDialog(control)
    }

    fun initItems() {
        first_before_school.id = 1
        listPartDayItem.add(first_before_school)

        second_before_school.id = 2
        listPartDayItem.add(second_before_school)

        third_before_school.id = 3
        listPartDayItem.add(third_before_school)

        fourth_after_school.id = 4
        listPartDayItem.add(fourth_after_school)

        first_before_sermon.id = 5
        listPartDayItem.add(first_before_sermon)

        second_before_sermon.id = 6
        listPartDayItem.add(second_before_sermon)

        third_before_sermon.id = 7
        listPartDayItem.add(third_before_sermon)

        fourth_after_sermon.id = 8
        listPartDayItem.add(fourth_after_sermon)

        label_date.setText(DatePresenter.getCurrentDate())
    }

    fun redrawDayItems () {
        resetWorshipDay()
        if (dayList!!.orders.isNullOrEmpty()) return
        for (i in dayList!!.orders.indices) {
            if (dayList!!.orders[i]  > listDaySong.size - 1) {
                addNewPart(dayList!!.namesPart[i])
            }
        }
        for (i in dayList!!.orders.indices) {
            var song = dayList!!.songs[i]
            listPartDayItem[dayList!!.orders[i]].song_name_part.text = song.name
            listPartDayItem[dayList!!.orders[i]].song_page_part.text = song.page.toString()
        }
        controlConfirmDay()
    }

    fun controlConfirmDay() {
        if (dayList!!.isConfirm) {
            for (partDayItem in listPartDayItem) {
                getResources().getColor(R.color.colorBlack)
                partDayItem.song_name_part.setTextColor(getResources().getColor(R.color.colorGray))
                partDayItem.song_page_part.setTextColor(getResources().getColor(R.color.colorGray))
                btn_confirm_day.visibility = View.INVISIBLE
                btn_save_day.visibility = View.INVISIBLE
            }
        } else {
            for (partDayItem in listPartDayItem) {
                partDayItem.song_name_part.setTextColor(getResources().getColor(R.color.colorBlack))
                partDayItem.song_page_part.setTextColor(getResources().getColor(R.color.colorBlack))
                btn_confirm_day.visibility = View.VISIBLE
                btn_save_day.visibility = View.VISIBLE
            }
        }
    }

    fun initListeners() {
        label_date.setOnClickListener {
            datePicker()
        }

        btn_save_day.setOnClickListener {
            saveDay()
        }

        btn_confirm_day.setOnClickListener {
           showMyDialog(TypeDialog.CONFIRM_DAY)
        }

        first_before_school.setOnClickListener {
            choiceSong(first_before_school)
        }
        second_before_school.setOnClickListener {
            choiceSong(second_before_school)
        }
        third_before_school.setOnClickListener {
            choiceSong(third_before_school)
        }
        fourth_after_school.setOnClickListener {
            choiceSong(fourth_after_school)
        }
        first_before_sermon.setOnClickListener {
            choiceSong(first_before_sermon)
        }
        second_before_sermon.setOnClickListener {
            choiceSong(second_before_sermon)
        }
        third_before_sermon.setOnClickListener {
            choiceSong(third_before_sermon)
        }
        fourth_after_sermon.setOnClickListener {
            choiceSong(fourth_after_sermon)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.worship_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {


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

            R.id.load_test_data -> {
                loadTestData()
                return true
            }

            R.id.factory_reset -> {
                showMyDialog(TypeDialog.RESET_DATA)
                return true
            }

            R.id.send_songbook_ftp -> {
                connectFtp()
                return true
            }

            R.id.settings -> {
                openSettings()
                return true
            }

            R.id.send_message -> {
                showMyDialog(TypeDialog.SEND_MESSAGE)
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    fun addNewPartDialog () {
        if (dayList!!.isConfirm) {
            Toast.makeText(this@WorshipActivity, "Den je již potvrzený a nejde editovat", Toast.LENGTH_SHORT).show()
            return
        }
        val builder = AlertDialog.Builder(this@WorshipActivity)
        builder.setTitle("Přidání části hraní písně")
        builder.setMessage("Napište popis, kdy píseň byla hraná")
        val input = EditText(this@WorshipActivity)
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        input.layoutParams = lp
        input.setText("Nový popis")
        builder.setView(input)

        builder.setPositiveButton("Uložit"){_, which ->
            if (input.text.toString().isEmpty()) {
                showMyDialog(TypeDialog.DESCRIBE_EMPTY)
            } else {
                addNewPart(input.text.toString())
                showMyDialog(TypeDialog.NEW_ITEM)
            }
        }

        builder.setNeutralButton("Zrušit"){_,_ ->
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    fun addNewPart (desc: String) {
        val newListDaySong = Array<Int> (listDaySong.size + 1) {i -> 0}
        for (i in listDaySong.indices) {
            newListDaySong[i] = listDaySong[i]
        }
        listDaySong = newListDaySong
        val newPart = PartDayItem(this@WorshipActivity)
        newPart.description_part?.text = desc
        newPart.id = listDaySong.size - 1
        newPart.setOnClickListener {
            choiceSong(newPart)
        }
        listPartDayItem.add(newPart)

        layout_day_woship.addView(newPart)
    }

    fun choiceSong (partDay: PartDayItem) {
        if (dayList!!.isConfirm) {
            Toast.makeText(this@WorshipActivity, "Den je již potvrzený a nejde editovat", Toast.LENGTH_SHORT).show()
            return
        }
        val intent = Intent(this, SongBookActivity::class.java)
        intent.putExtra("editable",false.toString())
        choisePartDay = partDay
        startActivityForResult(intent, CHOICE_SONG)
    }


    fun datePicker () {
        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, selectedYear, selectedMonth, selectedDay ->
            label_date.setText(DatePresenter.getSelectedDate(selectedYear, selectedMonth, selectedDay))
            currentDate = MyDate(selectedDay, selectedMonth + 1, selectedYear)
            val daylistDate = dayList?.date
            if (dayList != null && daylistDate?.compareTo(currentDate!!) != 0) {
                dayList = BooksManager.getWorshipDayByDate(currentDate!!)
                if (dayList == null) {
                    dayList = WorshipDay(currentDate!!)
                }
                redrawDayItems()
            }
        }, DatePresenter.getCurrentYear(), DatePresenter.getCurrentMonth(), DatePresenter.getCurrentDay())
        dpd.show()
    }


    fun resetWorshipDay() {
        first_before_school.description_part.setText("SŠ 1")
        second_before_school.description_part.setText("SŠ 2")
        third_before_school.description_part.setText("SŠ 3")
        fourth_after_school.description_part.setText("SŠ 4")
        first_before_sermon.description_part.setText("Kázání 1")
        second_before_sermon.description_part.setText("Kázání 2")
        third_before_sermon.description_part.setText("Kázání 3")
        fourth_after_sermon.description_part.setText("Kázání 4")

        first_before_school.song_name_part.setText("---")
        second_before_school.song_name_part.setText("---")
        third_before_school.song_name_part.setText("---")
        fourth_after_school.song_name_part.setText("---")
        first_before_sermon.song_name_part.setText("---")
        second_before_sermon.song_name_part.setText("---")
        third_before_sermon.song_name_part.setText("---")
        fourth_after_sermon.song_name_part.setText("---")

        first_before_school.song_page_part.setText("---")
        second_before_school.song_page_part.setText("---")
        third_before_school.song_page_part.setText("---")
        fourth_after_school.song_page_part.setText("---")
        first_before_sermon.song_page_part.setText("---")
        second_before_sermon.song_page_part.setText("---")
        third_before_sermon.song_page_part.setText("---")
        fourth_after_sermon.song_page_part.setText("---")
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
                    listDaySong[choisePartDay!!.id!! - 1] = choiceSong.id
                    dayList!!.updateSong(listPartDayItem.indexOf(choisePartDay!!), choiceSong, choisePartDay?.description_part?.text.toString())
                    redrawDayItems()
                    Toast.makeText(this@WorshipActivity, "přidána píseň: " + choiceSong.name, Toast.LENGTH_SHORT).show()
                }
            }
            choisePartDay = null
        }
    }

    fun confirmDay () {
        dayList!!.isConfirm = true
        saveDay()
        controlConfirmDay()
        var index = 0
        listDaySong.forEach {

            if (it != 0) {
                dayList?.addSong(index, Books.getSongByID(it)!!, listPartDayItem[index].description_part.text.toString())
            }
            index ++
        }
        showMyDialog(BooksManager.saveDaysData(dayList!!))
        BooksManager.setSongBookToPreferences(this@WorshipActivity)
    }

    fun saveDay () {
        if (dayList!!.isConfirm) {
            Toast.makeText(this@WorshipActivity, "Den je již potvrzený a není nutné ho již ukládat.", Toast.LENGTH_SHORT).show()
            return
        }
        BooksManager.saveWorshipDayToBook(dayList!!)
        BooksManager.setWorshipDayBookToPreferences(this)
        Toast.makeText(this@WorshipActivity, "Den je uložený.", Toast.LENGTH_SHORT).show()
    }



    fun loadTestData () {
        BooksManager.createDefaultSongBook()
        BooksManager.setSongBookToPreferences(this)
        Toast.makeText(this@WorshipActivity, "Testovací data jsou načtena.", Toast.LENGTH_SHORT).show()
    }

    fun showMyDialog (typeDialog: TypeDialog) {
        when (typeDialog) {
            TypeDialog.SEND_MESSAGE -> {
                val builder = AlertDialog.Builder(this@WorshipActivity)
                builder.setTitle("Poslání mailu")

                val preference = PreferenceManager.getDefaultSharedPreferences(this)
                val mailAdress = preference.getString(this.getString(R.string.mail_to_key), this.getString(R.string.mail_to_value))!!
                builder.setMessage("Chcete poslat mail s písněmi ze dne na adresy: " + mailAdress + "?")
                builder.setPositiveButton("Ano"){dialog, which ->
                    sendMessage()
                }

                builder.setNeutralButton("Ne"){_,_ ->
                }

                val dialog: AlertDialog = builder.create()
                dialog.show()
            }

            TypeDialog.EMPTY_DATA -> {
                val builder = AlertDialog.Builder(this@WorshipActivity)
                builder.setTitle("Prázdná data")
                builder.setMessage("V aplikaci nejsou uložena žádná data.")
                builder.setPositiveButton("Nahrát testovací data"){dialog, which ->
                    loadTestData()
                }

                builder.setNegativeButton("Stáhnout výchozí písně") {dialog, which ->
                    loadDefaultData()
                }

                builder.setNeutralButton("Nic nenahrát"){_,_ ->
                }

                val dialog: AlertDialog = builder.create()
                dialog.show()
            }

            TypeDialog.RESET_DATA -> {
                val builder = AlertDialog.Builder(this@WorshipActivity)
                builder.setTitle("Vymazání všech dat")
                builder.setMessage("Opravdu chcete vymazat všechna data?")

                builder.setPositiveButton("Ano"){dialog, which ->
                    BooksManager.resetData(this@WorshipActivity)
                    resetWorshipDay()
                    PreferenceManager.getDefaultSharedPreferences(this).edit().clear().apply()
                    val control: TypeDialog = BooksManager.controlData()
                    showMyDialog(control)
                }

                builder.setNeutralButton("Ne"){_,_ ->
                }

                val dialog: AlertDialog = builder.create()
                dialog.show()
            }

            TypeDialog.CONFIRM_DAY -> {
                val builder = AlertDialog.Builder(this@WorshipActivity)
                builder.setTitle("Uložení písní")
                builder.setMessage("Je opravdu tento den správně napsán? Chcete data napevno uložit?")


                builder.setPositiveButton("Ano"){dialog, which ->
                    confirmDay()
                }

                builder.setNeutralButton("Ne"){_,_ ->
                }

                val dialog: AlertDialog = builder.create()
                dialog.show()
            }

            TypeDialog.ALL_DATE_USED -> {
                Toast.makeText(this@WorshipActivity, "Ve všech vybraných písních se již toto datum nachází", Toast.LENGTH_SHORT).show()

            }

            TypeDialog.DATE_USED -> {
                Toast.makeText(this@WorshipActivity, "V některých vybraných písních se již toto datum nachází. Ostatní byly uloženy.", Toast.LENGTH_SHORT).show()

            }

            TypeDialog.LIST_EMPTY -> {
                Toast.makeText(this@WorshipActivity, "Seznam je prázdný", Toast.LENGTH_SHORT).show()

            }

            TypeDialog.DESCRIBE_EMPTY -> {
                Toast.makeText(this@WorshipActivity, "Popis nesmí zůstat prázdný...", Toast.LENGTH_SHORT).show()

            }

            TypeDialog.NO_DIALOG -> {
            }

            TypeDialog.DATA_SAVED-> {
                Toast.makeText(this@WorshipActivity, "Data jsou uložena", Toast.LENGTH_SHORT).show()
            }

            TypeDialog.NEW_ITEM-> {
                Toast.makeText(this@WorshipActivity, "Nová položka je vytvořena", Toast.LENGTH_SHORT).show()
            }

            else -> {
                Toast.makeText(this@WorshipActivity, "Nějaká jiná chyba", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun loadDefaultData () {
        FtpWorshipClient.downloadDefaultData(this, this)
    }

    fun connectFtp () {
        FtpWorshipClient.uploadBooksLibraryToFtp(this, this)
    }

    val handler: Handler = object : Handler(Looper.getMainLooper()) {

        override fun handleMessage(inputMessage: Message) {
            // Gets the image task from the incoming Message object.
            val bundle: Bundle = inputMessage.getData()
            val correct = bundle.getString("correct")
            val err = bundle.getString("error")
            val inputString = bundle.getString("array")
            if (!correct.isNullOrEmpty()) {
                Toast.makeText(this@WorshipActivity, correct, Toast.LENGTH_LONG).show()
                if (!inputString.isNullOrEmpty()) {
                    val array = JsonParser.jsonToSongBook(inputString)
                    for (song in array) {
                        Books.addSong(song)
                    }
                    Books.sortByLastDate()
                    BooksManager.setSongBookToPreferences(this@WorshipActivity)
                }
            }
            if (!err.isNullOrEmpty()) {
                Toast.makeText(this@WorshipActivity, err, Toast.LENGTH_LONG).show()
            }
        }
    }

    fun openSettings () {
        startActivity(Intent(this@WorshipActivity, SettingsActivity::class.java))

    }

    fun getBodyMessage (preference: SharedPreferences): String {
        var message = "\n"

        message += preference.getString(this.getString(R.string.mail_message_key), this.getString(R.string.mail_message_value))!! + "\n"

        var customDayList = WorshipDay(currentDate!!)

        for (index in listDaySong.indices) {

            if (listDaySong[index] != 0) {
                customDayList?.addSong(index, Books.getSongByID(listDaySong[index])!!, listPartDayItem[index].description_part.text.toString())
            }
        }

        message += customDayList!!.getMessageForMail()

        return message
    }

    fun sendMessage () {
        val preference = PreferenceManager.getDefaultSharedPreferences(this)

        MailManager.send(
            this,
            preference.getString(this.getString(R.string.mail_to_key), this.getString(R.string.mail_to_value))!!,
            preference.getString(this.getString(R.string.mail_from_key), this.getString(R.string.mail_from_value))!!,
            preference.getString(this.getString(R.string.mail_subject_key), this.getString(R.string.mail_subject_value))!!,
            getBodyMessage(preference)
        )
    }
}

