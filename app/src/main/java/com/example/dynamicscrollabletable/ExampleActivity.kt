package com.example.dynamicscrollabletable

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class ExampleActivity : AppCompatActivity() {
    val HEADER_HEIGHT = 90
    val ROW_HEIGHT = 120
    val COLUMN_WIDTH = 320
    val headers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14)
    val data = mapOf(
        listOf("Beast", R.drawable.braves_beast) to listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14),
        listOf("Ding", R.drawable.braves_ding) to listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14),
        listOf("Jack", R.drawable.braves_jack) to listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14),
        listOf("Jacky", R.drawable.braves_jacky) to listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14),
        listOf("Jet", R.drawable.braves_jet) to listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14),
        listOf("Josh", R.drawable.braves_josh) to listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14),
        listOf("Louis", R.drawable.braves_louis) to listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14),
        listOf("Ray", R.drawable.braves_ray) to listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14),
        listOf("Singletary", R.drawable.braves_singletary) to listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14),
        listOf("Smart", R.drawable.braves_smart) to listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14),
        listOf("Ting", R.drawable.braves_ting) to listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14),
        listOf("Winston", R.drawable.braves_winston) to listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14),
        listOf("Zaytsev", R.drawable.braves_zaytsev) to listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14),
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)

        val dynamicTable: DynamicTable = findViewById(R.id.dynamic_table)
        val columnView = layoutInflater.inflate(R.layout.column_cell_view, null)
        val headerView = layoutInflater.inflate(R.layout.header_cell_view, null)
        //dynamicTable.initDimensions(HEADER_HEIGHT, COLUMN_WIDTH)
        dynamicTable.initDimensions(HEADER_HEIGHT, COLUMN_WIDTH)

        val headerViews = arrayListOf<View>()
        for (header in headers) {
            headerViews.add(headerCellView(header.toString()))
        }
        dynamicTable.addHeader(headerViews)

        for (item in data) {
            val itemColumn = columnCellView(item.key[0] as String, item.key[1] as Int)
            val cellViewList = arrayListOf<View>()
            for (e in item.value) {
                cellViewList.add(contentCellView(e.toString()))
            }
            dynamicTable.addRow(itemColumn, cellViewList)

        }


    }

    private fun columnCellView(name: String, img: Int): View {
        val view = layoutInflater.inflate(R.layout.column_cell_view, null)
        val playerName: TextView = view.findViewById(R.id.player_name)
        playerName.text = name
        val playerImg: ImageView = view.findViewById(R.id.player_image)
        playerImg.setImageResource(img)

        return view
    }

    private fun contentCellView(text: String): View {
        val view = layoutInflater.inflate(R.layout.content_cell_view, null)
        val data: TextView = view.findViewById(R.id.player_data)
        data.text = text
        return view
    }

    private fun headerCellView(text: String): View {
        val view = layoutInflater.inflate(R.layout.header_cell_view, null)
        val data: TextView = view.findViewById(R.id.player_data)
        data.text = text
        return view
    }
}