package pro.oaks.android.taskup

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_view_task.*
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class ViewTask : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_task)

        val userkeypref = UserkeyPref(this)
        val userkey = userkeypref.getKeyValue()

        var title:String = this.intent.getStringExtra("title")
        var nid:String = this.intent.getStringExtra("nid")

        tasktitle.text = title
        taskdescription.text = nid

        var id = Integer.parseInt(nid)

        val url = getString(R.string.base_url) + "/getTask.php?userkey=" + userkey + "&taskid="+id

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
        private fun handleJson(jsonString: String){
            val jsonArray = JSONArray(jsonString)
            //val js = JSONObject(jsonString)
            var x =1
            val jsonObject = jsonArray.getJSONObject(x)
            var status = jsonObject.getString("status")
            var title = jsonObject.getString("title")
            var doc = jsonObject.getString("doc")
            var desc = jsonObject.getString("description")

            taskdescription.text = desc
            taskdoc.text = "Dated: "+ doc
            tasktitle.text = title


        }
    }

}
