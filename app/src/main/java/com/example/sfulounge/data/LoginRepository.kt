package com.example.sfulounge.data

import com.example.sfulounge.data.model.LoggedInUser

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(val dataSource: LoginDataSource) {

    // in-memory cache of the loggedInUser object
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = dataSource.isLoggedIn

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun logout() {
        user = null
        dataSource.logout()
    }

    fun getLoggedInUser(
        onSuccess: (Result.Success<LoggedInUser>) -> Unit,
        onError: (Result.Error) -> Unit
    ) {
        dataSource.getLoggedInUser(
            onSuccess = { result ->
                setLoggedInUser(result.data)
                onSuccess(result)
            },
            onError = onError
        )
    }

    fun login(
        email: String,
        password: String,
        onSuccess: (Result.Success<LoggedInUser>) -> Unit,
        onError: (Result.Error) -> Unit
    ) {
        // handle login
        dataSource.login(
            email,
            password,
            onSuccess = { result ->
                setLoggedInUser(result.data)
                onSuccess(result)
            },
            onError = onError
        )
    }


    fun register(
        email: String,
        password: String,
        onSuccess: (Result.Success<LoggedInUser>) -> Unit,
        onError: (Result.Error) -> Unit
    ) {
        // handle registration
        dataSource.register(
            email,
            password,
            onSuccess = { result ->
                setLoggedInUser(result.data)
                onSuccess(result)
            },
            onError = onError
        )
    }

    fun verifyUser(
        onIsVerified: () -> Unit,
        onError: (Result.Error) -> Unit
    ) {
        dataSource.verifyUser(onIsVerified, onError)
    }

    fun retrySendVerificationEmail(
        onSuccess: (Result.Success<LoggedInUser>) -> Unit,
        onError: (Result.Error) -> Unit
    ) {
        dataSource.retrySendVerificationEmail(onSuccess, onError)
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}