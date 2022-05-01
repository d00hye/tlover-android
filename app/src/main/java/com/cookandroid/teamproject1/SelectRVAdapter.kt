package com.cookandroid.teamproject1

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.cookandroid.teamproject1.id.view.SelectDestActivity

class SelectRVAdapter(var context : Context) : RecyclerView.Adapter<SelectRVAdapter.Holder>() {

    var dataList = emptyList<SelectDataModel>()
    private var selectdata = mutableListOf<SelectDataModel>()
    var count : Int =0

    private lateinit var selectDestActivity: SelectDestActivity


    internal fun setDataList(selectDataList : List<SelectDataModel>){
        this.dataList = selectDataList
        notifyDataSetChanged()
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var btn : Button = itemView.findViewById(R.id.grid_btn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_select, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        var data = dataList[position]
        holder.btn.text = data.title

        holder.btn.setOnClickListener{
            for (i in 0 until selectdata.size){
                if (selectdata[i].title== holder.btn.text){
                    selectdata.removeAt(i)
//                    Toast.makeText(this.context, selectdata[0].title, Toast.LENGTH_SHORT).show()
                    holder.btn.setBackgroundResource(R.drawable.select_dest_grid)
                    holder.btn.setTextColor(Color.parseColor("#6E6E76"))
                    return@setOnClickListener
                }
            }
            count += 1
            if (count==3){
//                selectDestActivity.changeConfirmButton()
            }
            if (selectdata.size>=3){
//                Toast.makeText(this.context, data.title, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            holder.btn.setBackgroundResource(R.drawable.select_click)
            holder.btn.setTextColor(Color.parseColor("#FFFFFF"))
            selectdata.add(SelectDataModel(data.title))

        }
    }

    fun getSelectData(): MutableList<SelectDataModel> {
        return selectdata
    }

//    fun getCountData(): Int {
//        return this.count
//    }

    override fun getItemCount(): Int {
       return  dataList.size
    }


}
