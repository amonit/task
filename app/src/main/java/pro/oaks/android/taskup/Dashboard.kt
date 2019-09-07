package pro.oaks.android.taskup

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.content_dashboard.*

class Dashboard : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        setSupportActionBar(toolbar)


        val adapter = MyViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(MyTask(),"My Task")
        adapter.addFragment(AllTask(),"All Task")

        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)

        fab.setOnClickListener {
            var intent = Intent(this, AddTask::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        var selectedoption = ""

        when(item?.itemId){
            R.id.about->{
                Toast.makeText(this,"About Page Not Ready",Toast.LENGTH_LONG).show()
            }
            R.id.settings->{
                var intent = Intent(this, Main2Activity::class.java)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    class MyViewPagerAdapter(Manager: FragmentManager) : FragmentPagerAdapter(Manager){

        private val fragmentList: MutableList<Fragment> = ArrayList()
        private val titleList: MutableList<String> = ArrayList()

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

        fun addFragment(fragment: Fragment,title: String){
            fragmentList.add(fragment)
            titleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titleList[position]
        }

    }

}
