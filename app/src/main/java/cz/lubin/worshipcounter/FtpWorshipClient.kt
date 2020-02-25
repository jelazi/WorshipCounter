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
    var directory = ""

    var worshipActivity: WorshipActivity? = null

    val mFtpClient = FTPClient()


    fun uploadSongsLibraryToFtp (context: Context, activity: WorshipActivity) {
        val file = JsonParser.getSongBookFileJson(context)
        worshipActivity = activity

        Thread {
            try {
                connectFtp()
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


    private fun connectFtp () {
        mFtpClient.connect(address)

        mFtpClient.login(user, password)
        mFtpClient.type = FTPClient.TYPE_BINARY
        mFtpClient.changeDirectory(directory)
    }

    private fun disconnectFtp () {
        mFtpClient.disconnect(true)
    }

    private fun uploadFileToFtp (file: File) {
        if (mFtpClient.isConnected) {
            mFtpClient.upload(file)
            var list = mFtpClient.list()

        }
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    fun setDefaultFtpPreferences (activity: Activity) {
        var preference = PreferenceManager.getDefaultSharedPreferences(activity)

        address = preference.getString("ftp_address", activity.getString(R.string.ftp_address_value))
        user = preference.getString("ftp_user", activity.getString(R.string.ftp_user_name_value))
        password = preference.getString("ftp_password", activity.getString(R.string.ftp_password_value))
        directory = preference.getString("ftp_directory", activity.getString(R.string.ftp_directory_value))
    }
}