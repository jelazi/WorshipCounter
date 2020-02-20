package cz.apollon.worshipcounter

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout

class PartDayItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes) {

    var id: Int? = null

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.part_day_item, this, true)
        orientation = HORIZONTAL
    }
}