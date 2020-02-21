package cz.lubin.worshipcounter

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.File

object JsonParser {

    fun songBookToJson ():String {
        val gsonPretty = GsonBuilder().setPrettyPrinting().create()
        val jsonListBookPretty: String = gsonPretty.toJson(Books.songBook)
        return jsonListBookPretty
    }

    fun jsonToSongBook (json: String):ArrayList<Song> {
        val gson = Gson()
        val itemType = object : TypeToken<ArrayList<Song>>() {}.type
        val jsonObject = gson.fromJson<ArrayList<Song>>(json, itemType)
        return jsonObject
    }

    fun getFileJson (context: Context): File {
        val songsJson = songBookToJson()
        val path = context.getFilesDir()
        val letDirectory = File(path, "worshipJsons")
        letDirectory.mkdirs()
        val file = File(letDirectory, "today.json")
        file.writeText(songsJson)
        return file
    }
}
