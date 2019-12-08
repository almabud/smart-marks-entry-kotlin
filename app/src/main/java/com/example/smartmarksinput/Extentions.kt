package com.example.smartmarksinput

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import android.widget.TextView


fun Context.dropDownInit(dataArray: Array<String>): SpinnerAdapter{

    val adapter =  object: ArrayAdapter<String>(
        this,
        android.R.layout.simple_list_item_activated_1,
        dataArray
    ){
        override fun isEnabled(position: Int): Boolean {
            if(position == 0){
                return false
            }
            return super.isEnabled(position)
        }

        override fun getDropDownView(
            position: Int,
            convertView: View?,
            parent: ViewGroup
        ): View {
            val view = super.getDropDownView(position, convertView, parent) as TextView
            if(position == 0){
                view.setBackgroundColor(Color.TRANSPARENT)
                view.setTextColor(Color.GRAY)
            }
            else{
                view.setTextColor(Color.BLACK)
            }
            return super.getDropDownView(position, convertView, parent)
        }
    }
    return adapter
}