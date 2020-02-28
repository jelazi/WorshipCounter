package cz.lubin.worshipcounter

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_song_book.*
import java.io.Serializable
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class SongBookActivity : AppCompatActivity() {

    var editable: String? = null
    var songListadapter: SongListAdapter? = null
    val info = ArrayList<HashMap<String, String>>()
    var isChangeSongBook: Boolean = false
    var listview: ListView? = null
    private val EDIT_SONG = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_book)

        val searchView = findViewById(R.id.searchView) as SearchView

        editable = intent.getStringExtra("editable")
        listview = findViewById(R.id.mListView) as ListView

        var hashMap: HashMap<String, String> = HashMap<String, String>()

        for (i in 0..Books.songBook.size - 1) {
            hashMap = HashMap()
            hashMap.put("name", Books.songBook[i].name)
            if (Books.songBook[i].getLastDate() == null) {
                hashMap.put("lastDate", "---")

            } else {
                hashMap.put("lastDate", Books.songBook[i].getLastDate().toString())

            }
            hashMap.put("page", Books.songBook[i].page.toString())
            info.add(hashMap)
        }

       songListadapter = SongListAdapter(this, info)
        listview?.adapter = (songListadapter)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val text = newText
                songListadapter!!.filter(text)
                return false
            }
        })

        listview?.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override fun onItemClick(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                val name = info[i].get("name")
                var choiceSong = Books.getSongByName(name!!)
                if (editable?.toBoolean()!!) {
                    editSong(choiceSong!!)
                } else {
                    val resultIntent = Intent()
                    resultIntent.putExtra(name, choiceSong!!.name)
                    resultIntent.putExtra(ID, choiceSong!!.id.toString())
                    setResult(Activity.RESULT_OK, resultIntent)
                    finish()
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        Books.sortByLastDate()

        if (isChangeSongBook) {
            isChangeSongBook = false
            songListadapter = SongListAdapter(this, info)
            listview?.setAdapter(songListadapter)
        }
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
        intent.putExtra("edit",false.toString())
        startActivityForResult(intent, EDIT_SONG)
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
        BooksManager.setSongBookToPreferences(this@SongBookActivity)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == EDIT_SONG && resultCode == RESULT_OK) {
            isChangeSongBook = true
        }
    }

    companion object {
        @JvmField
        val name = ""
        val ID = ""
    }
}
