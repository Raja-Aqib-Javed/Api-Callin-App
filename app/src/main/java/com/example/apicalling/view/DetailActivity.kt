package com.example.apicalling.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.example.apicalling.AppDatabase
import com.example.apicalling.R
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(findViewById(R.id.toolbar))

        val intent = intent
        val id = intent.getStringExtra("id")
        methodnametextView.text = intent.getStringExtra("mname")
        urltextView.text = intent.getStringExtra("url")
        val request = intent.getStringExtra("request")
        val response = intent.getStringExtra("response")
        val responseFormat = formatString(response.toString())

        reqandrestextView.text = request+"\n\n"+responseFormat
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var result = ""
        // Handle item selection
        if (item.itemId == R.id.detele) {
            val db = AppDatabase(this)
            GlobalScope.launch {
               result = db.clearAllTables().toString()

            }
            if(result != null){
                Toast.makeText(this@DetailActivity,"Successfully deleted",Toast.LENGTH_LONG).show()
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }
    fun formatString(text: String): String? {
        val json = StringBuilder()
        var indentString = ""
        for (i in 0 until text.length) {
            val letter = text[i]
            when (letter) {
                '{', '[' -> {
                    json.append(
                        """
                            
                            $indentString$letter
                            
                            """.trimIndent()
                    )
                    indentString = indentString + "\t"
                    json.append(indentString)
                }
                '}', ']' -> {
                    indentString = indentString.replaceFirst("\t".toRegex(), "")
                    json.append(
                        """
                            
                            $indentString$letter
                            """.trimIndent()
                    )
                }
                ',' -> json.append(
                    """
                        $letter
                        $indentString
                        """.trimIndent()
                )
                else -> json.append(letter)
            }
        }
        return json.toString()
    }
}