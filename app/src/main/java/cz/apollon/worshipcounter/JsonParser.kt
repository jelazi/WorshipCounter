package cz.apollon.worshipcounter

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

object JsonParser {

    fun songBookToJson ():String {
        val gson = Gson()
        val gsonPretty = GsonBuilder().setPrettyPrinting().create()

        val jsonListBook: String = gson.toJson(Books.songBook)

        val jsonListBookPretty: String = gsonPretty.toJson(Books.songBook)

        return jsonListBookPretty
    }

    fun jsonToSongBook (json: String):ArrayList<Song> {
        val gson = Gson()
        val itemType = object : TypeToken<ArrayList<Song>>() {}.type
        val jsonObject = gson.fromJson<ArrayList<Song>>(json, itemType)
        return jsonObject
    }
}
