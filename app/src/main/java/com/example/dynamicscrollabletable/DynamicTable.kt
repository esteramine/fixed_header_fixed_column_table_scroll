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
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

class DynamicTable(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs), HorizontalScroll.ScrollViewListener, VerticalScroll.ScrollViewListener {
    private var fixedRelativeLayout: RelativeLayout? = null
    private var headerRelativeLayout: RelativeLayout? = null
    private var columnRelativeLayout: RelativeLayout? = null

    private var headerTableLayout: TableLayout? = null
    private var columnTableLayout: TableLayout? = null
    private var contentTableLayout: TableLayout? = null

    private var headerHorizontalScrollView: HorizontalScroll? = null
    private var contentHorizontalScrollView: HorizontalScroll? = null

    private var columnVerticalScrollView: VerticalScroll? = null
    private var contentVerticalScrollView: VerticalScroll? = null

//    private var headerCellView: View? = null
//    private var columnCellView: View? = null
//    private var contentCellView: View? = null

    init {
        inflate(context, R.layout.dynamic_table_layout, this)
        initViews()
        initScrollViewListeners()

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.DynamicTable,
            0, 0)?.apply {
            try {
                val shadow = getInteger(R.styleable.DynamicTable_shadow, 0)
                val headerCardView: CardView = findViewById(R.id.header_card_view)
                headerCardView.cardElevation = shadow.toFloat()
                val columnCardView: CardView = findViewById(R.id.column_card_view)
                columnCardView.cardElevation = shadow.toFloat()

            } finally {
                recycle()
            }
            
            // init default dimensions
            initDimensions(100, 200)
        }
    }

    fun initDimensions(headerHeight: Int, columnWidth: Int) {
        fixedRelativeLayout?.let { it.layoutParams.height = headerHeight }
        fixedRelativeLayout?.let { it.layoutParams.width = columnWidth }
        headerRelativeLayout?.let { it.layoutParams.height = headerHeight }
        columnRelativeLayout?.let { it.layoutParams.width = columnWidth }
    }

//    fun initCellViews(headerCellView: View, columnCellView: View, contentCellView: View) {
//        this.headerCellView = headerCellView
//        this.columnCellView = columnCellView
//        this.contentCellView = contentCellView
//    }


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

//    fun addRow(text: List<List<String>>) { // only if view is init or will be text view in default
//        if (columnCellView == null) { // default text view
//
//        }
//        else {
//            columnCellView.text =
//
//        }
//
//        if (contentCellView == null) { // default cell view
//
//        }
//        else {
//
//        }
//    }

    fun addRow(columnView: View, cellViewList: List<View>) { // directly pass cell view
        val tableRow = TableRow(context)
        tableRow.addView(columnView)
        columnTableLayout?.addView(tableRow)

        val contentTableRow = TableRow(context)
        for (view in cellViewList) {
            contentTableRow.addView(view)
        }
        contentTableLayout?.addView(contentTableRow)
    }

    private fun initViews() {
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
    }

    private fun initScrollViewListeners() {
        headerHorizontalScrollView?.let {
            it.setScrollViewListener(this)
            it.isHorizontalScrollBarEnabled = false
        }
        contentHorizontalScrollView?.let {
            it.setScrollViewListener(this)
            it.isHorizontalScrollBarEnabled = false
        }
        columnVerticalScrollView?.let {
            it.setScrollViewListener(this)
            it.isVerticalScrollBarEnabled = false
        }
        contentVerticalScrollView?.let {
            it.setScrollViewListener(this)
            it.isVerticalScrollBarEnabled = false
        }
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

