package pro.oaks.android.taskup

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class Register : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        register.setOnClickListener {
            var fname = reg_firstname.text
            var lname = reg_lastname.text
            var phone = reg_mobile.text
            var email = reg_email.text
            var pword = reg_pword.text
            var country = country.text

            var url = getString(R.string.base_url)+"register.php?phone=" + phone + "&pword=" + pword + "&lname="+ lname + "&fname="+fname+"&country="+country+"&email="+email
            //Toast.makeText(this@Register,url, Toast.LENGTH_LONG).show()

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
                Toast.makeText(this@Register,message, Toast.LENGTH_LONG).show()
            }
            else
            {
                var userkey = js.getString("userkey")
                val userkeypref = UserkeyPref(this@Register)
                userkeypref.setUserKey(userkey)

                Toast.makeText(this@Register,userkey, Toast.LENGTH_LONG).show()

                var intent = Intent(this@Register, Dashboard::class.java)
                startActivity(intent)

            }
        }
    }
}
