package com.example.dynamicscrollabletable

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView

class TestActivity : AppCompatActivity() {
    val ROW_HEIGHT = 100
    val COLUMN_WIDTH = 150
    val CELL_WIDTH = 200
    val headers = listOf<String>("col1", "col2", "col3", "col4", "col5", "col6", "col7", "col8", "col9", "col10", "col11")
    val data = mapOf<String, List<String>>(
        "Row1" to listOf<String>("hi", "i", "like", "peanut", "how", "about", "you", "mee", "too", "and", "you"),
        "Row2" to listOf<String>("hii", "i", "like", "peanut", "how", "about", "you", "mee", "too", "and", "you"),
        "Row3" to listOf<String>("hiii", "i", "like", "peanut", "how", "about", "you", "mee", "too", "and", "you"),
        "Row4" to listOf<String>("hiiii", "i", "like", "peanut", "how", "about", "you", "mee", "too", "and", "you"),
        "Row5" to listOf<String>("hiiiiii", "i", "like", "peanut", "how", "about", "you", "mee", "too", "and", "you"),
        "Row6" to listOf<String>("hi", "i", "like", "peanut", "how", "about", "you", "mee", "too", "and", "you"),
        "Row7" to listOf<String>("hidfg", "i", "like", "peanut", "how", "about", "you", "mee", "too", "and", "you"),
        "Row8" to listOf<String>("hidfg", "i", "like", "peanut", "how", "about", "you", "mee", "too", "and", "you"),
        "Row9" to listOf<String>("hi", "i", "like", "peanut", "how", "about", "you", "mee", "too", "and", "you"),
        "Row10" to listOf<String>("hi", "i", "like", "peanut", "how", "about", "you", "mee", "too", "and", "you"),
        "Row11" to listOf<String>("hiwe", "i", "like", "peanut", "how", "about", "you", "mee", "too", "and", "you"),
        "Row12" to listOf<String>("hi", "i", "like", "peanut", "how", "about", "you", "mee", "too", "and", "you"),
        "Row13" to listOf<String>("hisdfxx", "i", "like", "peanut", "how", "about", "you", "mee", "too", "and", "you"),
        "Row14" to listOf<String>("hixvw", "i", "like", "peanut", "how", "about", "you", "mee", "too", "and", "you"),
        "Row15" to listOf<String>("hi", "i", "like", "peanut", "how", "about", "you", "mee", "too", "and", "you"),
        "Row16" to listOf<String>("hi", "i", "like", "peanut", "how", "about", "you", "mee", "too", "and", "you"),
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val dynamic_table: DynamicTable = findViewById(R.id.dynamic_table)
//        repeat(40) {
//            val textView= TextView(applicationContext)
//            textView.text = "yoyoyoyoyoyo"
//            val textView2 = TextView(applicationContext)
//            textView2.text = "abc"
//            val textView3 = TextView(applicationContext)
//            textView3.text = "def"
//            dynamic_table.addRow(textView, listOf(textView2, textView3))
//        }
//        val viewList = arrayListOf<View>()
//        repeat(10) {
//            val textView= TextView(applicationContext)
//            textView.text = "yoyoyoyoyoyo"
//            viewList.add(textView)
//        }
//        dynamic_table.addHeader(viewList)
//
//        val text = TextView(applicationContext)
//        text.text = "row\\column"
//        dynamic_table.addRowColumnName(text)
        val headerViews = arrayListOf<View>()
        for (header in headers) {
            headerViews.add(cellView(header))
        }
        dynamic_table.addHeader(headerViews)

        for (item in data) {
            val itemName = columnCellView(item.key)
            val cellViewList = arrayListOf<View>()
            for (e in item.value) {
                cellViewList.add(cellView(e))
            }
            dynamic_table.addRow(itemName, cellViewList)
        }

    }

    private fun cellView(text: String): TextView {
        val textView = TextView(applicationContext)
        textView.text = text
        textView.width = CELL_WIDTH
        textView.height = ROW_HEIGHT
        textView.gravity = Gravity.CENTER
        return textView
    }

    private fun columnCellView(text: String): TextView {
        val textView = TextView(applicationContext)
        textView.text = text
        textView.width = COLUMN_WIDTH
        textView.height = ROW_HEIGHT
        textView.gravity = Gravity.CENTER
        return textView
    }
}