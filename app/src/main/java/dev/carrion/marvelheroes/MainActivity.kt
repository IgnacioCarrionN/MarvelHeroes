package dev.carrion.marvelheroes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/**
 * MainActivity for the MarvelHeroes app.
 *
 * It's a single Activity application. All the logic its on the fragments.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
