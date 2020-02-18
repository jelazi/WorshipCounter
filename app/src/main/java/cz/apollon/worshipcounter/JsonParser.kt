package cz.apollon.worshipcounter

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

object JsonParser {

    fun listBookToJson ():String {
        val gson = Gson()
        val gsonPretty = GsonBuilder().setPrettyPrinting().create()

        val jsonListBook: String = gson.toJson(Book.songBook)

        val jsonListBookPretty: String = gsonPretty.toJson(Book.songBook)

        return jsonListBookPretty
    }

    fun jsonToListBook (json: String):ArrayList<Song> {
        val gson = Gson()
        val itemType = object : TypeToken<ArrayList<Song>>() {}.type
        val jsonObject = gson.fromJson<ArrayList<Song>>(json, itemType)
        return jsonObject
    }
}
