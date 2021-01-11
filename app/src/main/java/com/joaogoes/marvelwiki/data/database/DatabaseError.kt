package com.joaogoes.marvelwiki.data.database

sealed class DatabaseError {
    object InvalidCharacter : DatabaseError()
    object UnknownError : DatabaseError()
}