package com.swasti.bookhubapp.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.*
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.textclassifier.TextLinks
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.swasti.bookhubapp.R
import com.swasti.bookhubapp.adapter.DashboardRecyclerAdapter
import com.swasti.bookhubapp.model.Book
import com.swasti.bookhubapp.util.ConnectionManager
import org.json.JSONException
import java.util.*
import kotlin.Comparator
import kotlin.collections.HashMap

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DashboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DashboardFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    //lateinit var btnCheckInternet:Button
    lateinit var recyclerDashboard: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var progressLayout:RelativeLayout
    lateinit var progressBar:ProgressBar
    /*val booklist= arrayListOf(
        "P.S.I love You",
        "The Great Gatsby",
        "Anna Karenina",
        "Madame Bovary",
        "War and Peace",
        "Lolita",
        "Middlemarch",
        "The Adventures Fin",
        "Moby-Dick",
        "The Lord of the Rings"
    )*/

    lateinit var recyclerAdapter: DashboardRecyclerAdapter

    val bookInfoList=arrayListOf<Book>()
    var ratingComparator=Comparator<Book>{
    book1,book2->
        if(book1.bookRating.compareTo(book2.bookRating,true)==0) {
        book1.bookName.compareTo(book2.bookName,true)
    }
        else
        {
            book1.bookRating.compareTo(book2.bookRating,true)
        }
    }
    /*(
        Book("P.S.I love you","Cecelia Ahem","Rs.299",R.drawable.ps_you),
        Book("The Great Gatsby","F.Scott Fitzgerald","Rs.399",R.drawable.the_great_gatsby),
        Book("Anna Karenina","Leo Tolstoy","Rs.199",R.drawable.anna_kare),
        Book("Madame Bovary","Gustave Flaubert","Rs.500",R.drawable.madame_bovary),
        Book("War and Peace","Leo Tolstoy","Rs.249",R.drawable.warandpeace),
        Book("Lolita","Vladimir Nabokov","Rs.349",R.drawable.lolita),
        Book("Middlemarch","George Elliot","Rs.599",R.drawable.mid_march),
        Book("The Adventures Fin","Mark Twain","Rs.699",R.drawable.ad_finn),
        Book("Moby-Dick","Herman Melville","Rs.499",R.drawable.mob_dick),
        Book("The Lord of the Rings","J.R.R.Tolkien","Rs.749",R.drawable.lord_of_rings),

        )*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_dashboard, container, false)
        setHasOptionsMenu(true)
        recyclerDashboard=view.findViewById(R.id.recyclerDashboard)
       // btnCheckInternet=view.findViewById(R.id.btnCheckInternet)
        progressLayout=view.findViewById(R.id.progressLayout)
        progressBar=view.findViewById(R.id.progressBar)
        progressLayout.visibility=View.VISIBLE
      /*  btnCheckInternet.setOnClickListener {
            if(ConnectionManager().checkConnectivity(activity as Context))
            {
                val dialog=AlertDialog.Builder(activity as Context)
                dialog.setTitle("Success")
                dialog.setMessage("Internet Connection Found")
                dialog.setPositiveButton("Ok")
                {
                    text,listener->
                }
                dialog.setNegativeButton("Cancel")
                {
                        text,listener->
                }
                dialog.create()
                dialog.show()


            }
           /* else
            {
                val dialog=AlertDialog.Builder(activity as Context)
                dialog.setTitle("Error")
                dialog.setMessage("Internet Connection Not Found")
                dialog.setPositiveButton("Ok")
                {
                        text,listener->
                }
                dialog.setNegativeButton("Cancel")
                {
                        text,listener->
                }
                dialog.create()
                dialog.show()
            }*/
        }*/

        layoutManager= LinearLayoutManager(activity)
        recyclerAdapter= DashboardRecyclerAdapter(activity as Context,bookInfoList)

        recyclerDashboard.adapter=recyclerAdapter
        recyclerDashboard.layoutManager=layoutManager


        val queue=Volley.newRequestQueue(activity as Context)
        val url="http://13.235.250.119/v1/book/fetch_books/"
        if(ConnectionManager().checkConnectivity(activity as Context))
        {
            val jsonObjectRequest=object:JsonObjectRequest(Method.GET,url,null, Response.Listener {
                try {
                    progressLayout.visibility=View.GONE
                    val success=it.getBoolean("success")
                    if(success)
                    {
                        val data=it.getJSONArray("data")
                        for(i in 0 until data.length())
                        {
                            val bookJsonObject=data.getJSONObject(i)
                            val bookObject=Book(
                                bookJsonObject.getString("book_id"),
                                bookJsonObject.getString("name"),
                                bookJsonObject.getString("author"),
                                bookJsonObject.getString("rating"),
                                bookJsonObject.getString("price"),
                                bookJsonObject.getString("image"),
                            )
                            bookInfoList.add(bookObject)
                            recyclerAdapter= DashboardRecyclerAdapter(activity as Context,bookInfoList)

                            recyclerDashboard.adapter=recyclerAdapter
                            recyclerDashboard.layoutManager=layoutManager

                            recyclerDashboard.addItemDecoration(
                                DividerItemDecoration(
                                    recyclerDashboard.context,
                                    (layoutManager as LinearLayoutManager).orientation
                                )
                            )
                        }
                    }

                    else
                    {
                        Toast.makeText(activity as Context,"Some Error Occured!!!",Toast.LENGTH_SHORT).show()
                    }
                }
                catch(e:JSONException)
                {
                    Toast.makeText(activity as Context,"Some Unexpectederror Occurred!!!",Toast.LENGTH_SHORT).show()
                }

            },Response.ErrorListener {
                if(activity!=null)
                {
                    Toast.makeText(activity as Context,"Volley error occurred!!!",Toast.LENGTH_SHORT).show()
                }
            }
            )
            {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers=HashMap<String,String>()
                    headers["Content-type"]="application/json"
                    headers["token"]="9bf534118365f1"
                    return headers
                }
            }
            queue.add(jsonObjectRequest)
        }
        else
        {
            val dialog=AlertDialog.Builder(activity as Context)
            dialog.setTitle("Error")
            dialog.setMessage("Internet Connection Not Found")
            dialog.setPositiveButton("Open Settings")
            {
                    text,listener->
                val settingsIntent=Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingsIntent)
                activity?.finish()
            }
            dialog.setNegativeButton("Exit")
            {
                    text,listener->
                ActivityCompat.finishAffinity(activity as Activity)
            }
            dialog.create()
            dialog.show()
        }
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DashboardFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DashboardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_dashboard,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id=item?.itemId
        if(id==R.id.actionsort)
        {
            Collections.sort(bookInfoList,ratingComparator)
            bookInfoList.reverse()
        }
        recyclerAdapter.notifyDataSetChanged()
        return super.onOptionsItemSelected(item)
    }
}