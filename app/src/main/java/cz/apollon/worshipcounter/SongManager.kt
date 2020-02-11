package cz.apollon.worshipcounter

object SongManager {


    fun createDefaultSongBook () {
        var song1 = Song("Ať požehnán je Bůh", 1)
        var song2 = Song("Dobrořeč duše má Hospodinu", 2)
        var song3 = Song("Haleluja, sláva", 3)
        var song4 = Song("Hosana - Už vidím krále slávy", 4)
        var song5 = Song("Chceme Ti říct", 5)
        var song6 = Song("Chceš-li víru mít", 6)

        SongBook.addSong(song1)
        SongBook.addSong(song2)
        SongBook.addSong(song3)
        SongBook.addSong(song4)
        SongBook.addSong(song5)
        SongBook.addSong(song6)

    }

}