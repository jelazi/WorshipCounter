package cz.lubin.worshipcounter


import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Message
import android.preference.PreferenceManager
import it.sauronsoftware.ftp4j.FTPClient
import java.io.File

object FtpWorshipClient {


    var address = ""
    var user = ""
    var password = ""
    var directoryToday = ""
    var directoryBackup = ""

    var worshipActivity: WorshipActivity? = null

    val mFtpClient = FTPClient()


    fun uploadSongsLibraryToFtp (context: Context, activity: WorshipActivity) {
        val file = JsonParser.getSongBookFileJson(context)
        worshipActivity = activity


        Thread {
            try {
                connectFtp()
                removeOldData(context)
                uploadFileToFtp(file)



                disconnectFtp()
                val msg: Message = worshipActivity!!.handler.obtainMessage()
                val bundle = Bundle()
                bundle.putString("correct", "Zálohování dat na FTP server proběhlo v pořádku")
                msg.data = bundle
                worshipActivity!!.handler.sendMessage(msg)


            } catch (e: Exception) {
                e.printStackTrace()
                val msg: Message = worshipActivity!!.handler.obtainMessage()
                val bundle = Bundle()
                bundle.putString("error", "Nějaká chyba s posláním souboru: " + e.toString())
                msg.data = bundle
                worshipActivity!!.handler.sendMessage(msg)
            }

        }.start()
    }

    private fun removeOldData (context: Context) {
        var list = mFtpClient.list()
        var array: ArrayList<File> = arrayListOf()
        mFtpClient.changeDirectory(directoryToday)

        for ((index) in list.withIndex()) {
            val path = context.getFilesDir()
            var file = File(path, list.get(index).name)
            mFtpClient.download(list.get(index).name, file)
            array.add(file)
        }

        for ((index) in list.withIndex()) {
            mFtpClient.deleteFile(list.get(index).name)
        }

        mFtpClient.changeDirectory(directoryBackup)
        for ((index) in array.withIndex()) {
            mFtpClient.upload(array.get(index))
        }

    }


    private fun connectFtp () {
        mFtpClient.connect(address)

        mFtpClient.login(user, password)
        mFtpClient.type = FTPClient.TYPE_BINARY
        mFtpClient.changeDirectory(directoryToday)
    }

    private fun disconnectFtp () {
        mFtpClient.disconnect(true)
    }

    private fun uploadFileToFtp (file: File) {
        mFtpClient.changeDirectory(directoryToday)
        if (mFtpClient.isConnected) {
            mFtpClient.upload(file)
            var list = mFtpClient.list()

        }
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    fun setDefaultFtpPreferences (activity: Activity) {
        var preference = PreferenceManager.getDefaultSharedPreferences(activity)

        address = preference.getString(activity.getString(R.string.ftp_address_key), activity.getString(R.string.ftp_address_value))
        user = preference.getString(activity.getString(R.string.ftp_user_name_key), activity.getString(R.string.ftp_user_name_value))
        password = preference.getString(activity.getString(R.string.ftp_password_key), activity.getString(R.string.ftp_password_value))
        directoryToday = preference.getString(activity.getString(R.string.ftp_directory_today_key), activity.getString(R.string.ftp_directory_today_value))
        directoryBackup = preference.getString(activity.getString(R.string.ftp_directory_backup_key), activity.getString(R.string.ftp_directory_backup_value))

    }
}