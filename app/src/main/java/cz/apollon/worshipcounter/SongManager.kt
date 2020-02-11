package cz.apollon.worshipcounter

object SongManager {


    fun createDefaultSongBook () {
        var song1 = Song("Ať požehnán je Bůh", 1)
        var song2 = Song("Dobrořeč duše má Hospodinu", 2)
        var song3 = Song("Haleluja, sláva", 3)
        var song4 = Song("Hosana - Už vidím krále slávy", 4)
        var song5 = Song("Chceme Ti říct", 5)
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

    fun getSongbookNames (): Array<String> {
        var songbookNames: Array<String> = arrayOf()
        SongBook.listBook.forEach {
            songbookNames += it.name
        }
        return songbookNames
    }
}