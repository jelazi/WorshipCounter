package cz.apollon.worshipcounter
import java.io.Serializable

class Song (var name: String, var page: Int) : Serializable {
    var useDates: ArrayList<MyDate> = arrayListOf()
    var id: Int = 0

    fun compare (anotherSong: Song):Boolean {
        if (this.id != anotherSong.id) return false
        if (this.name.compareTo(anotherSong.name, true) != 0) return false
        if (this.page != anotherSong.page) return false
        this.useDates.forEach {
            if (!it.containsSameDate(anotherSong.useDates)) return false
        }
        return true
    }

    fun addDate(date: MyDate): TypeDialog {
        if (date.containsSameDate(useDates)) return TypeDialog.DATE_USED
        useDates.add(date)
        return TypeDialog.NO_DIALOG
    }

    fun existsInListBook (listBook: ArrayList<Song>): Boolean {
        listBook.forEach {
            if (this.compare(it)) return true
        }
        return false
    }

    fun getLastDate (): MyDate? {
        if (useDates.isEmpty()) return null
        var lastDate = useDates.get(0)
        useDates.forEach {
            if (it.compareTo(lastDate) == 1) lastDate = it
        }
        return lastDate
    }

    fun getDates(): ArrayList<String> {
        val datesName: ArrayList<String> = arrayListOf()
        useDates.forEach{
            datesName.add(it.toString())
        }
        return datesName
    }

}