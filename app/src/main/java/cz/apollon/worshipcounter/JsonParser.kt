package cz.apollon.worshipcounter

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

object JsonParser {

    fun listBookToJson ():String {
        val gson = Gson()
        val gsonPretty = GsonBuilder().setPrettyPrinting().create()

        val jsonListBook: String = gson.toJson(SongBook.listBook)

        val jsonListBookPretty: String = gsonPretty.toJson(SongBook.listBook)

        return jsonListBookPretty
    }

    fun jsonToListBook (json: String):ArrayList<Song> {
        val gson = Gson()
        val itemType = object : TypeToken<ArrayList<Song>>() {}.type
        val jsonObject = gson.fromJson<ArrayList<Song>>(json, itemType)
        return jsonObject
    }
}
