package cz.apollon.worshipcounter

object Books {

    var songBook: ArrayList<Song> = arrayListOf()
    var worshipDayBook: ArrayList<WorshipDay> = arrayListOf()
    var countSongID: Int = 0
    private set


    fun addSong (song: Song): Int {
        if (isSameName(song)) {
            return -1
        }
        if (isSamePage(song)) {
            return -2
        }

        song.ID = ++countSongID
        songBook.add(song)
        return 1
    }

    fun changeSong (song: Song) {
        var index = getSongIndexByID(song.ID)

        if (songBook[index!!].name.compareTo(song.name) != 0) {
            songBook[index!!].name = song.name
        }
        if (songBook[index!!].page != song.page) {
            songBook[index!!].page = song.page
        }
    }


    fun removeSong (song: Song) {
        songBook.forEach {
            if (it.ID == song.ID) {
                songBook.remove(it)
                countSongID--
            }
        }
    }

    private fun isSameName (song: Song): Boolean {
        songBook.forEach {
            if (it.name.compareTo(song.name, true) == 0) {
                return true
            }
        }
        return false
    }

    private fun isSamePage (song: Song): Boolean {
        songBook.forEach {
            if (it.page == song.page) {
                return true
            }
        }
        return false
    }

    fun sortByLastDate () {
        var sortedList = songBook.sortedWith(compareBy {  it.getLastDate()})
        songBook = arrayListOf()
        sortedList.forEach {
            songBook.add(it)
        }
    }

    fun getSongByID (id: Int): Song? {
        songBook.forEach {
            if (it.ID == id) return it
        }
        return null
    }

    fun getSongIndexByID (id: Int): Int? {
        for (i in songBook.indices) {
            if (songBook[i].ID == id) {
                return i
            }
        }
        return null
    }

    fun getSongByName (name: String): Song? {
        songBook.forEach {
            if (it.name.compareTo(name) == 0) {
                return it
            }
        }
        return null
    }

}