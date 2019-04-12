package com.sample.daggerworkmanagersample.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sample.daggerworkmanagersample.R
import com.sample.daggerworkmanagersample.di.Injection
import com.sample.daggerworkmanagersample.network.OPEN_WEATHER_BASE_URL
import com.sample.daggerworkmanagersample.network.OTHER_BASE_URL
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_change.*
import javax.inject.Inject

class ChangeActivity : AppCompatActivity() {

    @Inject
    lateinit var injection: Injection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_change)

        save_button.setOnClickListener {
            // TODO Persist selection - not important for the showcasing the issue

            // Reinitialisation
            injection.reinitCompnent(if(weather.isChecked) OPEN_WEATHER_BASE_URL else OTHER_BASE_URL)

            startActivity(
                Intent(this, MainActivity::class.java)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            )
        }
    }
}
