package pro.oaks.android.taskup

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONArray
import org.json.JSONObject
import javax.net.ssl.HttpsURLConnection
import java.net.URL

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        button2.setOnClickListener {
            var intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

        button.setOnClickListener {
            var phone = editText.text
            var pword = editText2.text

            var url = "https://enotes.gq/login.php?phone=" + phone + "&pword=" + pword

            AsyncTaskHandleJson().execute(url)
        }
    }
    inner class AsyncTaskHandleJson: AsyncTask<String,String,String>(){
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
                Toast.makeText(this@Login,message,Toast.LENGTH_LONG).show()
            }
            else
            {
                var userkey = js.getString("userkey")
                val userkeypref = UserkeyPref(this@Login)
                userkeypref.setUserKey(userkey)

                Toast.makeText(this@Login,userkey,Toast.LENGTH_LONG).show()

                var intent = Intent(this@Login, Dashboard::class.java)
                startActivity(intent)

            }
        }
    }
}
