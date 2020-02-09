package cz.apollon.worshipcounter

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    var button: Button? = null
    var lblDate: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button = findViewById(R.id.button)
        lblDate = findViewById(R.id.textView)
     initItems()
    }

    fun initItems() {
        val onClickListener = button?.setOnClickListener {
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



