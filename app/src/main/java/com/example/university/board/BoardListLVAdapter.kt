package com.example.university.board

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.example.university.R
import com.example.university.utils.FBAuth
import kotlinx.coroutines.NonDisposableHandle.parent

class BoardListLVAdapter(val boardList: MutableList<BoardModel>): BaseAdapter() {

    override fun getCount(): Int {
        return boardList.size
    }

    override fun getItem(position: Int): Any {
        return boardList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var view = convertView
 //       if (view == null) {
            view = LayoutInflater.from(parent?.context).inflate(R.layout.board_list_item,parent,false)
 //       }

        val itemLinearLayoutView = view?.findViewById<LinearLayout>(R.id.itemView)
        val title = view?.findViewById<TextView>(R.id.titleArea)
        val content = view?.findViewById<TextView>(R.id.contentArea)
        val time = view?.findViewById<TextView>(R.id.timeArea)

        if (boardList[position].uid.equals(FBAuth.getUid())) {
            itemLinearLayoutView?.setBackgroundColor(Color.parseColor("#ffa500"))
        }

        title!!.text= boardList[position].title
        content!!.text= boardList[position].content
        time!!.text= boardList[position].time
        return view!!
    }
}
