package cz.apollon.worshipcounter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.*

class SongListAdapter (private val context: Activity, private val page: Array<String>, private val name: Array<String>, private val lastDate: Array<String>)
    : ArrayAdapter<String>(context, R.layout.song_item, name)  {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.song_item, null, true)

        val nameText = rowView.findViewById(R.id.title) as TextView
        val pageText = rowView.findViewById(R.id.page) as TextView
        val lastDateText = rowView.findViewById(R.id.last_date) as TextView

        nameText.text = name[position]
        pageText.text = page[position]
        lastDateText.text = lastDate[position]

        return rowView
    }
}