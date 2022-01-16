package com.example.dynamicscrollabletable

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TableLayout
import android.widget.TableRow
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

class DynamicTable(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs), HorizontalScroll.ScrollViewListener, VerticalScroll.ScrollViewListener {
    var rowHeight: Int = 100
    var columnWidth: Int =  200
    var numRows: Int = 20
    var numColumns: Int = 10

    var fixedRelativeLayout: RelativeLayout? = null
    var headerRelativeLayout: RelativeLayout? = null
    var columnRelativeLayout: RelativeLayout? = null
    var contentRelativeLayout: RelativeLayout? = null

    var fixedTableLayout: TableLayout? = null
    var headerTableLayout: TableLayout? = null
    var columnTableLayout: TableLayout? = null
    var contentTableLayout: TableLayout? = null

    var headerHorizontalScrollView: HorizontalScroll? = null
    var contentHorizontalScrollView: HorizontalScroll? = null

    var columnVerticalScrollView: VerticalScroll? = null
    var contentVerticalScrollView: VerticalScroll? = null

    init {
        inflate(context, R.layout.dynamic_table_layout, this)

        fixedRelativeLayout = findViewById(R.id.fixed_section)
        headerRelativeLayout = findViewById(R.id.header_section)
        columnRelativeLayout = findViewById(R.id.column_section)

        headerTableLayout = findViewById(R.id.header_table_layout)
        columnTableLayout = findViewById(R.id.column_table_layout)
        contentTableLayout = findViewById(R.id.content_table_layout)

        headerHorizontalScrollView = findViewById(R.id.header_scrollview)
        columnVerticalScrollView = findViewById(R.id.column_scrollview)
        contentVerticalScrollView = findViewById(R.id.content_vertical_scrollview)
        contentHorizontalScrollView = findViewById(R.id.content_horizontal_scrollview)

        headerHorizontalScrollView?.let { it.setScrollViewListener(this) }
        contentHorizontalScrollView?.let { it.setScrollViewListener(this) }
        columnVerticalScrollView?.let { it.setScrollViewListener(this) }
        contentVerticalScrollView?.let { it.setScrollViewListener(this) }

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.DynamicTable,
            0, 0)?.apply {
            try {
                rowHeight = getInteger(R.styleable.DynamicTable_rowHeight, 100)
                columnWidth = getInteger(R.styleable.DynamicTable_columnWidth, 200)
                numRows = getInteger(R.styleable.DynamicTable_numRows, 20)
                numColumns = getInteger(R.styleable.DynamicTable_numColumns, 10)

            } finally {
                recycle()
            }

            fixedRelativeLayout?.let { it.layoutParams.height = rowHeight }
            fixedRelativeLayout?.let { it.layoutParams.width = columnWidth }
            headerRelativeLayout?.let { it.layoutParams.height = rowHeight }
            columnRelativeLayout?.let { it.layoutParams.width = columnWidth }
        }
    }

    fun initDimensions(rowHeight: Int, columnWidth: Int) {

        fixedRelativeLayout?.let { it.layoutParams.height = rowHeight }
        fixedRelativeLayout?.let { it.layoutParams.width = columnWidth }
        headerRelativeLayout?.let { it.layoutParams.height = rowHeight }
        columnRelativeLayout?.let { it.layoutParams.width = columnWidth }

    }

    fun addRowColumnName(view:View) {
        fixedRelativeLayout?.addView(view)
    }

    fun addHeader(cellViewList: List<View>) {
        val tableRow = TableRow(context)
        for (view in cellViewList) {
            tableRow.addView(view)
        }
        headerTableLayout?.addView(tableRow)
    }

    fun addRow(columnView: View, cellViewList: List<View>) {
//        val linearLayout = LinearLayout(context)
//        linearLayout.layoutParams = LinearLayout.LayoutParams(
//            LayoutParams.MATCH_PARENT,
//            rowHeight
//        )
//        linearLayout.orientation = LinearLayout.VERTICAL
//        linearLayout.gravity = Gravity.CENTER
//        linearLayout.addView(columnView)

        val tableRow = TableRow(context)
        tableRow.addView(columnView)
        columnTableLayout?.addView(tableRow)

        val contentTableRow = TableRow(context)
        for (view in cellViewList) {
            contentTableRow.addView(view)
        }
        contentTableLayout?.addView(contentTableRow)
    }

    override fun onScrollChanged(
        scrollView: HorizontalScroll?,
        x: Int,
        y: Int,
        oldx: Int,
        oldy: Int
    ) {
        if(scrollView == headerHorizontalScrollView){
            contentHorizontalScrollView?.scrollTo(x,y);
        }
        else if(scrollView == contentHorizontalScrollView){
            headerHorizontalScrollView?.scrollTo(x, y);
        }
    }

    override fun onScrollChanged(
        scrollView: VerticalScroll?,
        x: Int,
        y: Int,
        oldx: Int,
        oldy: Int
    ) {
        if (scrollView == columnVerticalScrollView) {
            contentVerticalScrollView?.scrollTo(x, y)
        } else if (scrollView == contentVerticalScrollView) {
            columnVerticalScrollView?.scrollTo(x, y)
        }
    }
}

