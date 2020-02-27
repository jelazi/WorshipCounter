package cz.lubin.worshipcounter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.*

class SongListAdapter (private val context: Activity, private val songBook:ArrayList<Song>)
    : ArrayAdapter<String>(context, R.layout.song_item, SongManager.getSongbookItems("name"))  {

    var songB: ArrayList<Song> = songBook

    private val filter = SongListFilter(SongManager.getSongbookItems("name", songB))

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.song_item, null, true)

        val nameText = rowView.findViewById(R.id.title) as TextView
        val pageText = rowView.findViewById(R.id.page) as TextView
        val lastDateText = rowView.findViewById(R.id.last_date) as TextView

        nameText.text = SongManager.getSongbookItems("name", songB)[position]
        pageText.text = SongManager.getSongbookItems("page", songB)[position]
        lastDateText.text = SongManager.getSongbookItems("lastDate", songB)[position]

        return rowView
    }


    override fun getFilter() = filter

    inner class SongListFilter(private val originalList: Array<String>) : Filter() {

        private val sourceObjects: ArrayList<String> = ArrayList()

        init {
            synchronized (this) {
                sourceObjects.addAll(originalList)
            }
        }

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            if (constraint == null) return FilterResults()

            val result = FilterResults()

            if (constraint.isNotEmpty()) {
                val filteredList = ArrayList<String>()

                sourceObjects.filterTo(filteredList) { isWithinConstraint(it, constraint) }

                result.count = filteredList.size
                result.values = filteredList

            } else {
                synchronized(this) {
                    result.values = sourceObjects
                    result.count = sourceObjects.size
                }

            }
            return result
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults) {
            if (results.values == null) return

            @Suppress("UNCHECKED_CAST")
            val filtered = results.values as ArrayList<String>

                songB = SongManager.getSongbookByName(filtered, songB)
                notifyDataSetChanged()
                //clear()

        }


        override fun convertResultToString(resultValue: Any?): CharSequence {
            return (resultValue as String)
        }

        private fun isWithinConstraint(registration: String, constraint: CharSequence): Boolean {
            return registration.toLowerCase().contains(constraint, true)
        }

    }
}