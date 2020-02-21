package cz.lubin.worshipcounter


import android.content.Context
import android.os.Bundle
import android.os.Message
import it.sauronsoftware.ftp4j.FTPClient

object FtpWorshipClient {

    const val DECODE_STATE_COMPLETED: Int = 1





    val address = "klokanek.endora.cz"
    val user = "worship"
    val password = "Adventisti777"
    val directory = "/backup/"

    fun connectFtp (context: Context, worshipActivity: WorshipActivity) {
        val file = JsonParser.getFileJson(context)

        Thread {
            try {
                val mFtpClient = FTPClient()

                mFtpClient.connect(address)

                mFtpClient.login(user, password)
                mFtpClient.type = FTPClient.TYPE_BINARY
                mFtpClient.changeDirectory(directory)


                mFtpClient.upload(file)
                var list = mFtpClient.list()
                mFtpClient.disconnect(true)
                var runnable = Runnable {
                    "message"
                }

                val msg: Message = worshipActivity.handler.obtainMessage()
                val bundle = Bundle()
                bundle.putString("correct", "Všechno proběhlo v pořádku")
                msg.data = bundle
                worshipActivity.handler.sendMessage(msg)


            } catch (e: Exception) {
                e.printStackTrace()
                val msg: Message = worshipActivity.handler.obtainMessage()
                val bundle = Bundle()
                bundle.putString("error", "Nějaká chyba s posláním souboru")
                msg.data = bundle
                worshipActivity.handler.sendMessage(msg)
            }

        }.start()
    }


    fun loginFtp (mFtpClient: FTPClient) {

    }


}