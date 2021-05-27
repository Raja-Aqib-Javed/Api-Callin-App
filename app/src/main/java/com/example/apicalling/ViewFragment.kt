package com.example.apicalling

import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_view.*
import kotlinx.android.synthetic.main.fragment_view.*

class ViewFragment : Fragment() {

    companion object{
        fun newInstance(message:String):ViewFragment{
            val f = ViewFragment()

            val bundle = Bundle(1)

            bundle.putString(EXTRA_MESSAGE,message)
            f.setArguments(bundle)

            return f
        }
    }
    override fun onCreateView(inflater: LayoutInflater,container:ViewGroup?,savedInstancesState:Bundle?):View{
        val view:View?=inflater.inflate(R.layout.fragment_demo,container,false)
        val message =arguments!!.getString(EXTRA_MESSAGE)

        val textView:TextView = view!!.findViewById(R.id.texttv)
        textView.text = message
        return view
    }

    }
