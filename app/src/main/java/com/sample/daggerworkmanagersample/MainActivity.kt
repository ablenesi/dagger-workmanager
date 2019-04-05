package com.sample.daggerworkmanagersample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        hello_button.setOnClickListener {
            WorkManager.getInstance().enqueue(
                OneTimeWorkRequestBuilder<HelloWorldWorker>().build()
            )
        }
        change_button.setOnClickListener {
            startActivity(Intent(this, ChangeActivity::class.java))
        }
    }
}
