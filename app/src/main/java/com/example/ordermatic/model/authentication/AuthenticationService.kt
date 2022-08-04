package com.example.ordermatic.model.authentication

import com.example.ordermatic.model.CompanyAccount
import de.thm.tp.library.firebase.authentication.TPFirebaseAuthentication

class AuthenticationService {
    companion object {
        fun createCompanyAccount(
            email: String,
            password: String,
            callback: (CompanyAccount?, String?) -> Unit
        ) {
            TPFirebaseAuthentication.signUp(email, password) { user, error ->
                user?.let { userObject ->
                    callback(CompanyAccount(userObject.uid, userObject.email), null)
                }
                error?.let { errorString ->
                    callback(null, errorString)
                }
            }
        }

        fun signInToCompanyAccount(
            email: String,
            password: String,
            callback: (CompanyAccount?, String?) -> Unit
        ) {
            TPFirebaseAuthentication.signIn(email, password) { user, error ->
                user?.let { userObject ->
                    callback(CompanyAccount(userObject.uid, userObject.email), null)
                }
                error?.let { errorString ->
                    callback(null, errorString)
                }
            }
        }

        fun signOutFromCompanyAccount() {
            TPFirebaseAuthentication.signOut()
        }

        fun getCurrentCompanyAccount(): CompanyAccount? {
            val user = TPFirebaseAuthentication.getUser()
            user?.let { userObject ->
                return CompanyAccount(userObject.uid, userObject.email)
            }
            return null
        }
    }
}