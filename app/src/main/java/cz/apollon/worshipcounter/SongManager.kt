package cz.apollon.worshipcounter

object SongManager {


    fun createDefaultSongBook () {
        var song1 = Song("Ať požehnán je Bůh", 1)
        song1.useDates.add(DateSong(20, 12, 2012))
        song1.useDates.add(DateSong(21, 12, 2012))
        song1.useDates.add(DateSong(20, 1, 2012))
        var song2 = Song("Dobrořeč duše má Hospodinu", 2)
        song2.useDates.add(DateSong(13, 5, 2020))
        song2.useDates.add(DateSong(30, 6, 2020))
        song2.useDates.add(DateSong(8, 2, 2020))
        var song3 = Song("Haleluja, sláva", 3)
        song3.useDates.add(DateSong(20, 12, 2018))
        song3.useDates.add(DateSong(21, 12, 2018))
        song3.useDates.add(DateSong(20, 1, 2018))
        var song4 = Song("Hosana - Už vidím krále slávy", 4)
        song4.useDates.add(DateSong(1, 5, 2011))
        song4.useDates.add(DateSong(2, 5, 2011))
        song4.useDates.add(DateSong(3, 1, 2011))
        var song5 = Song("Chceme Ti říct", 5)
        song5.useDates.add(DateSong(1, 1, 2019))
        song5.useDates.add(DateSong(2, 12, 2019))
        song5.useDates.add(DateSong(3, 4, 2019))
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

        SongBook.addSong(song1)
        SongBook.addSong(song2)
        SongBook.addSong(song3)
        SongBook.addSong(song4)
        SongBook.addSong(song5)
        SongBook.addSong(song6)
        SongBook.addSong(song7)
        SongBook.addSong(song8)
        SongBook.addSong(song9)
        SongBook.addSong(song10)
        SongBook.addSong(song11)
        SongBook.addSong(song12)
        SongBook.addSong(song13)
        SongBook.addSong(song14)
        SongBook.addSong(song15)
        SongBook.addSong(song16)
        SongBook.addSong(song17)
        SongBook.addSong(song18)
        SongBook.addSong(song19)
        SongBook.addSong(song20)
    }

    fun getSongbookItems (nameItems: String): Array<String> {
        var songbookNames: Array<String> = arrayOf()
        if (nameItems == "name") {
            SongBook.listBook.forEach {
                songbookNames += it.name
            }
        } else if (nameItems == "ID") {
            SongBook.listBook.forEach {
                songbookNames += it.ID.toString()
            }
        } else if (nameItems == "page") {
            SongBook.listBook.forEach {
                songbookNames += it.page.toString()
            }
        } else if (nameItems == "lastDate") {
            SongBook.listBook.forEach {
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
                if (firstSong.ID == it.ID) {
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
                if (firstSong.ID == it.ID) {
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

}