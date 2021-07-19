package com.example.password

import android.provider.BaseColumns

object DBContract {

    /* Inner class that defines the table contents */
    class UserEntry : BaseColumns {
        companion object {
            var TABLE_NAME = "users"
            var COLUMN_USER_ID = "userid"
            var COLUMN_NAME = "name"

        }
    }
}