package cz.apollon.worshipcounter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SongActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song)
        var button:Button = findViewById(R.id.button)
    }
}
