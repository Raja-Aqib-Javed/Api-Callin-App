package com.example.apicalling.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apicalling.AppDatabase
import com.example.apicalling.Logs
import com.example.apicalling.R
import com.example.apicalling.listener.OnItemClicked
import kotlinx.android.synthetic.main.activity_logs.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LogsActivity : AppCompatActivity() {
    private lateinit var adapter: LogsAdapter
    private var viewmanager = LinearLayoutManager(this)
    private lateinit var db: AppDatabase
    private lateinit var logsList: List<Logs>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logs)

        logsRecylerview.layoutManager = viewmanager
        db = AppDatabase(this)

        GlobalScope.launch {
            val data = db.logsDao().getAll()
            logsList = data.asReversed()
            adapter = LogsAdapter(logsList,this@LogsActivity,object :OnItemClicked{
                override fun onClick(position: Int) {
                    
                    val intent = Intent(this@LogsActivity,DetailActivity::class.java)
                    intent.putExtra("id",logsList.get(position).id)
                    intent.putExtra("mname",logsList.get(position).methodName)
                    intent.putExtra("url",logsList.get(position).url)
                    intent.putExtra("request",logsList.get(position).plainRequest)
                    intent.putExtra("response",logsList.get(position).response)
                    startActivity(intent)


                }

            })

            this@LogsActivity.runOnUiThread{
                logsRecylerview.adapter = adapter
            }

        }


    }

}