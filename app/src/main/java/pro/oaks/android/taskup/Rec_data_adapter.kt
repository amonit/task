package pro.oaks.android.taskup

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class Rec_data_adapter(var notes: ArrayList<listfetch>): RecyclerView.Adapter<Rec_data_adapter.ViewHolder>()
{
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.title?.text=notes[position].title
        holder?.doc?.text=notes[position].doc

        holder?.del?.setOnClickListener { itemView->
            val context = itemView.context
            val sn = Intent(context,Delete::class.java)
            sn.putExtra("nid",""+notes[position].id)
            sn.putExtra("title",notes[position].title)
            context.startActivity(sn)
        }

        holder?.view?.setOnClickListener({itemView->
            val context = itemView.context
            val sn = Intent(context,ViewTask::class.java)
            sn.putExtra("nid",""+notes[position].id)
            sn.putExtra("title",notes[position].title)
            context.startActivity(sn)
            //val i= Intent(this,viewnote::class.java)
            //i.putExtra("id",notes[position].id)
            //startActivity(i)
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_task,parent,false))
    }

    override fun getItemCount(): Int {
        return notes.size
    }


    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val title = itemView.findViewById<TextView>(R.id.textView7)
        val view = itemView.findViewById<Button>(R.id.button6)
        val del = itemView.findViewById<Button>(R.id.button4)
        val doc = itemView.findViewById<TextView>(R.id.textView8)
    }
}