package cz.apollon.worshipcounter

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.*
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity



class SongActivity : AppCompatActivity() {

    var lblNameSong: TextView? = null
    var lblPageSong: TextView? = null
    var btnSaveSong: Button? = null
    var datesSong: ListView? = null

    var song: Song? = null

    var isChangeName = false
    var isChangePage = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song)

        song = intent.extras.get("song") as Song

        initItems()
    }

    fun initItems () {
        lblNameSong = findViewById(R.id.name_song)
        lblPageSong = findViewById(R.id.page_song)
        btnSaveSong = findViewById(R.id.btn_save_song)
        datesSong = findViewById(R.id.dates_song)

        initListeners()

        if (song != null) {
            lblNameSong?.setText(song?.name)
            lblPageSong?.setText(song?.page.toString())

            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, song?.getDates())
            datesSong?.adapter = adapter



        // get listview height
        var totalHeight = 0
        var adapterCount = adapter.getCount()
        for (size in 0 until adapterCount) {
            var listItem: View = adapter.getView(size, null, datesSong)
            listItem.measure(0, 0)
            totalHeight += listItem.getMeasuredHeight()
        }
        // Change Height of ListView
        var params = datesSong?.getLayoutParams()
        params?.height = (totalHeight + (datesSong?.getDividerHeight()!! * (adapterCount)))
        datesSong?.setLayoutParams(params)

        }

    }

    fun initListeners () {
        lblNameSong?.setOnClickListener {
            changeName()
        }
        lblPageSong?.setOnClickListener {
            changePage()
        }
        btnSaveSong?.setOnClickListener {
            saveSong()
        }
    }

    fun changeName() {

        val builder = AlertDialog.Builder(this@SongActivity)
        builder.setTitle("Změna jména")
        builder.setMessage("Uložit toto jméno?")
        val input = EditText(this@SongActivity)
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        input.layoutParams = lp
        input.setText(lblNameSong?.text)
        builder.setView(input)

        builder.setPositiveButton("ANO"){dialog, which ->
            lblNameSong?.setText(input.text)
            isChangeName = true
        }

        builder.setNeutralButton("Zrušit"){_,_ ->
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
    fun changePage() {
        val builder = AlertDialog.Builder(this@SongActivity)
        builder.setTitle("Změna stránky")
        builder.setMessage("Uložit tuto stránku?")
        val input = EditText(this@SongActivity)
        input.inputType = InputType.TYPE_CLASS_NUMBER
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        input.layoutParams = lp
        input.setText(lblPageSong?.text)
        builder.setView(input)

        builder.setPositiveButton("ANO"){dialog, which ->
            // Do something when user press the positive button
            lblPageSong?.setText(input.text)
            isChangePage = true
        }


        builder.setNeutralButton("Zrušit"){_,_ ->
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()

    }
    fun saveSong () {
        if (isChangeName) {
            song?.name = lblNameSong?.text.toString()
        }
        if (isChangePage) {
            song?.page = lblPageSong?.text.toString().toInt()
        }
        Books.changeSong(song!!)
        finish()
    }
}
