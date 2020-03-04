package cz.lubin.worshipcounter

import android.app.Activity
import android.preference.PreferenceManager

object Books {

    var songBook: ArrayList<Song> = arrayListOf()
    var worshipDayBook: ArrayList<WorshipDay> = arrayListOf()
    var booksName: ArrayList<String> = arrayListOf()
    var countSongID: Int = 0

    fun initItems (activity: Activity) {
        val loadSongBook = BooksManager.getSongBookFromPreferences(activity)
        if (!loadSongBook.isNullOrEmpty()) {
           songBook = loadSongBook
        }
        val loadDayBook = BooksManager.getWorshipDayBookFromPreferences(activity)
        if (!loadDayBook.isNullOrEmpty()) {
            worshipDayBook = loadDayBook
        }

        val preference = PreferenceManager.getDefaultSharedPreferences(activity)

        val loadBooksName:String = preference.getString(activity.getString(R.string.book_name_key), activity.getString(R.string.book_name_value))!!
        booksName = ArrayList(loadBooksName.split(","))

        setLastId()
    }


    fun addSong (song: Song): TypeDialog {
        if (isSameName(song)) {
            return TypeDialog.IS_SAME_SONG
        }
        val array = BooksManager.getArrayOneBook(song.book)
        if (isSamePage(song, array)) {
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
        if (songBook[index!!].book.compareTo(song.book) != 0) {
            songBook[index].book = song.book
        }
        if (songBook[index!!].webPage.compareTo(song.webPage) != 0) {
            songBook[index].webPage = song.webPage
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

    fun isSameName (song: Song): Boolean {
        songBook.forEach {
            if (it.name.compareTo(song.name, true) == 0) {
                return true
            }
        }
        return false
    }

    fun isSameName (name: String): Boolean {
        songBook.forEach {
            if (it.name.compareTo(name, true) == 0) {
                return true
            }
        }
        return false
    }

    fun isSamePage (song: Song, array:ArrayList<Song>): Boolean {
        array.forEach {
            if (it.page == song.page) {
                return true
            }
        }
        return false
    }

    fun isSamePage (page: Int, array:ArrayList<Song>):Boolean {
        for (song in array) {
            if (song.page == page) {
                return true
            }
        }
        return false
    }

    fun sortByLastDate () {
        val firstSortedList = songBook.sortedWith ( compareBy {it.name} )
        val sortedList = firstSortedList.sortedWith(compareBy {  it.getLastDate()})
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