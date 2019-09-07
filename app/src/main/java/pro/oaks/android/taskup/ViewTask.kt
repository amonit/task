package pro.oaks.android.taskup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_view_task.*

class ViewTask : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_task)

        var title:String = this.intent.getStringExtra("title")
        var nid:String = this.intent.getStringExtra("nid")

        tasktitle.text = title
        taskdescription.text = nid
    }
}
