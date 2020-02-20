package cz.apollon.worshipcounter

class WorshipDay (date: MyDate){
    var date = date
    var orders: ArrayList<Int> = arrayListOf()
    private set
    var songs: ArrayList<Song> = arrayListOf()
    private set
    var namesPart: ArrayList<String> = arrayListOf()
    private set

    fun addSong (order: Int, song: Song, namePart: String) {
        orders.add(order)
        songs.add(song)
        namesPart.add(namePart)
    }

    fun changeSong (order: Int, song: Song, namePart: String) {
        eraseSong(order)
        addSong(order, song, namePart)
    }

    fun eraseSong (order: Int) {
        var index = -1
        for(i in orders) {
            if (i == order) {
                index = orders.indexOf(i)
                break
            }
        }
        removeSongByIndex(index)
    }

    fun eraseSong (song: Song) {
        var index = -1
        for(i in songs) {
            if (i.id == song.id) {
                index = songs.indexOf(i)
                break
            }
        }
        removeSongByIndex(index)
    }

    fun eraseSong (namePart: String) {
        var index = -1
        for(i in namesPart) {
            if (i.compareTo(namePart) == 0) {
                index = namesPart.indexOf(i)
                break
            }
        }
        removeSongByIndex(index)
    }

    private fun removeSongByIndex (index: Int) {
        if (index != -1) {
            orders.removeAt(index)
            songs.removeAt(index)
            namesPart.removeAt(index)
        }
    }

}