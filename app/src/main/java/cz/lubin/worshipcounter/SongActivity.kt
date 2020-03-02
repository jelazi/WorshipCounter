package cz.lubin.worshipcounter

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.view.WindowManager
import android.widget.*
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_song.*

class SongActivity : AppCompatActivity() {

    var song: Song? = null
    var isChangeName = false
    var isChangePage = false
    var isChangePres = false
    var book:String? = null
    val array = arrayOf("Bílý kostel", "Chci oslavovat", "Sionský")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song)

        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Úprava písně"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        song = intent.extras.get("song") as Song
        book = song?.book
        this.initItems()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun initItems () {

        name_song.text


        if (song != null) {
            name_song.text = (song?.name)
            page_song.text = (song?.page.toString())

            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, song?.getDates())
            dates_song.adapter = adapter


        var totalHeight = 0
        val adapterCount = adapter.getCount()
        for (size in 0 until adapterCount) {
            val listItem: View = adapter.getView(size, null, dates_song)
            listItem.measure(0, 0)
            totalHeight += listItem.getMeasuredHeight()
        }
        val params = dates_song.getLayoutParams()
        params.height = (totalHeight + (dates_song.getDividerHeight() * (adapterCount)))
        dates_song.setLayoutParams(params)
        }

        if (!song?.presentation.isNullOrEmpty()) {
            presentation_song.text = song?.presentation
        }


        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, array
        )

        books_type.adapter = adapter
        var index = 0
        if (song?.book in array) {
            index = array.indexOf(song?.book)
        }
        books_type.setSelection(index)

        initListeners()
    }

    fun initListeners () {
        name_song.setOnClickListener {
            changeName()
        }
        page_song.setOnClickListener {
            changePage()
        }
        btn_save_song.setOnClickListener {
            saveSong()
        }
        presentation_song.setOnClickListener {
            changePresentationSong()
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
        input.setText(name_song.text)
        input.requestFocus()
        builder.setView(input)

        builder.setPositiveButton("ANO"){dialog, which ->
            name_song.setText(input.text)
            isChangeName = true
        }

        builder.setNeutralButton("Zrušit"){_,_ ->
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
        dialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
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
        input.setText(page_song.text)
        input.requestFocus()
        builder.setView(input)

        builder.setPositiveButton("ANO"){dialog, which ->
            page_song.setText(input.text)
            isChangePage = true
        }


        builder.setNeutralButton("Zrušit"){_,_ ->
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
        dialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
    }

    fun changePresentationSong() {
        val builder = AlertDialog.Builder(this@SongActivity)
        builder.setTitle("Změna webové stránky")
        builder.setMessage("Prezentace písně se nachází zde?")
        val input = EditText(this@SongActivity)
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        input.layoutParams = lp
        input.setText(presentation_song.text)
        input.requestFocus()
        builder.setView(input)

        builder.setPositiveButton("ANO"){dialog, which ->
            presentation_song.setText(input.text)
            isChangePres = true
        }

        builder.setNeutralButton("Zrušit"){_,_ ->
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
        dialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
    }

    fun saveSong () {
        if (name_song.text.isEmpty()) {
            Toast.makeText(this@SongActivity, "Pole jméno nesmí být prázdné.", Toast.LENGTH_SHORT).show()
            return
        }

        if (name_song.text.isEmpty()) {
            Toast.makeText(this@SongActivity, "Pole stránka nesmí být prázdné.", Toast.LENGTH_SHORT).show()
            return
        }

        if (isChangeName) {
            song?.name = name_song.text.toString()
        }
        if (isChangePage) {
            song?.page = page_song.text.toString().toInt()
        }

        if (isChangePres) {
            song?.presentation = presentation_song.text.toString()
        }

        song?.book = books_type.selectedItem.toString()
        Books.changeSong(song!!)
        BooksManager.setSongBookToPreferences(this@SongActivity)
        val resultIntent = Intent()
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }
}
