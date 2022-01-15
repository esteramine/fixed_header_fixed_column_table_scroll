package com.example.dynamicscrollabletable

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.ViewGroup
import android.widget.TableLayout

import android.widget.RelativeLayout
import android.widget.TableRow

import android.view.Gravity

import android.widget.TextView

class MainActivity : AppCompatActivity(), HorizontalScroll.ScrollViewListener, VerticalScroll.ScrollViewListener {
    private var SCREEN_HEIGHT = 0
    private var SCREEN_WIDTH = 0
    var mainRelativeLayout: RelativeLayout? = null

    var fixedRelativeLayout: RelativeLayout? = null
    var headerRelativeLayout: RelativeLayout? = null
    var columnRelativeLayout: RelativeLayout? = null
    var contentRelativeLayout: RelativeLayout? = null

    var fixedTableLayout: TableLayout? = null
    var headerTableLayout: TableLayout? = null
    var columnTableLayout: TableLayout? = null
    var contentTableLayout: TableLayout? = null

    var tableRow: TableRow? = null
    var headerTableRow: TableRow? = null

    var headerHorizontalScrollView: HorizontalScroll? = null
    var contentHorizontalScrollView: HorizontalScroll? = null

    var columnVerticalScrollView: VerticalScroll? = null
    var contentVerticalScrollView: VerticalScroll? = null
    var headerTableColumnCount = 0
    var columnTableRowCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainRelativeLayout = findViewById(R.id.main_relative_layout)
        getScreenDimensions()
        initFixedSection()
        initHeaderSection()
        initColumnSection()
        initContentSection()
        initScrollViewListeners()

        addRowToFixedSection();
        initRowForHeaderTable();

        for (i in 0..9) {
            addColumnsToHeader("Head $i", i)
        }
        for (i in 0..19) {
            addRowToColumn("Row $i")
            initContentTableRow(i)
            for (j in 0 until headerTableColumnCount) {
                addColumnToContent(i, "($i, $j)")
            }
        }

    }

    private fun getScreenDimensions() {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        SCREEN_WIDTH = displayMetrics.widthPixels
        SCREEN_HEIGHT = displayMetrics.heightPixels
    }

    private fun initRelativeLayout(
        layout: RelativeLayout,
        layoutParams: RelativeLayout.LayoutParams,
        layoutRules: Map<Int, Int>,
        parentView: ViewGroup
    ) {
        layout.apply {
            setPadding(0, 0, 0, 0)
        }
        for (rule in layoutRules) {
            layoutParams.addRule(rule.key, rule.value)
        }
        parentView!!.addView(layout, layoutParams)
    }

    private fun initGeneralLayout(
        layout: ViewGroup,
        layoutParams: ViewGroup.LayoutParams,
        parentView: ViewGroup
    ) {
        layout.apply {
            setPadding(0, 0, 0, 0)
        }
        parentView!!.addView(layout, layoutParams)
    }

    private fun initFixedSection() {
        fixedRelativeLayout = RelativeLayout(applicationContext)
        fixedRelativeLayout!!.id = R.id.fixed_relative_layout
        mainRelativeLayout?.let {
            initRelativeLayout(
                fixedRelativeLayout!!,
                RelativeLayout.LayoutParams(
                    SCREEN_WIDTH / 5,
                    SCREEN_HEIGHT / 20
                ),
                emptyMap(),
                it
            )
        }

        fixedTableLayout = TableLayout(applicationContext)
        fixedTableLayout!!.setBackgroundColor(resources.getColor(R.color.teal_200))
        fixedRelativeLayout?.let {
            initGeneralLayout(
                fixedTableLayout!!,
                TableLayout.LayoutParams(SCREEN_WIDTH / 5, SCREEN_HEIGHT / 20),
                it
            )
        }

    }
    private fun initHeaderSection() {
        headerRelativeLayout = RelativeLayout(applicationContext)
        headerRelativeLayout!!.id = R.id.header_relative_layout
        mainRelativeLayout?.let {
            initRelativeLayout(
                headerRelativeLayout!!,
                RelativeLayout.LayoutParams(
                    SCREEN_WIDTH - (SCREEN_WIDTH/5),
                    SCREEN_HEIGHT/20
                ),
                mapOf(RelativeLayout.RIGHT_OF to R.id.fixed_relative_layout),
                it
            )
        }

        headerHorizontalScrollView = HorizontalScroll(applicationContext)
        headerRelativeLayout?.let {
            initGeneralLayout(
                headerHorizontalScrollView!!,
                ViewGroup.LayoutParams(
                    SCREEN_WIDTH - (SCREEN_WIDTH/5),
                    SCREEN_HEIGHT/20
                ),
                it
            )
        }

        headerTableLayout = TableLayout(applicationContext)
        headerTableLayout!!.setBackgroundColor(resources.getColor(R.color.purple_200))
        headerHorizontalScrollView?.let {
            initGeneralLayout(
                headerTableLayout!!,
                TableLayout.LayoutParams(SCREEN_WIDTH - (SCREEN_WIDTH / 5), SCREEN_HEIGHT / 20),
                it
            )
        }

    }
    private fun initColumnSection() {
        columnRelativeLayout = RelativeLayout(applicationContext)
        columnRelativeLayout!!.id = R.id.column_relative_layout
        mainRelativeLayout?.let {
            initRelativeLayout(
                columnRelativeLayout!!,
                RelativeLayout.LayoutParams(
                    SCREEN_WIDTH/5,
                    SCREEN_HEIGHT - (SCREEN_HEIGHT/20)
                ),
                mapOf(RelativeLayout.BELOW to R.id.fixed_relative_layout),
                it
            )
        }

        columnVerticalScrollView = VerticalScroll(applicationContext)
        columnRelativeLayout?.let {
            initGeneralLayout(
                columnVerticalScrollView!!,
                ViewGroup.LayoutParams(
                    SCREEN_WIDTH/5,
                    SCREEN_HEIGHT - (SCREEN_HEIGHT/20)
                ),
                it
            )
        }

        columnTableLayout = TableLayout(applicationContext)
        columnTableLayout!!.setBackgroundColor(resources.getColor(R.color.purple_500))
        columnVerticalScrollView?.let {
            initGeneralLayout(
                columnTableLayout!!,
                TableLayout.LayoutParams(SCREEN_WIDTH / 5, SCREEN_HEIGHT - (SCREEN_HEIGHT / 20)),
                it
            )
        }
    }
    private fun initContentSection() {
        contentRelativeLayout = RelativeLayout(applicationContext)
        contentRelativeLayout!!.id = R.id.content_relative_layout
        mainRelativeLayout?.let {
            initRelativeLayout(
                contentRelativeLayout!!,
                RelativeLayout.LayoutParams(
                    SCREEN_WIDTH - (SCREEN_WIDTH/5),
                    SCREEN_HEIGHT - (SCREEN_HEIGHT/20)
                ),
                mapOf(RelativeLayout.RIGHT_OF to R.id.column_relative_layout, RelativeLayout.BELOW to R.id.header_relative_layout),
                it
            )
        }

        contentVerticalScrollView = VerticalScroll(applicationContext)
        contentRelativeLayout?.let {
            initGeneralLayout(
                contentVerticalScrollView!!,
                ViewGroup.LayoutParams(
                    SCREEN_WIDTH - (SCREEN_WIDTH/5),
                    SCREEN_HEIGHT - (SCREEN_HEIGHT/20)
                ),
                it
            )
        }

        contentHorizontalScrollView = HorizontalScroll(applicationContext)
        contentVerticalScrollView?.let {
            initGeneralLayout(
                contentHorizontalScrollView!!,
                ViewGroup.LayoutParams(
                    SCREEN_WIDTH - (SCREEN_WIDTH/5),
                    SCREEN_HEIGHT - (SCREEN_HEIGHT/20)
                ),
                it
            )
        }

        contentTableLayout = TableLayout(applicationContext)
        contentTableLayout!!.setBackgroundColor(resources.getColor(R.color.purple_700))
        contentHorizontalScrollView?.let {
            initGeneralLayout(
                contentTableLayout!!,
                TableLayout.LayoutParams(SCREEN_WIDTH - (SCREEN_WIDTH / 5), SCREEN_HEIGHT - (SCREEN_HEIGHT / 20)),
                it
            )
        }

    }
    private fun initScrollViewListeners() {
        headerHorizontalScrollView?.setScrollViewListener(this);
        headerHorizontalScrollView?.setHorizontalScrollBarEnabled(false)
        contentHorizontalScrollView?.setScrollViewListener(this)
        contentHorizontalScrollView?.setHorizontalScrollBarEnabled(false)
        columnVerticalScrollView?.setScrollViewListener(this)
        columnVerticalScrollView?.setVerticalScrollBarEnabled(false)
        contentVerticalScrollView?.setScrollViewListener(this)
        contentVerticalScrollView?.setHorizontalScrollBarEnabled(false)
    }

    private fun addRowToFixedSection() {
        val labelNames = TextView(applicationContext)
        labelNames.text = "Row \\ Col."
        labelNames.textSize = resources.getDimension(R.dimen.cell_text_size)

        tableRow = TableRow(applicationContext)
        tableRow!!.gravity = Gravity.CENTER_HORIZONTAL
        tableRow!!.addView(labelNames)

        fixedTableLayout?.let {
            initGeneralLayout(
                tableRow!!,
                TableRow.LayoutParams(SCREEN_WIDTH / 5, SCREEN_HEIGHT / 20),
                it
            )
        }
    }

    private fun initRowForHeaderTable() {
        headerTableRow = TableRow(applicationContext)
        headerTableLayout?.addView(headerTableRow)
    }

    private fun addCell(textContent: String, tableRowLayoutParams: ViewGroup.LayoutParams, parentView: ViewGroup, ) {
        tableRow = TableRow(applicationContext)
        val cellTextView = TextView(applicationContext)
        cellTextView.apply {
            text = textContent
            textSize = resources.getDimension(R.dimen.cell_text_size)
        }
        tableRow?.apply {
            layoutParams = tableRowLayoutParams
            addView(cellTextView)
        }
        parentView.addView(tableRow)
    }

    @Synchronized
    private fun addColumnsToHeader(text: String, id: Int) {
        headerTableRow?.let {
            addCell(text, TableRow.LayoutParams(SCREEN_WIDTH / 5, SCREEN_HEIGHT / 20),
                it
            )
        }
        headerTableColumnCount++
    }

    @Synchronized
    private fun initContentTableRow(pos: Int) {
        tableRow = TableRow(applicationContext)
        tableRow!!.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, SCREEN_HEIGHT / 20)
        contentTableLayout?.addView(tableRow, pos)
    }

    @Synchronized
    private fun addRowToColumn(text: String) {
        val columnTableRow = TableRow(applicationContext)
        addCell(
            text,
            TableRow.LayoutParams(SCREEN_WIDTH / 5, SCREEN_HEIGHT / 20),
            columnTableRow
        )
        columnTableLayout?.let {
            initGeneralLayout(
                columnTableRow,
                TableRow.LayoutParams(SCREEN_WIDTH / 5, SCREEN_HEIGHT / 20),
                it
            )
        }
        columnTableRowCount++
    }

    @Synchronized
    private fun addColumnToContent(rowPos: Int, text: String) {
        val tableRowAdd = contentTableLayout?.getChildAt(rowPos) as TableRow
        addCell(
            text,
            TableRow.LayoutParams(SCREEN_WIDTH / 5, SCREEN_HEIGHT / 20),
            tableRowAdd
        )
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