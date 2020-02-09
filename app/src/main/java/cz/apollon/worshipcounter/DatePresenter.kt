package cz.apollon.worshipcounter

import java.util.*

class DatePresenter {

    companion object {

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val dayOfWeek = c.get(Calendar.DAY_OF_WEEK)
        var nameDay = getNameDayOfWeek(dayOfWeek)
        var nameMonth = getNameMonth(month)

        fun getCurrentDate(): String {

            var currentDate : String = ""
            currentDate = "" + nameDay + " " + day + ". " + month + ". " + year
            return currentDate
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

        private fun getNameMonth(month: Int): String {
            when (month) {
                0->return "Leden"
                1->return "Únor"
                2->return "Březen"
                3->return "Duben"
                4->return "Květen"
                5->return "Červen"
                6->return "Červenec"
                7->return "Srpen"
                8->return "Září"
                9->return "Říjen"
                10->return "Listopad"
                11->return "Prosinec"
            }
            return ""
        }


    }




}