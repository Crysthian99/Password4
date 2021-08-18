package com.example.password

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.password.HashUtils.sha256
import kotlinx.android.synthetic.main.activitycreate.*
import kotlinx.android.synthetic.main.activitylogin.*

class MainActivity : AppCompatActivity() {
    var master: String = "0"
    var checkLogin2: String = ""


    fun createMaster(v: View){
        this.master = this.editmasterpass.text.toString()

        val sharedPref = this.getPreferences(MODE_PRIVATE) ?:return
        with (sharedPref.edit()){
            putString("cheie",sha256(master))
            commit()
        }

        val sharedPref2 = this.getPreferences(MODE_PRIVATE) ?: return
        val defaultValue = sharedPref2.getString("cheie", "key not found")
        System.out.println(" Key value = " + defaultValue)
        setContentView(R.layout.activity_main)
    }

    fun checklogin(v:View)
    {
        this.checkLogin2 = this.editTextTextPassword.text.toString()
        val castoras = this.getPreferences(MODE_PRIVATE)
        val defaultValue2 = castoras.getString("cheie", "key not found")
        if(sha256(this.checkLogin2)==defaultValue2.toString())
        {
            setContentView(R.layout.activity_main)
        }
        else {
            textView3.text="Incorrect Password"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val castoras = this.getPreferences(MODE_PRIVATE)
        val defaultValue2 = castoras.getString("cheie", "key not found")
        System.out.println("Abracanabra= "+defaultValue2)
        if ((defaultValue2.toString())==null)
            setContentView(R.layout.activitycreate)
        else {setContentView((R.layout.activitylogin))}

        setupActionBarWithNavController(findNavController(R.id.fragment))
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}

