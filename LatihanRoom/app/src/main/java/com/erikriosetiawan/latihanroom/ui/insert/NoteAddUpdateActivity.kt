package com.erikriosetiawan.latihanroom.ui.insert

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.erikriosetiawan.latihanroom.R

class NoteAddUpdateActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_NOTE = "extra_note"
        const val EXTRA_POSITION = "extra_position"
        const val REQUEST_ADD = 100
        const val RESULT_ADD = 101
        const val REQUEST_UPDATE = 200
        const val RESULT_UPDATE = 201
        const val RESULT_DELETE = 301
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_add_update)
    }
}