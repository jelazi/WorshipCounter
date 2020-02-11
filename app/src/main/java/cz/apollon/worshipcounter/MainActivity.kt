package cz.apollon.worshipcounter

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    var lblDate: TextView? = null
    var btnSongPreview: Button? = null
    var btnSongBookPreview: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lblDate = findViewById(R.id.label_date)
        btnSongPreview = findViewById(R.id.btnSongPreview)
        btnSongBookPreview = findViewById(R.id.btnSongBookPreview)
        initItems()
        SongManager.createDefaultSongBook()
        var list = SongBook.listBook
        datePicker()



    }

    fun initItems() {
        val lblDateListener = lblDate?.setOnClickListener {
            datePicker()
        }
        val btnSongPreviewListener = btnSongPreview?.setOnClickListener {
            openSongPreview()
        }
        val btnSongBookPreviewListener = btnSongBookPreview?.setOnClickListener {
            openSongBookPreview()
        }
        lblDate?.setText(DatePresenter.getCurrentDate())
    }


    fun datePicker () {
        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, selectedYear, selectedMonth, selectedDay ->
            lblDate?.setText(DatePresenter.getSelectedDate(selectedYear, selectedMonth, selectedDay))
        }, DatePresenter.getCurrentYear(), DatePresenter.getCurrentMonth(), DatePresenter.getCurrentDay())
        dpd.show()
    }

    fun openSongPreview () {

    }

    fun openSongBookPreview () {
        val intent = Intent(this, SongBookActivity::class.java)
// To pass any data to next activity
       // intent.putExtra("keyIdentifier", value)
// start your next activity
        startActivity(intent)
    }

}



