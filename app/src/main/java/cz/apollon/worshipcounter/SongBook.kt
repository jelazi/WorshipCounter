package cz.apollon.worshipcounter

object SongBook {

    var listBook: ArrayList<Song> = arrayListOf()
    private set
    var count: Int = 0
    private set


    fun addSong (song: Song): Boolean {
        if (isSameName(song)) {
            return false
        }
        if (isSamePage(song)) {
            return false
        }

        song.ID = ++count
        listBook.add(song)
        return true
    }

    fun removeSong (song: Song) {
        listBook.forEach {
            if (it.ID == song.ID) {
                listBook.remove(it)
                count--
            }
        }
    }

    private fun isSameName (song: Song): Boolean {
        listBook.forEach {
            if (it.name.compareTo(song.name, true) == 0) {
                return true
            }
        }
        return false
    }

    private fun isSamePage (song: Song): Boolean {
        listBook.forEach {
            if (it.page == song.page) {
                return true
            }
        }
        return false
    }

    fun sortByLastDate () {
        var sortedList = listBook.sortedWith(compareBy {  it.getLastDate()})
        listBook = arrayListOf()
        sortedList.forEach {
            listBook.add(it)
        }
    }
}