package com.example.apicalling

import android.R.attr.key
import android.content.Context
import android.os.Bundle
import android.telecom.Call
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class TableFragment : Fragment(), View.OnClickListener {
    private lateinit var db: AppDatabase
    private lateinit var table: TableLayout
    private lateinit var logsList: List<Logs>

    private var click: buttonClick? = null

    interface buttonClick {
        fun buttonClicked(view: View)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val myview = inflater.inflate(R.layout.fragment_table, container, false)

        table = myview.findViewById(R.id.table_layout)

        return myview
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = AppDatabase(context!!)

        GlobalScope.launch {
            val data = db.logsDao().getAll()
            logsList = data.asReversed()
            addRecordToTable(logsList)

        }

    }


    fun addRecordToTable(logs: List<Logs>) {

        val trParams = TableRow.LayoutParams(
            TableRow.LayoutParams.MATCH_PARENT,
            100
        )
        trParams.column = 9
        trParams.setMargins(10, 10, 10, 10)

        var position = 0
        for (logsItems in logs) {
            val tableRow = TableRow(requireContext())
            tableRow.layoutParams = trParams

            val idTv = TextView(requireContext())
            idTv.layoutParams = trParams
            idTv.setBackgroundResource(R.drawable.textview_bg_black)
            idTv.gravity = Gravity.CENTER
            idTv.setText(logsItems.id.toString())
            idTv.setPadding(20, 20, 20, 20)
            tableRow.addView(idTv)

            val methodNameTv = TextView(requireContext())
            methodNameTv.layoutParams = trParams
            methodNameTv.setBackgroundResource(R.drawable.textview_bg_black)
            methodNameTv.gravity = Gravity.CENTER
            methodNameTv.setPadding(20, 20, 20, 20)
            methodNameTv.setText(logsItems.methodName)
            tableRow.addView(methodNameTv)

            val urlTv = TextView(requireContext())
            urlTv.layoutParams = trParams
            urlTv.setBackgroundResource(R.drawable.textview_bg_black)
            urlTv.gravity = Gravity.CENTER
            urlTv.setPadding(20, 20, 20, 20)
            urlTv.setText(logsItems.url)
            tableRow.addView(urlTv)

            val plainReqTv = TextView(requireContext())
            plainReqTv.layoutParams = trParams
            plainReqTv.setBackgroundResource(R.drawable.textview_bg_black)
            plainReqTv.gravity = Gravity.CENTER
            plainReqTv.setPadding(20, 20, 20, 20)
            plainReqTv.setText(logsItems.plainRequest)
            tableRow.addView(plainReqTv)

            val plainResponseTv = TextView(requireContext())
            plainResponseTv.layoutParams = trParams
            plainResponseTv.setBackgroundResource(R.drawable.textview_bg_black)
            plainResponseTv.gravity = Gravity.CENTER
            plainResponseTv.setPadding(20, 20, 20, 20)
            plainResponseTv.setText(logsItems.response)
            tableRow.addView(plainResponseTv)

//            val encRequestTv = TextView(requireContext())
//            encRequestTv.layoutParams = trParams
//            encRequestTv.setBackgroundResource(R.drawable.textview_bg_black)
//            encRequestTv.gravity = Gravity.CENTER
//            encRequestTv.setPadding(20, 20, 20, 20)
//            encRequestTv.setText(logsItems.encRequest)
//            tableRow.addView(encRequestTv)
//
//            val encReqKeyTv = TextView(requireContext())
//            encReqKeyTv.layoutParams = trParams
//            encReqKeyTv.setBackgroundResource(R.drawable.textview_bg_black)
//            encReqKeyTv.gravity = Gravity.CENTER
//            encReqKeyTv.setPadding(20, 20, 20, 20)
//            encReqKeyTv.setText(logsItems.encRequestKey)
//            tableRow.addView(encReqKeyTv)
//
//            val encResponseTv = TextView(requireContext())
//            encResponseTv.layoutParams = trParams
//            encResponseTv.setBackgroundResource(R.drawable.textview_bg_black)
//            encResponseTv.gravity = Gravity.CENTER
//            encResponseTv.setPadding(20, 20, 20, 20)
//            encResponseTv.setText(logsItems.encResponse)
//            tableRow.addView(encResponseTv)
//
//            val encResponseKeyTv = TextView(requireContext())
//            encResponseKeyTv.layoutParams = trParams
//            encResponseKeyTv.setBackgroundResource(R.drawable.textview_bg_black)
//            encResponseKeyTv.gravity = Gravity.CENTER
//            encResponseKeyTv.setPadding(20, 20, 20, 20)
//            encResponseKeyTv.setText(logsItems.encResponseKey)
//            tableRow.addView(encResponseKeyTv)

            tableRow.tag = position
            tableRow.setOnClickListener(this@TableFragment)
            position++
            requireActivity().runOnUiThread {
                table.addView(tableRow)
            }
        }
    }

    override fun onClick(view: View?) {
        val row = view?.getParent() as View

        val rowIndex: Int = table.indexOfChild(view)

        Toast.makeText(requireContext(), "click on :" + rowIndex, Toast.LENGTH_LONG).show()
        (requireActivity() as ViewActivity).viewpager.currentItem =1
        val currentFrag= ((requireActivity() as ViewActivity).viewpager.adapter as PagerAdapter)
            .getItem((requireActivity() as ViewActivity).viewpager.currentItem)
        if (currentFrag is DetailFragment){

            val logs = logsList.get(rowIndex)
            currentFrag.setData(logs)
        }


    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        click = context as? buttonClick
    }

    override fun onDetach() {
        super.onDetach()
        click = null
    }

}

