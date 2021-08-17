package com.joaogoes.marvelwiki.database

sealed class DatabaseError {
    object InvalidCharacter : DatabaseError()
    object UnknownError : DatabaseError()
}