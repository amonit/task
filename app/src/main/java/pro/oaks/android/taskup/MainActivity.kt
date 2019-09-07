package pro.oaks.android.taskup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userkeypref = UserkeyPref(this)
        val userkey = userkeypref.getKeyValue()

        if(userkey.length>0){
            var intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
        }
        button3.setOnClickListener {
            var intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

    }
}
