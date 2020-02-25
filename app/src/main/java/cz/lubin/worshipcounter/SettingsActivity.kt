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
    }

    override fun onBackPressed() {
        super.onBackPressed()
        FtpWorshipClient.setDefaultFtpPreferences(this)
    }



    class MainPreferenceFragment : PreferenceFragment() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.preference_main)

            // gallery EditText change listener
            bindPreferenceSummaryToValue(findPreference(getString(R.string.ftp_address_key)))
            bindPreferenceSummaryToValue(findPreference(getString(R.string.ftp_password_key)))
            bindPreferenceSummaryToValue(findPreference(getString(R.string.ftp_user_name_key)))
            bindPreferenceSummaryToValue(findPreference(getString(R.string.ftp_directory_key)))

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