package com.example.apicalling.view

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RemoteViews
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apicalling.*
import com.example.apicalling.viewmodel.MainViewModel
import com.example.apicalling.viewmodel.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    //View Model
    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DataAdapter
    private var viewmanager = LinearLayoutManager(this)
    val factory = MainViewModelFactory()
    // declaring variables
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val CHANNEL_ID = "com.example.apicalling.view"
    val description = "Test notification"
    var logId:Int = 0
    @RequiresApi(Build.VERSION_CODES.KITKAT_WATCH)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        recyclerView = findViewById(R.id.recylerview)
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        recyclerView.layoutManager = viewmanager
        initAdapter()
        setupScrollListener()
        progressBar.visibility = View.GONE

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        viewModel.progressdata.observe(this, Observer {
            if(it == true)
            {
                progressBar.visibility = View.VISIBLE

            }
            else{
                progressBar.visibility = View.GONE
            }
        })
        viewModel.listdata.observe(this, Observer {
            adapter.addItems(it)
        })

        val broadCastReceiver = object : BroadcastReceiver() {
            override fun onReceive(contxt: Context?, intent: Intent?) {
                Toast.makeText(this@MainActivity,"done",Toast.LENGTH_LONG).show()
                //toast("This toast will be shown every X minutes")
            }
        }
        notify.setOnClickListener {

            val db = AppDatabase(this)
            var logsInstance = LogsApplication.getLogs()
            logsInstance.id = logId
            logsInstance.methodName = "updated"
            logsInstance.url = "url"
            logsInstance.plainRequest = "updated request"
            logsInstance.response = "updated response"

            GlobalScope.launch {
                db.logsDao().updateLog(logsInstance)
            }


            val intent = Intent(
                this, LogsActivity::class.java
            )
            val pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT)
            val contentView = RemoteViews(packageName,R.layout.notificationlayout)
            contentView.setTextViewText(R.id.notificationtitletv,"CodeAndroid")
            contentView.setTextViewText(R.id.notificationdestv,"Text notification")

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationChannel = NotificationChannel(CHANNEL_ID,description,NotificationManager.IMPORTANCE_HIGH)
                notificationChannel.enableLights(true)
                notificationChannel.lightColor = Color.GREEN
                notificationChannel.enableVibration(false)
                notificationManager.createNotificationChannel(notificationChannel)

                builder = Notification.Builder(this,CHANNEL_ID)
                        .setContentTitle("Title")
                        .setContentText("my data")
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentIntent(pendingIntent)



            }else{
                builder = Notification.Builder(this)
                        .setContentTitle("Title")
                        .setContentText("my data")
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentIntent(pendingIntent)

            }


            notificationManager.notify(1,builder.build())



//
//            //use constant ID for notification used as group summary
//            val SUMMARY_ID = 0
//            val GROUP_KEY_WORK_EMAIL = "com.android.example.WORK_EMAIL"
//
//            val newMessageNotification1 = NotificationCompat.Builder(this@MainActivity, CHANNEL_ID)
//                    .setSmallIcon(R.drawable.ic_launcher)
//                    .setContentTitle("emailObject1.getSummary()")
//                    .setContentText("You will not believe...")
//                    .setGroup(GROUP_KEY_WORK_EMAIL)
//                    .build()
//
//            val newMessageNotification2 = NotificationCompat.Builder(this@MainActivity, CHANNEL_ID)
//                    .setSmallIcon(R.drawable.ic_launcher)
//                    .setContentTitle("emailObject2.getSummary()")
//                    .setContentText("Please join us to celebrate the...")
//                    .setGroup(GROUP_KEY_WORK_EMAIL)
//                    .build()
//
//            val summaryNotification = NotificationCompat.Builder(this@MainActivity, CHANNEL_ID)
//                    .setContentTitle("emailObject.getSummary()")
//                    //set content text to support devices running API level < 24
//                    .setContentText("Two new messages")
//                    .setSmallIcon(R.drawable.ic_launcher)
//                    //build summary info into InboxStyle template
//                    .setStyle(NotificationCompat.InboxStyle()
//                            .addLine("Alex Faarborg Check this out")
//                            .addLine("Jeff Chang Launch Party")
//                            .setBigContentTitle("2 new messages")
//                            .setSummaryText("janedoe@example.com"))
//                    //specify which group this notification belongs to
//                    .setGroup(GROUP_KEY_WORK_EMAIL)
//                    //set this notification as the summary for the group
//                    .setGroupSummary(true)
//                    .build()
//
//            NotificationManagerCompat.from(this).apply {
//
//              // notify(1, newMessageNotification1)
//              //  notify(2, newMessageNotification2)
//                notify(SUMMARY_ID, summaryNotification)
//            }


        }
//        val db = AppDatabase(this)
//        viewModel.logsData.observe(this, Observer {
//            var datait = it
//            GlobalScope.launch {
//                db.logsDao().insertAll(datait)
//
//                var data = db.logsDao().getAll()
//                data?.forEach{
//                    roomdbbutton.setText("data"+datait.id)
//                    notification(datait.id.toString(),datait.response.toString())
//                }
//            }
//
//        })
        roomdbbutton.setOnClickListener {

            val db = AppDatabase(this)
            viewModel.logsData.observe(this, Observer {
                var datait = it
                GlobalScope.launch {
                    db.logsDao().insertAll(datait)

                    var data = db.logsDao().getAll()
                    logId = data.get(data.lastIndex).id


                data?.forEach{
                    notification("Response",datait.response.toString())

                }
                }

            })


//            viewModel.logsData.observe(this, Observer {
//                var logsdata = it
//                db.logsDao().insertAll(logsdata)
//                var data = db.logsDao().getAll()
//                data?.forEach{
//                    roomdbbutton.setText("data"+it.id.toString())
//                }

//            GlobalScope.launch {
//                db.logsDao().insertAll(Logs(id,"method","url","request","response","encrequest","encrequestkey","encresponse","encresponsekey"))
//
//                var data = db.logsDao().getAll()
//                data?.forEach{
//                    roomdbbutton.setText("data"+it.id.toString())
//                }
//            }



//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                notificationChannel = NotificationChannel(CHANNEL_ID,description,NotificationManager.IMPORTANCE_HIGH)
//                notificationChannel.enableLights(true)
//                notificationChannel.lightColor = Color.GREEN
//                notificationChannel.enableVibration(false)
//                notificationManager.createNotificationChannel(notificationChannel)
//
//                builder = Notification.Builder(this,CHANNEL_ID)
//                        .setContent(contentView)
//                        .setSmallIcon(R.mipmap.ic_launcher_round)
//                        .setLargeIcon(BitmapFactory.decodeResource(this.resources,R.mipmap.ic_launcher))
//                        .setContentIntent(pendingIntent)
//            }else{
//                builder = Notification.Builder(this)
//                        .setContent(contentView)
//                        .setSmallIcon(R.mipmap.ic_launcher_round)
//                        .setLargeIcon(BitmapFactory.decodeResource(this.resources,R.drawable.ic_launcher))
//                        .setContentIntent(pendingIntent)
//            }
//            notificationManager.notify(1234,builder.build())

        }
//        viewModel.pagingLivedata.observe(this, Observer {
//            adapter = DataAdapter(viewModel,it,this)
//            recyclerView.adapter = adapter
//
//        })
//        viewModel.fetchUser()

    }
    private fun notification(title:String,des:String)
    {
        val intent = Intent(this,LogsActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT)
        val contentView = RemoteViews(packageName,R.layout.notificationlayout)
        contentView.setTextViewText(R.id.notificationtitletv,"CodeAndroid")
        contentView.setTextViewText(R.id.notificationdestv,"Text notification")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(CHANNEL_ID,description,NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(false)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(this,CHANNEL_ID)
                    .setContentTitle(title)
                    .setContentText(des)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContentIntent(pendingIntent)


        }

        notificationManager.notify(1234,builder.build())

    }
    private fun setupScrollListener() {
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount

                if(layoutManager.findLastVisibleItemPosition() == totalItemCount-1)
                {
                    viewModel.listScrolled()
                }
                }
        })
    }
    private fun initAdapter() {
        viewModel.livedata.observe(this, Observer {
            adapter = DataAdapter(viewModel,it,this)
            recyclerView.adapter = adapter
        })
        viewModel.requestAndSaveData()
    }
//             viewModel.livedata.observe(this, Observer {
//            if(it.size>0){
//                adapter = DataAdapter(viewModel,it,this)
//                recyclerView.adapter = adapter
//
//            }
//        })
//        viewModel.fetchAllUser()

//        viewModel.resourceLiveData.observe(this, Observer {
//            if(it.size>0){
//                var item = it[0]
//                textview.text = item.name.plus(" / ").plus(item.color)
//            }
//        })
//        viewModel.fetchAllResource()





//    fun onRefresh() {
//        adapter.clear()
//        page = 1
//        getUsers(true)
//    }

}