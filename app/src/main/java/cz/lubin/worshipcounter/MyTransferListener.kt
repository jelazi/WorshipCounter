package cz.lubin.worshipcounter

import android.os.Bundle
import android.os.Message
import android.util.Log
import it.sauronsoftware.ftp4j.FTPDataTransferListener

class MyTransferListener : FTPDataTransferListener{

    var ftpWorshipClient: FtpWorshipClient? = null

    fun init (ftpWorship: FtpWorshipClient) {
        ftpWorshipClient = ftpWorship
    }

    override fun transferred(p0: Int) {

    }

    override fun started() {


    }

    override fun completed() {

    }

    override fun failed() {

    }

    override fun aborted() {

    }

}