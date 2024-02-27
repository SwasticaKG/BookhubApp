package com.swasti.bookhubapp.adapter

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso
import com.swasti.bookhubapp.R
import com.swasti.bookhubapp.activity.DescriptionActivity
import com.swasti.bookhubapp.model.Book

class DashboardRecyclerAdapter(val context:Context,val itemList:ArrayList<Book>):
    RecyclerView.Adapter<DashboardRecyclerAdapter.DashboardViewHolder>() {

    class DashboardViewHolder(view:View):
        RecyclerView.ViewHolder(view) {
        val txtBookName: TextView = view.findViewById(R.id.txtBookName)
        val txtBookAuthor:TextView=view.findViewById(R.id.txtBookAuthor)
        val txtBookRating:TextView=view.findViewById(R.id.txtBookRatings)
        val txtBookPrice:TextView=view.findViewById(R.id.txtBookCost)
        val imgBookImage:ImageView=view.findViewById(R.id.imgBookImage)
        val llContent:LinearLayout=view.findViewById(R.id.llContent)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): DashboardViewHolder {
        val view=LayoutInflater.from(p0.context).inflate(R.layout.recycler_dashboard_single_row,p0,false)
        return DashboardViewHolder(view)
    }

    override fun onBindViewHolder(p0: DashboardViewHolder, p1: Int) {
        val book=itemList[p1]
        p0.txtBookName.text=book.bookName
        p0.txtBookAuthor.text=book.bookAuthor
        p0.txtBookRating.text=book.bookRating
        p0.txtBookPrice.text=book.bookPrice
        //p0.imgBookImage.setImageResource(book.bookImage)
       // Picasso.get().load(book.bookImage).into(p0.imgBookImage)
        Picasso.get().load(book.bookImage).error(R.drawable.default_book_cover).into(p0.imgBookImage)
        p0.llContent.setOnClickListener {
            val intent=Intent(context,DescriptionActivity::class.java)
            intent.putExtra("book_id",book.bookId)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}
