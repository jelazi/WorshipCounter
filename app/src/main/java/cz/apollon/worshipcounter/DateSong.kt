package cz.apollon.worshipcounter



class DateSong (var day: Int, var month: Int, var year: Int){

    fun compare(anotherDateSong: DateSong):Boolean {
        if (this.day != anotherDateSong.day) return false
        if (this.month != anotherDateSong.month) return false
        if (this.year != anotherDateSong.year) return false
        return true
    }

    fun containsSameDate(arrayDateSong: ArrayList<DateSong>): Boolean {
        arrayDateSong.forEach {
            if (compare(it)) return true
        }
        return false
    }
}