package com.joaogoes.marvelwiki.network

import java.io.IOException


class NoConnectionException(
    message: String = "No connection found",
    cause: Throwable? = null
) : IOException(message, cause)