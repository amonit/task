package pro.oaks.android.taskup

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_task.*
import org.json.JSONObject
import java.net.URL
import java.net.URLEncoder
import javax.net.ssl.HttpsURLConnection

class AddTask : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        val userkeypref = UserkeyPref(this)
        val userkey = userkeypref.getKeyValue()

        addtask.setOnClickListener {
            var title = URLEncoder.encode(task_title.text.toString())
            var description = URLEncoder.encode(task_description.text.toString())

            var url= getString(R.string.base_url)+"addMyTask.php?userkey="+userkey+"&title="+title+"&description="+description

            //Toast.makeText(this@AddTask,url, Toast.LENGTH_LONG).show()

            AsyncTaskHandleJson().execute(url)
        }
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
            //val jsonArray = JSONArray(jsonString)
            val js = JSONObject(jsonString)
            //var x =0
            //val jsonObject = jsonArray.getJSONObject(x)
            var status = js.getString("status")
            var message = js.getString("msg")

            if(status.equals("failed")){
                Toast.makeText(this@AddTask,message, Toast.LENGTH_LONG).show()
            }
            else
            {
                val userkeypref = UserkeyPref(this@AddTask)
                val userkey = userkeypref.getKeyValue()

                Toast.makeText(this@AddTask,userkey, Toast.LENGTH_LONG).show()

                var intent = Intent(this@AddTask, Dashboard::class.java)
                startActivity(intent)

            }
        }
    }
}
