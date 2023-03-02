package com.example.foottricks.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class VPAdapter(fm: FragmentManager, private val fragments: List<Fragment>) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
    override fun getCount(): Int {
        return fragments.size     }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
       if(position==0)
       {
           return "RECORD"
       }
        else if(position==1)
       {
           return "ASSIST"
       }
       else if(position==2)
       {
           return "GOALS"
       }
        else{
            return "RED CARD"
       }
    }
}