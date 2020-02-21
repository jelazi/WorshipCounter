package cz.lubin.worshipcounter

import java.util.*

class DatePresenter {

    companion object {

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val dayOfWeek = c.get(Calendar.DAY_OF_WEEK)
        var nameDay = getNameDayOfWeek(dayOfWeek)

        fun getCurrentDate(): String {
            val currentDate : String = "" + nameDay + " " + day + ". " + getNameMonth(month) + ". " + year
            return currentDate
        }

        fun getCurrentYear(): Int {
            return c.get(Calendar.YEAR)
        }

        fun getCurrentMonth(): Int {
            return c.get(Calendar.MONTH)
        }

        fun getCurrentDay(): Int {
            return c.get(Calendar.DATE)
        }

        fun getSelectedDate(selectedYear: Int, selectedMonth: Int, selectedDay: Int): String{
            val calNow = Calendar.getInstance()
            val calSet = calNow.clone() as Calendar

            calSet[Calendar.DATE] = selectedDay
            calSet[Calendar.MONTH] = selectedMonth
            calSet[Calendar.YEAR] = selectedYear
            nameDay = getNameDayOfWeek(calSet[Calendar.DAY_OF_WEEK])
            return "" + nameDay + " " + selectedDay + ". " + getNameMonth(selectedMonth) + ". " + selectedYear
        }


        private fun getNameDayOfWeek(dayOfWeek: Int): String {
            when (dayOfWeek) {
                1->return "Neděle"
                2->return "Pondělí"
                3->return "Úterý"
                4->return "Středa"
                5->return "Čtvrtek"
                6->return "Pátek"
                7->return "Sobota"
            }
            return ""
        }

        fun getNameMonth(month: Int): String {
            when (month) {
                0->return "leden"
                1->return "únor"
                2->return "březen"
                3->return "duben"
                4->return "květen"
                5->return "červen"
                6->return "červenec"
                7->return "srpen"
                8->return "září"
                9->return "říjen"
                10->return "listopad"
                11->return "prosinec"
            }
            return ""
        }
    }
}