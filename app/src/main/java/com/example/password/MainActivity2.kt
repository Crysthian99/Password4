package com.example.password
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.password.PasswordUtils.generateSalt
import kotlinx.android.synthetic.main.activity1.*
import kotlinx.android.synthetic.main.activitycreate.*

class MainActivity : AppCompatActivity() {

    var master: String = "0"
    var variableNeeded1 = 0;

    lateinit var usersDBHelper : UsersDBHelper

    fun createMaster(v:View){
        this.master = this.editmasterpass.text.toString()
        setContentView(R.layout.activity1)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(kotlin.run { this.master=="0" }) {
            setContentView(R.layout.activitycreate)
        }
        else{
            setContentView(R.layout.activitylogin)
        }

        usersDBHelper = UsersDBHelper(this)

        val passwordGenerator = PasswordGenerator()
        generator.setOnClickListener{
            val password:String=passwordGenerator.generatePassword(length = 8, specialWord = "crypted")
            textView3.text=password
        }
    }

    fun addUser(v:View){
        var users = usersDBHelper.readAllUsers()
        var webid = users.size+1
        var web = this.edittext_web.text.toString()

        var pass = this.edittext_pass.text.toString()
        var salt = generateSalt().toString()


        var result = usersDBHelper.insertUser(UserModel(
            webid =webid,
            web = web, "a", pass =pass,
            salt =salt ))
        //clear all edittext s

        this.edittext_pass.setText("")

        this.edittext_web.setText("")

        this.textview_result.text = "Added account : "+result
        this.ll_entries.removeAllViews()
        this.variableNeeded1++
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
        this.textview_result.text = "Fetched " + users.size + " users"
    }


}