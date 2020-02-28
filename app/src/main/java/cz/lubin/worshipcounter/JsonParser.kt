package cz.lubin.worshipcounter

import android.annotation.SuppressLint
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.File
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

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

    fun worshipDayBookToJson ():String {
        val gsonPretty = GsonBuilder().setPrettyPrinting().create()
        val jsonListBookPretty: String = gsonPretty.toJson(Books.worshipDayBook)
        return jsonListBookPretty
    }

    fun jsonToWorshipDayBook (json: String):ArrayList<WorshipDay> {
        val gson = Gson()
        val itemType = object : TypeToken<ArrayList<WorshipDay>>() {}.type
        val jsonObject = gson.fromJson<ArrayList<WorshipDay>>(json, itemType)
        return jsonObject
    }

    @SuppressLint("SimpleDateFormat")
    fun getSongBookFileJson (context: Context): File {
        val songsJson = songBookToJson()
        val path = context.getFilesDir()
        val letDirectory = File(path, "worshipJsons")
        letDirectory.mkdirs()

        val sdf = SimpleDateFormat("yyyy_M_dd_hh_mm_ss")
        val currentDate = sdf.format(Date())

        val file = File(letDirectory, "songs$currentDate.json")
        file.writeText(songsJson)
        return file
    }

    @SuppressLint("SimpleDateFormat")
    fun getDayBookFileJson (context: Context): File {
        val daysJson = worshipDayBookToJson()
        val path = context.getFilesDir()
        val letDirectory = File(path, "worshipJsons")
        letDirectory.mkdirs()

        val sdf = SimpleDateFormat("yyyy_M_dd_hh_mm_ss")
        val currentDate = sdf.format(Date())

        val file = File(letDirectory, "days$currentDate.json")
        file.writeText(daysJson)
        return file
    }

}
