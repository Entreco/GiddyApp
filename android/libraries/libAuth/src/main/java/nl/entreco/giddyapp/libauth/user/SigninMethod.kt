package nl.entreco.giddyapp.libauth.user

sealed class SigninMethod {
    data class Google(val googleIdToken: String) : SigninMethod()
    data class EmailSignIn(val email: String, val password: String) : SigninMethod()
}