package com.example.apicalling

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_detail.*


class DetailFragment : Fragment() ,ViewActivity.setData{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val myview = inflater.inflate(R.layout.fragment_detail, container, false)
        return myview
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun setData(dataa: Logs) {
        idTv.text = "id : "+dataa.id.toString()
        methodNameTv.text ="Method Name : "+dataa.methodName
        urlTv.text ="url : "+dataa.url
        requestTV.text ="request : "+dataa.plainRequest
        val jsonFormat = formatString(dataa.response.toString())
        responseTv.text =jsonFormat
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