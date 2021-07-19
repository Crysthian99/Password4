package com.example.password


import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var usersDBHelper : UsersDBHelper



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main0)

        usersDBHelper = UsersDBHelper(this)
    }



    fun addUser(view: View) {
        val userid = this.edittext_userid.text.toString()
        val name = this.edittext_name.text.toString()

        val result = usersDBHelper.insertUser(UserModel(userid = userid,name = name))
        //clear all edittext s

        this.edittext_name.setText("")
        this.edittext_userid.setText("")
        this.textview_result.text = "Website added : $result"
        this.ll_entries.removeAllViews()
    }

    fun deleteUser(view: View) {
        val userid = this.edittext_userid.text.toString()
        val result = usersDBHelper.deleteUser(userid)
        this.textview_result.text = "Website deleted : $result"
        this.ll_entries.removeAllViews()
    }

    fun showmain0 (view: View)
    {
        setContentView(R.layout.activity_main)
    }

    fun showaccounts(view: View)
    {
        setContentView(R.layout.accounts_main)
    }

    fun showAllUsers(view: View) { val users = usersDBHelper.readAllUsers()
        this.ll_entries.removeAllViews()
        users.forEach {
            val tvuser = TextView(this)
            tvuser.textSize = 30F
            tvuser.text = "Website : " + it.name + "                     Password : " + " - " + it.userid + ""
            this.ll_entries.addView(tvuser)
        }
        this.textview_result.text = "You have saved " + users.size + " accounts"}


}