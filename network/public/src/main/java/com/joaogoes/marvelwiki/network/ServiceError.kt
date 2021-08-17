package com.joaogoes.marvelwiki.network


sealed class ServiceError {
    object NetworkError : ServiceError()
    object NoConnectionError : ServiceError()
    object NotFoundError : ServiceError()
    object UnknownError : ServiceError()
}