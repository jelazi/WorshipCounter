package cz.apollon.worshipcounter

object Books {

    var songBook: ArrayList<Song> = arrayListOf()
    var worshipDayBook: ArrayList<WorshipDay> = arrayListOf()
    var countSongID: Int = 0
    private set


    fun addSong (song: Song): Boolean {
        if (isSameName(song)) {
            return false
        }
        if (isSamePage(song)) {
            return false
        }

        song.ID = ++countSongID
        songBook.add(song)
        return true
    }

    fun changeSong (song: Song) {
        var songForChange = getSongByID(song.ID)

        if (songForChange?.name?.compareTo(song.name) == 0) {
            songForChange?.name = song.name
        }
        if (songForChange?.page == song.page) {
            songForChange?.page = song.page
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

    fun getSongByName (name: String): Song? {
        songBook.forEach {
            if (it.name.compareTo(name) == 0) {
                return it
            }
        }
        return null
    }

}