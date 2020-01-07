package com.kritsanadum.firebasedemo

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Users(
    var username: String? = "",
    var email: String? = ""
)