package com.santiago.battaglino.esp32.data.network.error

class ErrorObject(val code: Int, private val msg: String?) {

    fun isUnknown(): Boolean {
        return code == UNKNOWN
    }

    fun isNoContent(): Boolean {
        return code == NO_CONTENT
    }

    fun isBadRequest(): Boolean {
        return code == BAD_REQUEST
    }

    fun isUnauthorized(): Boolean {
        return code == UNAUTHORIZED
    }

    fun isForbidden(): Boolean {
        return code == FORBIDDEN
    }

    fun isNotFound(): Boolean {
        return code == NOT_FOUND
    }

    fun isInternalServerError(): Boolean {
        return code == INTERNAL_SERVER_ERROR
    }

    fun isRoomEmptyResultSet(): Boolean {
        return code == ROOM_EMPTY_RESULT_SET
    }

    fun isRoomError(): Boolean {
        return code == ROOM_ERROR
    }

    fun isNoConnection(): Boolean {
        return code == NO_CONNECTION
    }

    fun isDescriptionLong(): Boolean {
        return msg == DESCRIPTION_TOO_LONG
    }

    fun isUserAlreadyExists(): Boolean {
        return msg == USER_ALREADY_EXISTS
    }

    override fun toString(): String {
        return "ErrorObject(code=$code, msg=$msg)"
    }

    companion object {
        const val NO_CONNECTION = 0
        const val UNKNOWN = 1
        const val ROOM_EMPTY_RESULT_SET = 2
        const val ROOM_ERROR = 3
        const val JSON_ERROR = 4
        const val UPLOAD_ERROR = 5
        const val NO_CONTENT = 204
        const val BAD_REQUEST = 400
        const val UNAUTHORIZED = 401
        const val FORBIDDEN = 403
        const val NOT_FOUND = 404
        const val INTERNAL_SERVER_ERROR = 500
        const val USER_ALREADY_EXISTS =
            "Validation failed: name should not be empty, lastName should not be empty"
        const val DESCRIPTION_TOO_LONG =
            "SequelizeDatabaseError: value too long for type character varying(255)"
    }
}

data class ErrorResponse(val error: String, val message: String)