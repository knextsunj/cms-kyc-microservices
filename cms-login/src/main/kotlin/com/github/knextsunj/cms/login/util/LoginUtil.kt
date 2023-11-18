package com.github.knextsunj.cms.login.util

import io.jsonwebtoken.Jwts
import java.security.Key
import java.util.*

open class LoginUtil {

    companion object {

        @JvmStatic
        fun buildJwtToken(key: Key, username: String?, expirationTime: Long): String? {

            val dateTime: Long = System.currentTimeMillis() + expirationTime;

            return Jwts.builder()
                .setSubject(username)
                .setExpiration(Date(dateTime))
                .signWith(key)
                .compact()
        }
    }
}