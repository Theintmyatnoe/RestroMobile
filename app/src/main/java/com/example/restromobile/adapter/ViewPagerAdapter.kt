package com.example.restromobile.adapter

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.example.restromobile.fragment.FirstFloorFragment
import com.example.restromobile.fragment.OutsideFragment
import com.example.restromobile.fragment.SecondFloorFragment

class ViewPagerAdapter(context: Context, supportFragmentManager: FragmentManager,private var tabCount: Int) : FragmentPagerAdapter(supportFragmentManager) {
    override fun getItem(position: Int): Fragment {
        when (position){
            0->{
                return FirstFloorFragment()
            }
            1 -> {
                return SecondFloorFragment()
            }
            else -> {
                // val movieFragment = MovieFragment()
                return OutsideFragment()
            }
        }

    }

    override fun getCount(): Int {
        return tabCount

    }


}
