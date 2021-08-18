package com.example.password

import AESKnowledgeFcatory.encrypt
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.password.HashUtils.generateSalt
import com.example.password.HashUtils.sha256
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*

class AddFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        view.add_btn.setOnClickListener {
            insertDataToDatabase()
        }

        return view
    }

    private fun insertDataToDatabase() {
        val salt = generateSalt().toString()
        val website_account = addFirstName_et.text.toString()
        val password_name = (encrypt(addLastName_et.text.toString(), sha256(this.master+salt))).toString()


        if(inputCheck(salt, website_account, password_name)){
            // Create User Object
            val user = User(0, salt, website_account, password_name)
            // Add Data to Database
            mUserViewModel.addUser(user)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
            // Navigate Back
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(salt: String, website_account: String, password_name: String): Boolean{
        return !(TextUtils.isEmpty(salt) && TextUtils.isEmpty(website_account) && password_name.isEmpty())
    }

}