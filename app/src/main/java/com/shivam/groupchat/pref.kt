package com.shivam.groupchat

import android.content.Context
import android.content.SharedPreferences

class pref {

    private val PREFERENCE_NAME = "my_preference"
    lateinit var context: Context
    lateinit var preferences: SharedPreferences

    constructor(context: Context)
    {
        this.context=context
        preferences=context.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE)
    }
    public fun savedata(key:String,value: String)
    {
        var editor = preferences.edit()
        editor.putString(key,value)
        editor.commit()
    }
    public fun getData(key: String):String
    {
        return preferences.getString(key,"")!!
    }

    public fun clearData()
    {
        var editor = preferences.edit()
        editor.clear()
        editor.commit()
    }

}