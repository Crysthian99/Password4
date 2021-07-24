package com.example.password

import android.provider.BaseColumns

object DBContract {

    /* Inner class that defines the table contents */
    class UserEntry : BaseColumns {
        companion object {
            val TABLE_NAME = "users"
            val COLUMN_WEBID = "webid"
            val COLUMN_WEB = "web"
            val COLUMN_USID = "usid"
            val COLUMN_PASS = "pass"
            val COLUMN_SALT = "salt"
        }
    }
}