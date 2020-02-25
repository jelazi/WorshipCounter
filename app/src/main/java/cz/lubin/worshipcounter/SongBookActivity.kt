package cz.lubin.worshipcounter

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.mancj.materialsearchbar.MaterialSearchBar
import kotlinx.android.synthetic.main.activity_song_book.*
import java.io.Serializable


class SongBookActivity : AppCompatActivity() {

  var editable: String? = null
    var songBookNames: Array<String> = arrayOf()
    var songBookLastDate: Array<String> = arrayOf()
    var songBookPages: Array<String> = arrayOf()
    var songListadapter: SongListAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_book)

        //REFERENCE MATERIALSEARCHBAR AND LISTVIEW
        val searchBar = findViewById(R.id.searchBar) as MaterialSearchBar
        searchBar.setHint("Search..")
        searchBar.setSpeechMode(true)

        editable = intent.getStringExtra("editable")


        songBookNames = SongManager.getSongbookItems("name")
        songBookLastDate = SongManager.getSongbookItems("lastDate")
        songBookPages = SongManager.getSongbookItems("page")


        //ADAPTER
       songListadapter = SongListAdapter(this, songBookPages, songBookNames, songBookLastDate)
        mListView.setAdapter(songListadapter)

        //SEARCHBAR TEXT CHANGE LISTENER
        searchBar.addTextChangeListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                //SEARCH FILTER
                songListadapter!!.getFilter().filter(charSequence)
            }

            override fun afterTextChanged(editable: Editable) {

            }
        })

        //LISTVIEW ITEM CLICKED
        mListView.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override fun onItemClick(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                var choiceSong = Books.songBook.get(i)
                if (editable?.toBoolean()!!) {
                    editSong(choiceSong)
                } else {
                    val resultIntent = Intent()
                    resultIntent.putExtra(name, choiceSong.name)
                    resultIntent.putExtra(ID, choiceSong.id.toString())
                    setResult(Activity.RESULT_OK, resultIntent)
                    finish()
                }

            }
        })

        //end
    }

    override fun onResume() {
        super.onResume()
        Books.sortByLastDate()
        songBookNames = SongManager.getSongbookItems("name")
        songBookLastDate = SongManager.getSongbookItems("lastDate")
        songBookPages = SongManager.getSongbookItems("page")
        val songListadapter = SongListAdapter(this, songBookPages, songBookNames, songBookLastDate)
        mListView.setAdapter(songListadapter)

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.preview_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                Toast.makeText(applicationContext, "click on setting", Toast.LENGTH_LONG).show()
                true
            }

            R.id.new_song -> {
                dialogAddNewSong()
                return true
            }


            else -> super.onOptionsItemSelected(item)
        }
    }

    fun editSong (song: Song)  {
        val intent = Intent(this, SongActivity::class.java)
        intent.putExtra("song", song as Serializable)
        startActivity(intent)
    }

    fun dialogAddNewSong () {
        val builder = AlertDialog.Builder(this@SongBookActivity)
        builder.setTitle("Přidání písně")
        val editNameSong = EditText(this@SongBookActivity)
        val editPageSong = EditText(this@SongBookActivity)
        val mainLayout = LinearLayout(this@SongBookActivity)
        val nameLayout = LinearLayout(this@SongBookActivity)
        val pageLayout = LinearLayout(this@SongBookActivity)
        val descNameSong = TextView (this@SongBookActivity)
        val descPageSong = TextView (this@SongBookActivity)

        descNameSong.text = "Jméno"
        descPageSong.text = "stránka"

        mainLayout.orientation = LinearLayout.VERTICAL
        pageLayout.orientation = LinearLayout.HORIZONTAL
        nameLayout.orientation = LinearLayout.HORIZONTAL
        editNameSong.setText("Nová píseň")
        editNameSong.setSelectAllOnFocus(true)
        builder.setView(editNameSong)
        editPageSong.setText("0")
        editPageSong.setSelectAllOnFocus(true)
        editPageSong.inputType = InputType.TYPE_CLASS_NUMBER

        nameLayout.addView(descNameSong)
        nameLayout.addView(editNameSong)
        pageLayout.addView(descPageSong)
        pageLayout.addView(editPageSong)

        mainLayout.addView(nameLayout)
        mainLayout.addView(pageLayout)
        builder.setView(mainLayout)

        builder.setPositiveButton("Uložit"){dialog, which ->
            if (editNameSong.text.toString().isEmpty()) {
                Toast.makeText(this@SongBookActivity, "Jméno nesmí zůstat prázdné...", Toast.LENGTH_SHORT).show()
            } else if (editNameSong.text.toString().isEmpty()) {
                Toast.makeText(this@SongBookActivity, "Stránka nesmí zůstat prázdná...", Toast.LENGTH_SHORT).show()
            } else {
                addNewSong(editNameSong.text.toString(), editPageSong.text.toString().toInt())
            }
        }

        builder.setNeutralButton("Zrušit"){_,_ ->
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
        dialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
    }

    fun addNewSong (name: String, page: Int) {
        val song = Song(name, page)
        val typeDialog = Books.addSong(song)
        SongManager.setSongBookToPreferences(this@SongBookActivity)
        showMyDialog(typeDialog)
        onResume()
    }

    fun showMyDialog (typeDialog: TypeDialog) {
        when (typeDialog) {
            TypeDialog.IS_SAME_SONG -> {
                Toast.makeText(this@SongBookActivity, "Již uložená píseň má stejný název. Použijte jiný název", Toast.LENGTH_SHORT).show()
            }
            TypeDialog.IS_SAME_PAGE -> {
                Toast.makeText(this@SongBookActivity, "Již uložená píseň má stejnou stránku. Použijte jinou stránku", Toast.LENGTH_SHORT).show()
            }
            TypeDialog.NO_DIALOG -> {

            }
            TypeDialog.SONG_CREATE -> {
                Toast.makeText(this@SongBookActivity, "Píseň je vytvořena", Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(this@SongBookActivity, "Nějaká jiná chyba", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        @JvmField
        val name = ""
        val ID = ""
    }


}
