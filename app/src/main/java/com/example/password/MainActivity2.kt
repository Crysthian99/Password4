package com.example.password
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity1.*

class MainActivity : AppCompatActivity() {

    lateinit var usersDBHelper : UsersDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity1)

        usersDBHelper = UsersDBHelper(this)
    }

    fun addUser(v:View){
        var webid = this.edittext_webid.text.toString()
        var web = this.edittext_web.text.toString()
        var usid = this.edittext_usid.text.toString()
        var pass = this.edittext_pass.text.toString()
        var salt = this.edittext_salt.text.toString()

        var result = usersDBHelper.insertUser(UserModel(webid = webid,web = web,usid = usid, pass=pass,salt=salt))
        //clear all edittext s
        this.edittext_salt.setText("")
        this.edittext_pass.setText("")
        this.edittext_usid.setText("")
        this.edittext_web.setText("")
        this.edittext_webid.setText("")
        this.textview_result.text = "Added user : "+result
        this.ll_entries.removeAllViews()
    }

    fun deleteUser(v:View){
        var webid = this.edittext_webid.text.toString()
        val result = usersDBHelper.deleteUser(webid)
        this.textview_result.text = "Deleted user : "+result
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