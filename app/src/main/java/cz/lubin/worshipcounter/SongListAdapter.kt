package cz.lubin.worshipcounter

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class SongListAdapter(context: Context, arrayList: ArrayList<HashMap<String, String>>) : BaseAdapter() {
    var arrayList = arrayList
    var context = context
    var tempNameVersionList = ArrayList(arrayList)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var myview = convertView
        val holder: ViewHolder

        if (convertView == null) {
            val mInflater = (context as Activity).layoutInflater

            myview = mInflater!!.inflate(R.layout.song_item, parent, false)

            holder = ViewHolder()
            holder.pageHeader = myview!!.findViewById<TextView>(R.id.page) as TextView
            holder.title = myview!!.findViewById<TextView>(R.id.title) as TextView
            holder.lastDate = myview!!.findViewById<TextView>(R.id.last_date) as TextView
            holder.songBook = myview!!.findViewById(R.id.song_book) as TextView

            myview.setTag(holder)
        } else {
            holder = myview!!.getTag() as ViewHolder
        }

        val map = arrayList.get(position)

        holder.pageHeader!!.setText(map.get("page"))
        holder.title!!.setText(map.get("name"))
        holder.lastDate!!.setText(map.get("lastDate"))
        holder.songBook!!.setText(map.get("songBook"))

        return myview
    }

    override fun getItem(p0: Int): Any {
        return arrayList.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return arrayList.size
    }

    class ViewHolder {

        var pageHeader: TextView? = null
        var title: TextView? = null
        var lastDate: TextView? = null
        var songBook: TextView? = null
    }

    fun filter(text: String?) {

        val text = text!!.toLowerCase(Locale.getDefault())
        val arrayText = text!!.toLowerCase(Locale.getDefault()).split(" ")

        arrayList.clear()

        if (text.length == 0) {
            arrayList.addAll(tempNameVersionList)
        } else {
            for (i in 0..tempNameVersionList.size - 1) {
            var isCorrect = true
                for (tex in arrayText) {
                    if (!tempNameVersionList.get(i).get("name")!!.toLowerCase(Locale.getDefault()).contains(tex)) {
                        isCorrect = false
                    }
                }
                if (isCorrect) {
                    arrayList.add(tempNameVersionList.get(i))
                }
            }
        }
        notifyDataSetChanged()
    }
}