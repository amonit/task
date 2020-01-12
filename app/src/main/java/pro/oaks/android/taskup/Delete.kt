package pro.oaks.android.taskup

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_delete.*
import org.json.JSONObject
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class Delete : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete)

        val userkeypref = UserkeyPref(this)
        val userkey = userkeypref.getKeyValue()


        del_yes.setOnClickListener {
            var nid: String = this.intent.getStringExtra("nid")

            var id = Integer.parseInt(nid)

            val url =
                getString(R.string.base_url) + "/deleteTask.php?userkey=" + userkey + "&taskid=" + id

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
                Toast.makeText(this@Delete,message, Toast.LENGTH_LONG).show()
            }
            else
            {
                val userkeypref = UserkeyPref(this@Delete)
                val userkey = userkeypref.getKeyValue()

                Toast.makeText(this@Delete,userkey, Toast.LENGTH_LONG).show()

                var intent = Intent(this@Delete, Dashboard::class.java)
                startActivity(intent)

            }
        }
    }
}
