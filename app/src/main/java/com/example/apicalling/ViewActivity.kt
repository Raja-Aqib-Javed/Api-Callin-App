package com.example.apicalling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_view.*

class ViewActivity : AppCompatActivity(), TableFragment.buttonClick {
    private lateinit var viewPager: ViewPager
    private lateinit var tabs: TabLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)

        initViews()
        setUpViewPager()

    }

    private fun initViews() {
        tabs = findViewById(R.id.tabs)
        viewPager = findViewById(R.id.viewpager)
    }

    private fun setUpViewPager() {
        val adapter = PagerAdapter(supportFragmentManager)

        val firstFragment = TableFragment() as Fragment
        val secondFragment = DetailFragment() as Fragment

        adapter.addFragment(firstFragment, "ONE")
        adapter.addFragment(secondFragment, "TWO")

        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
    }
    override fun buttonClicked(view: View) {
        viewpager.currentItem = 2
    }
    interface setData {
        fun setData(dataa: Logs)
    }
}