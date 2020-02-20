package cz.apollon.worshipcounter

import android.app.Activity

object SongManager {


    private var PRIVATE_MODE = 0
    private val PREF_NAME = "songbook_pref"


    fun createDefaultSongBook () {
        var song1 = Song("Ať požehnán je Bůh", 1)
        song1.useDates.add(MyDate(20, 12, 2012))
        song1.useDates.add(MyDate(21, 12, 2012))
        song1.useDates.add(MyDate(20, 1, 2012))
        var song2 = Song("Dobrořeč duše má Hospodinu", 2)
        song2.useDates.add(MyDate(13, 5, 2020))
        song2.useDates.add(MyDate(30, 6, 2020))
        song2.useDates.add(MyDate(8, 2, 2020))
        var song3 = Song("Haleluja, sláva", 3)
        song3.useDates.add(MyDate(20, 12, 2018))
        song3.useDates.add(MyDate(21, 12, 2018))
        song3.useDates.add(MyDate(20, 1, 2018))
        var song4 = Song("Hosana - Už vidím krále slávy", 4)
        song4.useDates.add(MyDate(1, 5, 2011))
        song4.useDates.add(MyDate(2, 5, 2011))
        song4.useDates.add(MyDate(3, 1, 2011))
        var song5 = Song("Chceme Ti říct", 5)
        song5.useDates.add(MyDate(1, 1, 2019))
        song5.useDates.add(MyDate(2, 12, 2019))
        song5.useDates.add(MyDate(3, 4, 2019))
        var song6 = Song("Chceš-li víru mít", 6)
        var song7 = Song("Chtěl bych blíž Tě mít. Jsi vším, co mám", 7)
        var song8 = Song("Chval ho, ó duše má", 8)
        var song9 = Song("Chval Pána na výšinách", 9)
        var song10 = Song("Immanuel - společně Tě na tomto místě", 10)
        var song11 = Song("Immanuel – Se vším, co mám", 11)
        var song12 = Song("Já se vzdávám", 12)
        var song13 = Song("Jak úžasný Bůh", 13)
        var song14 = Song("Jen v Kristu", 14)
        var song15 = Song("Jen v Tebe důvěru mám", 15)
        var song16 = Song("Ježíši, mám Tě rád", 16)
        var song17 = Song("Kde se křesťané", 17)
        var song18 = Song("Kéž poznají nás po ovoci", 18)
        var song19 = Song("Kéž se v našich domech tančí", 19)
        var song20 = Song("Můj Králi, má spáso", 20)

        Books.addSong(song1)
        Books.addSong(song2)
        Books.addSong(song3)
        Books.addSong(song4)
        Books.addSong(song5)
        Books.addSong(song6)
        Books.addSong(song7)
        Books.addSong(song8)
        Books.addSong(song9)
        Books.addSong(song10)
        Books.addSong(song11)
        Books.addSong(song12)
        Books.addSong(song13)
        Books.addSong(song14)
        Books.addSong(song15)
        Books.addSong(song16)
        Books.addSong(song17)
        Books.addSong(song18)
        Books.addSong(song19)
        Books.addSong(song20)

        Books.sortByLastDate()
    }

    fun resetData () {
        Books.songBook = arrayListOf()
        Books.worshipDayBook = arrayListOf()
    }

    fun saveDaysData (dayList: WorshipDay): TypeDialog {
        if (dayList.songs.isEmpty()) return TypeDialog.LIST_EMPTY
        var result = TypeDialog.DATA_SAVED
        var wrongIndex = 0
        dayList.songs.forEach {
            if (Books.getSongByID(it.id)?.addDate(dayList.date)!! == TypeDialog.DATE_USED) {
                wrongIndex++
            }
        }
        if (wrongIndex > 0) result = TypeDialog.DATE_USED
        if (wrongIndex == dayList.songs.size) result = TypeDialog.ALL_DATE_USED
        return result
    }


    fun controlData (): TypeDialog {
        if (Books.songBook.isEmpty()) {
            return TypeDialog.EMPTY_DATA
        }
        return TypeDialog.NO_DIALOG
    }


    fun getSongbookItems (nameItems: String): Array<String> {
        var songbookNames: Array<String> = arrayOf()
        if (nameItems == "name") {
            Books.songBook.forEach {
                songbookNames += it.name
            }
        } else if (nameItems == "ID") {
            Books.songBook.forEach {
                songbookNames += it.id.toString()
            }
        } else if (nameItems == "page") {
            Books.songBook.forEach {
                songbookNames += it.page.toString()
            }
        } else if (nameItems == "lastDate") {
            Books.songBook.forEach {
                if (it.useDates.isEmpty()) {
                    songbookNames += "---"
                } else {
                    songbookNames += it.getLastDate().toString()
                }
            }
        }

        return songbookNames
    }


    fun compareListBooks (firstBook: ArrayList<Song>, secondBook: ArrayList<Song>):Boolean {
        var isID: Boolean = false
        firstBook.forEach {
            var firstSong = it
            isID = false
            secondBook.forEach {
                if (firstSong.id == it.id) {
                    isID = true
                    if (!it.compare(firstSong)) return false
                }
            }
            if (!isID) return false
        }
        return true
    }


    fun getChangingSongs (firstBook: ArrayList<Song>, secondBook: ArrayList<Song>, isRecursive: Boolean):ArrayList<Song> {
        var changingSongs: ArrayList<Song> = arrayListOf()
        var isID: Boolean = false
        firstBook.forEach {
            var firstSong = it
            isID = false
            secondBook.forEach {
                if (firstSong.id == it.id) {
                    isID = true
                    if (!it.compare(firstSong) && !it.existsInListBook(changingSongs)) changingSongs.add(firstSong)
                }
            }
            if (!isID && !firstSong.existsInListBook(changingSongs)) changingSongs.add(firstSong)
        }
        if (isRecursive) {
            changingSongs.addAll(getChangingSongs(secondBook, firstBook, false))

        }
        return changingSongs
    }

    fun getSongBookFromPreferences (activity: Activity): ArrayList<Song> {
       var sharedPref = activity.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        var songBookString: String = sharedPref.getString(PREF_NAME, "")
        return JsonParser.jsonToSongBook(songBookString)
    }

    fun setSongBookToPreferences (activity: Activity): Boolean {
        val songBookString: String = JsonParser.songBookToJson()
        var sharedPref = activity.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        val editor = sharedPref.edit()
        editor.putString(PREF_NAME, songBookString)
        editor.apply()
        return true
    }

}