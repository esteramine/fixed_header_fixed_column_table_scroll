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
        initRelativeLayouts()
        initScrollers()
        initTableLayouts()
        headerHorizontalScrollView?.setScrollViewListener(this);
        headerHorizontalScrollView?.setHorizontalScrollBarEnabled(false)
        contentHorizontalScrollView?.setScrollViewListener(this)
        contentHorizontalScrollView?.setHorizontalScrollBarEnabled(false)
        columnVerticalScrollView?.setScrollViewListener(this)
        columnVerticalScrollView?.setVerticalScrollBarEnabled(false)
        contentVerticalScrollView?.setScrollViewListener(this)
        contentVerticalScrollView?.setHorizontalScrollBarEnabled(false)
        addRowToTableA();
        initializeRowForTableB();

        for (i in 0..8) {
            addColumnsToHeader("Head$i", i)
        }
        for (i in 0..19) {
            initializeContentTableRow(i)
            addRowToColumn("Row$i")
            for (j in 0 until headerTableColumnCount) {
                addColumnToContent(i, "D $i $j")
            }
        }

    }

    private fun getScreenDimensions() {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        SCREEN_WIDTH = displayMetrics.widthPixels
        SCREEN_HEIGHT = displayMetrics.heightPixels
    }

    private fun initRelativeLayouts() {
        // fixed
        fixedRelativeLayout = RelativeLayout(applicationContext)
        mainRelativeLayout?.let {
            initLayout(
                fixedRelativeLayout!!,
                R.id.fixed_relative_layout,
                RelativeLayout.LayoutParams(
                    SCREEN_WIDTH / 5,
                    SCREEN_HEIGHT / 20
                ),
                emptyMap(),
                it
            )
        }

        // header
        headerRelativeLayout = RelativeLayout(applicationContext)
        mainRelativeLayout?.let {
            initLayout(
                headerRelativeLayout!!,
                R.id.header_relative_layout,
                RelativeLayout.LayoutParams(
                    SCREEN_WIDTH - (SCREEN_WIDTH/5),
                    SCREEN_HEIGHT/20
                ),
                mapOf(RelativeLayout.RIGHT_OF to R.id.fixed_relative_layout),
                it
            )
        }

        // column
        columnRelativeLayout = RelativeLayout(applicationContext)
        mainRelativeLayout?.let {
            initLayout(
                columnRelativeLayout!!,
                R.id.column_relative_layout,
                RelativeLayout.LayoutParams(
                    SCREEN_WIDTH/5,
                    SCREEN_HEIGHT - (SCREEN_HEIGHT/20)
                ),
                mapOf(RelativeLayout.BELOW to R.id.fixed_relative_layout),
                it
            )
        }

        // content
        contentRelativeLayout = RelativeLayout(applicationContext)
        mainRelativeLayout?.let {
            initLayout(
                contentRelativeLayout!!,
                R.id.content_relative_layout,
                RelativeLayout.LayoutParams(
                    SCREEN_WIDTH - (SCREEN_WIDTH/5),
                    SCREEN_HEIGHT - (SCREEN_HEIGHT/20)
                ),
                mapOf(RelativeLayout.RIGHT_OF to R.id.column_relative_layout, RelativeLayout.BELOW to R.id.header_relative_layout),
                it
            )
        }
    }

    private fun initLayout(
        layout: RelativeLayout,
        assignedId: Int,
        layoutParams: RelativeLayout.LayoutParams,
        layoutRules: Map<Int, Int>,
        parentView: ViewGroup
    ) {
        layout.apply {
            id = assignedId
            setPadding(0, 0, 0, 0)
        }
        for (rule in layoutRules) {
            layoutParams.addRule(rule.key, rule.value)
        }
        parentView!!.addView(layout, layoutParams)
    }

    private fun initScrollers() {
        headerHorizontalScrollView = HorizontalScroll(applicationContext)
        headerHorizontalScrollView!!.setPadding(0,0,0,0)

        columnVerticalScrollView = VerticalScroll(applicationContext)
        columnVerticalScrollView!!.setPadding(0,0,0,0)

        contentHorizontalScrollView = HorizontalScroll(applicationContext)
        contentHorizontalScrollView!!.setPadding(0,0,0,0)

        contentVerticalScrollView = VerticalScroll(applicationContext)
        contentVerticalScrollView!!.setPadding(0,0,0,0)

        headerHorizontalScrollView!!.layoutParams = ViewGroup.LayoutParams(
            SCREEN_WIDTH - (SCREEN_WIDTH/5),
            SCREEN_HEIGHT/20
        )

        columnVerticalScrollView!!.layoutParams = ViewGroup.LayoutParams(
            SCREEN_WIDTH/5,
            SCREEN_HEIGHT - (SCREEN_HEIGHT/20)
        )

        contentVerticalScrollView!!.layoutParams = ViewGroup.LayoutParams(
            SCREEN_WIDTH - (SCREEN_WIDTH/5),
            SCREEN_HEIGHT - (SCREEN_HEIGHT/20)
        )

        contentHorizontalScrollView!!.layoutParams = ViewGroup.LayoutParams(
            SCREEN_WIDTH - (SCREEN_WIDTH/5),
            SCREEN_HEIGHT - (SCREEN_HEIGHT/20)
        )

        headerRelativeLayout?.addView(headerHorizontalScrollView)
        columnRelativeLayout?.addView(columnVerticalScrollView)
        contentVerticalScrollView!!.addView(contentHorizontalScrollView)
        contentRelativeLayout?.addView(contentVerticalScrollView)
    }

    private fun initScroller() {

    }

    private fun initTableLayouts() {
        fixedTableLayout = TableLayout(applicationContext)
        fixedTableLayout!!.setPadding(0,0,0,0)
        headerTableLayout = TableLayout(applicationContext)
        headerTableLayout!!.setPadding(0,0,0,0)
        columnTableLayout = TableLayout(applicationContext)
        columnTableLayout!!.setPadding(0,0,0,0)
        contentTableLayout = TableLayout(applicationContext)
        contentTableLayout!!.setPadding(0,0,0,0)

        val fixedLayoutParams =
            TableLayout.LayoutParams(SCREEN_WIDTH / 5, SCREEN_HEIGHT / 20)
        fixedTableLayout!!.layoutParams = fixedLayoutParams
        fixedTableLayout!!.setBackgroundColor(resources.getColor(R.color.teal_200))
        fixedRelativeLayout!!.addView(fixedTableLayout)

        val headerLayoutParams =
            TableLayout.LayoutParams(SCREEN_WIDTH - (SCREEN_WIDTH / 5), SCREEN_HEIGHT / 20)
        headerTableLayout!!.layoutParams = headerLayoutParams
        headerTableLayout!!.setBackgroundColor(resources.getColor(R.color.purple_200))
        headerHorizontalScrollView!!.addView(headerTableLayout)

        val columnLayoutParams =
            TableLayout.LayoutParams(SCREEN_WIDTH / 5, SCREEN_HEIGHT - (SCREEN_HEIGHT / 20))
        columnTableLayout!!.layoutParams = columnLayoutParams
        columnTableLayout!!.setBackgroundColor(resources.getColor(R.color.purple_500))
        columnVerticalScrollView!!.addView(columnTableLayout)

        val contentLayoutParams =
            TableLayout.LayoutParams(SCREEN_WIDTH - (SCREEN_WIDTH / 5), SCREEN_HEIGHT - (SCREEN_HEIGHT / 20))
        contentTableLayout!!.layoutParams = contentLayoutParams
        contentTableLayout!!.setBackgroundColor(resources.getColor(R.color.purple_700))
        contentHorizontalScrollView!!.addView(contentTableLayout)
    }


    private fun addRowToTableA() {
        tableRow = TableRow(applicationContext)
        val layoutParamsTableRow = TableRow.LayoutParams(SCREEN_WIDTH / 5, SCREEN_HEIGHT / 20)
        tableRow!!.layoutParams = layoutParamsTableRow
        val label_date = TextView(applicationContext)
        label_date.text = "Item/ID"
        tableRow!!.gravity = Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL
        label_date.textSize = resources.getDimension(R.dimen.cell_text_size)
        tableRow!!.addView(label_date)
        fixedTableLayout?.addView(tableRow)
    }

    private fun initializeRowForTableB() {
        headerTableRow = TableRow(applicationContext)
        tableRow!!.setPadding(0, 0, 0, 0)
        headerTableLayout?.addView(headerTableRow)
    }

    @Synchronized
    private fun addColumnsToHeader(text: String, id: Int) {
        tableRow = TableRow(applicationContext)
        val layoutParamsTableRow = TableRow.LayoutParams(SCREEN_WIDTH / 5, SCREEN_HEIGHT / 20)
        tableRow!!.setPadding(3, 3, 3, 4)
        tableRow!!.layoutParams = layoutParamsTableRow
        val label_date = TextView(applicationContext)
        label_date.text = text
        label_date.textSize = resources.getDimension(R.dimen.cell_text_size)
        tableRow!!.addView(label_date)
        tableRow!!.tag = id
        headerTableRow?.addView(tableRow)
        headerTableColumnCount++
    }

    @Synchronized
    private fun initializeContentTableRow(pos: Int) {
        val tableRowB = TableRow(applicationContext)
        val layoutParamsTableRow =
            TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, SCREEN_HEIGHT / 20)
        tableRowB.setPadding(0, 0, 0, 0)
        tableRowB.layoutParams = layoutParamsTableRow
        contentTableLayout?.addView(tableRowB, pos)
    }

    @Synchronized
    private fun addRowToColumn(text: String) {
        val tableRow1 = TableRow(applicationContext)
        val layoutParamsTableRow1 = TableRow.LayoutParams(SCREEN_WIDTH / 5, SCREEN_HEIGHT / 20)
        tableRow1.setPadding(3, 3, 3, 4)
        tableRow1.layoutParams = layoutParamsTableRow1
        val label_date = TextView(applicationContext)
        label_date.text = text
        label_date.textSize = resources.getDimension(R.dimen.cell_text_size)
        tableRow1.addView(label_date)
        val tableRow = TableRow(applicationContext)
        val layoutParamsTableRow = TableRow.LayoutParams(SCREEN_WIDTH / 5, SCREEN_HEIGHT / 20)
        tableRow.setPadding(0, 0, 0, 0)
        tableRow.layoutParams = layoutParamsTableRow
        tableRow.addView(tableRow1)
        columnTableLayout?.addView(tableRow, columnTableRowCount)
        columnTableRowCount++
    }

    @Synchronized
    private fun addColumnToContent(rowPos: Int, text: String) {
        val tableRowAdd = contentTableLayout?.getChildAt(rowPos) as TableRow
        tableRow = TableRow(applicationContext)
        val layoutParamsTableRow = TableRow.LayoutParams(SCREEN_WIDTH / 5, SCREEN_HEIGHT / 20)
        tableRow!!.setPadding(3, 3, 3, 4)
        //tableRow!!.background = resources.getDrawable(R.drawable.cell_bacground)
        tableRow!!.layoutParams = layoutParamsTableRow
        val label_date = TextView(applicationContext)
        label_date.text = text
        label_date.textSize = resources.getDimension(R.dimen.cell_text_size)
        tableRow!!.tag = label_date
        tableRow!!.addView(label_date)
        tableRowAdd.addView(tableRow)
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