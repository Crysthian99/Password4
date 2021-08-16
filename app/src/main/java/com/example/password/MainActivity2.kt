package com.example.password
import AESKnowledgeFcatory.decrypt
import AESKnowledgeFcatory.encrypt
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.password.HashUtils.generateSalt
import com.example.password.HashUtils.sha256
import kotlinx.android.synthetic.main.activity1.*
import kotlinx.android.synthetic.main.activitycreate.*
import kotlinx.android.synthetic.main.activitylogin.*


// materials sau materialise pt design Android
class MainActivity : AppCompatActivity() {

    var master: String = "0"
    var checkLogin2: String = ""
    lateinit var usersDBHelper : UsersDBHelper

    fun createMaster(v:View){
        this.master = this.editmasterpass.text.toString()

        val sharedPref = this.getPreferences(MODE_PRIVATE) ?:return
        with (sharedPref.edit()){
            putString("cheie",sha256(master))
            commit()
        }

        val sharedPref2 = this.getPreferences(MODE_PRIVATE) ?: return
        val defaultValue = sharedPref2.getString("cheie", "key not found")
        System.out.println(" Key value = " + defaultValue)
        setContentView(R.layout.activity1)
    }

    fun checklogin(v:View)
    {
        this.checkLogin2 = this.editTextTextPassword.text.toString()
        val castoras = this.getPreferences(MODE_PRIVATE)
        val defaultValue2 = castoras.getString("cheie", "key not found")
        if(sha256(this.checkLogin2)==defaultValue2.toString())
        {
            setContentView(R.layout.activity1)
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

        usersDBHelper = UsersDBHelper(this)
    }


    fun addUser(v:View){
        var users = usersDBHelper.readAllUsers()
        var webid = users.size+1
        var web = this.edittext_web.text.toString()
        var pass = this.edittext_pass.text.toString()
        var salt = generateSalt().toString()
        var ananas = this.master+salt
        var usid = sha256(ananas)
        var burgundia = encrypt(pass, usid).toString()
        var burgundia2 = (decrypt(burgundia, usid)).toString()
        var result = usersDBHelper.insertUser(UserModel(
            webid =webid,
            web = web, usid= usid, pass = burgundia,
            salt =salt ))
        //clear all edittext
        this.edittext_pass.setText("")
        this.edittext_web.setText("")
        this.textview_result.text = "Added account : "+result+" ||||| "+burgundia2+" |||||"
        this.ll_entries.removeAllViews()
    }

    fun deleteUser(v:View){
        var web = this.edittext_web.text.toString()
        val result = usersDBHelper.deleteUser(web)
        this.textview_result.text = "Deleted account : "+result
        this.ll_entries.removeAllViews()
    }

    fun showAllUsers(v:View){
        var users = usersDBHelper.readAllUsers()
        this.ll_entries.removeAllViews()
        users.forEach {
            var tv_user = TextView(this)
            tv_user.textSize = 30F
            tv_user.text = it.web.toString() + " - " + it.usid.toString() + " - " + it.pass.toString() + " - " + it.salt.toString()
            this.ll_entries.addView(tv_user)
        }
        this.textview_result.text = "Total of " + users.size + " accounts"
    }


}