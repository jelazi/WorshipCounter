package cz.lubin.worshipcounter


import android.app.Activity
import android.os.Bundle
import android.preference.PreferenceManager
import android.os.Message
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

object MailManager {

    var smtpAddress = ""
    var smtpUserName = ""
    var smtpPassword = ""
    var smtpPort = ""
    lateinit var appExecutors: AppExecutors
    var worshipActivity: WorshipActivity? = null




    fun send(activity: WorshipActivity, to:String, from:String, subject: String, text:String){
        worshipActivity = activity
        var listTo = to.split(",")

        appExecutors.diskIO().execute {
            val props = System.getProperties()
            props.put("mail.smtp.host", smtpAddress)
            props.put("mail.smtp.socketFactory.port", smtpPort)
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory")
            props.put("mail.smtp.auth", "true")
            props.put("mail.smtp.port", smtpPort)

            val session =  Session.getInstance(props,
                object : javax.mail.Authenticator() {
                    //Authenticating the password
                    override fun getPasswordAuthentication(): PasswordAuthentication {
                        return PasswordAuthentication(smtpUserName, smtpPassword)
                    }
                })

            try {
                //Creating MimeMessage object
                val mm = MimeMessage(session)
                //Setting sender address
                mm.setFrom(InternetAddress(from))
                //Adding receiver
                for (mail in listTo) {
                    mm.addRecipient(
                        javax.mail.Message.RecipientType.TO,
                        InternetAddress(mail))
                }

                //Adding subject
                mm.subject = subject
                //Adding message
                mm.setText(text)

                //Sending email
                Transport.send(mm)

                appExecutors.mainThread().execute {
                    val msg: Message = worshipActivity!!.handler.obtainMessage()
                    val bundle = Bundle()
                    bundle.putString("correct", "Zpráva byla poslána")
                    msg.data = bundle
                    worshipActivity!!.handler.sendMessage(msg)
                    //Something that should be executed on main thread.
                }

            } catch (e: MessagingException) {
                e.printStackTrace()
                val msg: Message = worshipActivity!!.handler.obtainMessage()
                val bundle = Bundle()
                bundle.putString("error", "Chyba s posíláním zprávy: " + e.toString())
                msg.data = bundle
                worshipActivity!!.handler.sendMessage(msg)
            }
        }
    }


    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    fun setDefaultMailPreferences (activity: Activity) {
        var preference = PreferenceManager.getDefaultSharedPreferences(activity)
        appExecutors = AppExecutors()

        smtpAddress = preference.getString(activity.getString(R.string.smtp_address_key), activity.getString(R.string.smtp_address_value))
        smtpUserName = preference.getString(activity.getString(R.string.smtp_user_name_key), activity.getString(R.string.smtp_user_name_value))
        smtpPassword = preference.getString(activity.getString(R.string.smtp_password_key), activity.getString(R.string.smtp_password_value))
        smtpPort = preference.getString(activity.getString(R.string.smtp_port_key), activity.getString(R.string.smtp_port_value))

    }

}