package com.joaogoes.marvelwiki.network

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class MarvelAuthHashGenerator {
    fun generate(timestamp: String, publicKey: String, privateKey: String): String {
        try {
            val value = timestamp + privateKey + publicKey
            val md5Encoder = MessageDigest.getInstance("MD5")
            val digest = md5Encoder.digest(value.toByteArray())
            return digest.fold("", { str, it -> str + "%02x".format(it) })
        } catch (e: NoSuchAlgorithmException) {
            throw Exception("cannot generate the api key", e)
        }
    }
}