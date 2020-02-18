package cz.apollon.worshipcounter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.mancj.materialsearchbar.MaterialSearchBar
import java.io.Serializable



class SongBookActivity : AppCompatActivity() {

  var editable: String? = null
    var lv: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_book)

        //REFERENCE MATERIALSEARCHBAR AND LISTVIEW
        lv = findViewById(R.id.mListView) as ListView
        val searchBar = findViewById(R.id.searchBar) as MaterialSearchBar
        searchBar.setHint("Search..")
        searchBar.setSpeechMode(true)

        editable = intent.getStringExtra("editable")


        var songBookNames = SongManager.getSongbookItems("name")
        var songBookLastDate = SongManager.getSongbookItems("lastDate")
        var songBookPages = SongManager.getSongbookItems("page")


        //ADAPTER
        val songListadapter = SongListAdapter(this, songBookPages, songBookNames, songBookLastDate)
        lv?.setAdapter(songListadapter)

        //SEARCHBAR TEXT CHANGE LISTENER
        searchBar.addTextChangeListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                //SEARCH FILTER
                songListadapter.getFilter().filter(charSequence)
            }

            override fun afterTextChanged(editable: Editable) {

            }
        })

        //LISTVIEW ITEM CLICKED
        lv?.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override fun onItemClick(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                var choiceSong = Books.songBook.get(i)
                if (editable?.toBoolean()!!) {
                    editSong(choiceSong)
                } else {
                    val resultIntent = Intent()
                    resultIntent.putExtra(name, choiceSong.name)
                    resultIntent.putExtra(ID, choiceSong.ID.toString())
                    setResult(Activity.RESULT_OK, resultIntent)
                    finish()
                }

            }
        })

        //end
    }

    override fun onResume() {
        super.onResume()
        var songBookNames = SongManager.getSongbookItems("name")
        var songBookLastDate = SongManager.getSongbookItems("lastDate")
        var songBookPages = SongManager.getSongbookItems("page")
        val songListadapter = SongListAdapter(this, songBookPages, songBookNames, songBookLastDate)
        lv?.deferNotifyDataSetChanged()
        lv?.invalidateViews();
    }

    fun editSong (song: Song)  {
        val intent = Intent(this, SongActivity::class.java)
        intent.putExtra("song", song as Serializable)
        startActivity(intent)
    }

    companion object {
        @JvmField
        val name = ""
        val ID = ""
    }


}
