package cz.apollon.worshipcounter

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    var lblDate: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lblDate = findViewById(R.id.label_date)
        initItems()
        SongManager.createDefaultSongBook()
        var list = SongBook.listBook
        datePicker()



    }

    fun initItems() {
        val onClickListener = lblDate?.setOnClickListener {
            datePicker()
        }
        lblDate?.setText(DatePresenter.getCurrentDate())
    }


    fun datePicker () {
        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, selectedYear, selectedMonth, selectedDay ->
            lblDate?.setText(DatePresenter.getSelectedDate(selectedYear, selectedMonth, selectedDay))
        }, DatePresenter.getCurrentYear(), DatePresenter.getCurrentMonth(), DatePresenter.getCurrentDay())
        dpd.show()
    }
}



