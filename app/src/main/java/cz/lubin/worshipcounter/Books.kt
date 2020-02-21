package cz.lubin.worshipcounter

import android.app.Activity

object Books {

    var songBook: ArrayList<Song> = arrayListOf()
    var worshipDayBook: ArrayList<WorshipDay> = arrayListOf()
    var countSongID: Int = 0
    private set

    fun initItems (activity: Activity) {
        val loadSongBook = SongManager.getSongBookFromPreferences(activity)
        if (!loadSongBook.isNullOrEmpty()) {
           songBook = loadSongBook
        }
        setLastId()
    }


    fun addSong (song: Song): TypeDialog {
        if (isSameName(song)) {
            return TypeDialog.IS_SAME_SONG
        }
        if (isSamePage(song)) {
            return TypeDialog.IS_SAME_PAGE
        }

        song.id = ++countSongID
        songBook.add(song)
        return TypeDialog.SONG_CREATE
    }

    fun changeSong (song: Song) {
        val index = getSongIndexByID(song.id)

        if (songBook[index!!].name.compareTo(song.name) != 0) {
            songBook[index].name = song.name
        }
        if (songBook[index].page != song.page) {
            songBook[index].page = song.page
        }
    }


    fun removeSong (song: Song) {
        songBook.forEach {
            if (it.id == song.id) {
                songBook.remove(it)
                countSongID--
            }
        }
    }

    fun setLastId () {
        var lastId = countSongID
        songBook.forEach {
            if (it.id > lastId) {
                lastId = it.id
            }
        }
        countSongID = lastId + 1
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
        val sortedList = songBook.sortedWith(compareBy {  it.getLastDate()})
        songBook = arrayListOf()
        sortedList.forEach {
            songBook.add(it)
        }
    }

    fun getSongByID (id: Int): Song? {
        songBook.forEach {
            if (it.id == id) return it
        }
        return null
    }

    fun getSongIndexByID (id: Int): Int? {
        for (i in songBook.indices) {
            if (songBook[i].id == id) {
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