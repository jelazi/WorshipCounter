package cz.lubin.worshipcounter

import android.os.Bundle
import android.preference.*
import android.view.MenuItem

class SettingsActivity : AppCompatPreference()  {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // load settings fragment
        fragmentManager.beginTransaction().replace(android.R.id.content, MainPreferenceFragment()).commit()

        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Nastavení"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        FtpWorshipClient.setDefaultFtpPreferences(this)
        MailManager.setDefaultMailPreferences(this)
    }





    class MainPreferenceFragment : PreferenceFragment() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.preference_main)

            // gallery EditText change listener
            bindPreferenceSummaryToValue(findPreference(getString(R.string.ftp_address_key)))
            bindPreferenceSummaryToValue(findPreference(getString(R.string.ftp_password_key)))
            bindPreferenceSummaryToValue(findPreference(getString(R.string.ftp_user_name_key)))
            bindPreferenceSummaryToValue(findPreference(getString(R.string.ftp_directory_today_key)))
            bindPreferenceSummaryToValue(findPreference(getString(R.string.ftp_directory_backup_key)))

            bindPreferenceSummaryToValue(findPreference(getString(R.string.smtp_address_key)))
            bindPreferenceSummaryToValue(findPreference(getString(R.string.smtp_user_name_key)))
            bindPreferenceSummaryToValue(findPreference(getString(R.string.smtp_password_key)))
            bindPreferenceSummaryToValue(findPreference(getString(R.string.smtp_port_key)))
            bindPreferenceSummaryToValue(findPreference(getString(R.string.mail_from_key)))
            bindPreferenceSummaryToValue(findPreference(getString(R.string.mail_to_key)))
            bindPreferenceSummaryToValue(findPreference(getString(R.string.mail_subject_key)))
            bindPreferenceSummaryToValue(findPreference(getString(R.string.mail_message_key)))
            bindPreferenceSummaryToValue(findPreference(getString(R.string.book_name_key)))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private val TAG = SettingsActivity::class.java!!.getSimpleName()

        private fun bindPreferenceSummaryToValue(preference: Preference) {
            preference.onPreferenceChangeListener = sBindPreferenceSummaryToValueListener

            sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                    .getDefaultSharedPreferences(preference.context)
                    .getString(preference.key, ""))
        }



        private val sBindPreferenceSummaryToValueListener = Preference.OnPreferenceChangeListener { preference, newValue ->
            val stringValue = newValue.toString()

     if (preference is EditTextPreference) {
         preference.setSummary(stringValue)
            } else {
                preference.summary = stringValue
            }
            true
        }
    }


}