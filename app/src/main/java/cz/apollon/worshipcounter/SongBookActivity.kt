package cz.apollon.worshipcounter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.mancj.materialsearchbar.MaterialSearchBar

class SongBookActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_book)

        //REFERENCE MATERIALSEARCHBAR AND LISTVIEW
        val lv = findViewById(R.id.mListView) as ListView
        val searchBar = findViewById(R.id.searchBar) as MaterialSearchBar
        searchBar.setHint("Search..")
        searchBar.setSpeechMode(true)

        var songBookNames = SongManager.getSongbookItems("name")
        var songBookIDs = SongManager.getSongbookItems("lastDate")
        var songBookPages = SongManager.getSongbookItems("page")



        //ADAPTER
        val songListadapter = SongListAdapter(this, songBookPages, songBookNames, songBookIDs)
        lv.setAdapter(songListadapter)

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
        lv.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override fun onItemClick(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                Toast.makeText(this@SongBookActivity, songListadapter.getItem(i)!!.toString(), Toast.LENGTH_SHORT).show()
            }
        })

        //end
    }
}
