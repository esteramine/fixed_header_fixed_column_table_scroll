package com.example.dynamicscrollabletable

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val dynamic_table: DynamicTable = findViewById(R.id.dynamic_table)
        repeat(40) {
            val textView= TextView(applicationContext)
            textView.text = "yoyoyoyoyoyo"
            val textView2 = TextView(applicationContext)
            textView2.text = "abc"
            val textView3 = TextView(applicationContext)
            textView3.text = "def"
            dynamic_table.addRow(textView, listOf(textView2, textView3))
        }

    }
}