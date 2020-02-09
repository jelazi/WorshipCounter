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
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, selectedYear, selectedMonth, selectedDay ->
            lblDate?.setText(DatePresenter.getSelectedDate(selectedYear, selectedMonth, selectedDay))
        }, year, month, day)
        dpd.show()
    }
}



