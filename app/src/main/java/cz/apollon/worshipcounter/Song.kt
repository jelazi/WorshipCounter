package cz.apollon.worshipcounter

class Song (var name: String, var page: Int){
    var useDates: ArrayList<DateSong> = arrayListOf()
    var ID: Int = 0

    fun compare (anotherSong: Song):Boolean {
        if (this.ID != anotherSong.ID) return false
        if (this.name.compareTo(anotherSong.name, true) != 0) return false
        if (this.page != anotherSong.page) return false
        this.useDates.forEach {
            if (!it.containsSameDate(anotherSong.useDates)) return false
        }
        return true
    }

    fun existsInListBook (listBook: ArrayList<Song>): Boolean {
        listBook.forEach {
            if (this.compare(it)) return true
        }
        return false
    }

}