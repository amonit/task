package pro.oaks.android.taskup

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_my_task.*
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class MyTask : Fragment() {
    val p=ArrayList<listfetch>()
    lateinit var rv:RecyclerView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater?.inflate(R.layout.fragment_my_task,container,false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val userkeypref = context?.let { UserkeyPref(it) }
        val userkey = userkeypref!!.getKeyValue()


        refreshlayout.setOnRefreshListener {
            rv=view!!.findViewById(R.id.tasklist) as RecyclerView
            rv.layoutManager=LinearLayoutManager(activity)
            val url = "https://enotes.gq/getMyTask.php?userkey=" + userkey
            AsyncTaskHandleJson().execute(url)
        }

        rv=view!!.findViewById(R.id.tasklist) as RecyclerView
        rv.layoutManager=LinearLayoutManager(activity)
        val url = "https://enotes.gq/getMyTask.php?userkey=" + userkey

        AsyncTaskHandleJson().execute(url)
    }
    inner class AsyncTaskHandleJson: AsyncTask<String, String, String>(){
        override fun doInBackground(vararg url: String?): String {
            var text: String
            val connection = URL(url[0]).openConnection() as HttpsURLConnection
            try{
                connection.connect()
                text = connection.inputStream.use{it.reader().use { reader->reader.readText() }}
            }
            finally{
                connection.disconnect()
            }
            return text
        }
        override fun onPostExecute(result: String) {
            super.onPostExecute(result)
            handleJson(result)
        }
    }
    private fun handleJson(JsonString : String?){
        val jsonArray = JSONArray(JsonString)
        //println(JsonString)
        //val p = ArrayList<listfetch>()
        //val op = ArrayList<listfetch>()
        var x = 1
        p.clear()
        if(jsonArray.length()>1)
        {
            while(x<jsonArray.length()){
                val jsonObject = jsonArray.getJSONObject(x)

                p.add(listfetch(
                    jsonObject.getString("id"),
                    jsonObject.getString("title"),
                    jsonObject.getString("doc")
                ))
                x++
            }
        }
        else
        {
            p.add(listfetch("0","Create your First Task",""))
        }
        var adapter = Rec_data_adapter(p)
        rv.adapter=adapter
        refreshlayout.isRefreshing = false
    }
}