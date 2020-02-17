package cz.apollon.worshipcounter




class DateSong (var day: Int, var month: Int, var year: Int) : Comparable<DateSong>{

    fun isSame(anotherDateSong: DateSong):Boolean {
        if (this.day != anotherDateSong.day) return false
        if (this.month != anotherDateSong.month) return false
        if (this.year != anotherDateSong.year) return false
        return true
    }

    fun containsSameDate(arrayDateSong: ArrayList<DateSong>): Boolean {
        arrayDateSong.forEach {
            if (isSame(it)) return true
        }
        return false
    }


    override operator fun compareTo(other: DateSong): Int {
        if (this.year > other.year) return 1
        if (this.year < other.year) return -1
        if (this.month > other.month) return 1
        if (this.month < other.month) return -1
        if (this.day > other.day) return 1
        if (this.day < other.day) return -1
        return 0
    }

    override  fun toString(): String {
    return "" + day.toString() + ". " + DatePresenter.getNameMonth(month - 1) + " " + year.toString()
    }


}


