package pro.oaks.android.taskup

import android.content.Context

class UserkeyPref(context:Context){
    val userkey = "userkey"
    val userkey_value = "KeyValue"
    val preference = context.getSharedPreferences(userkey,Context.MODE_PRIVATE)

    fun getKeyValue():String{
        return preference.getString(userkey," ").toString()
    }
    fun setUserKey(userk:String){
        val editor = preference.edit()
        editor.putString(userkey,userk)
        editor.apply()
    }
}