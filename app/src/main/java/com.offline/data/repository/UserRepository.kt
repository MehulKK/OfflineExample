
package com.offline.data.repository

import com.offline.data.remote.ApiService
import com.offline.model.data.User
import com.offline.utils.extension.response
import javax.inject.Inject
import javax.inject.Singleton

interface UserRepository {
    /**
     * Loads [List] of [User]
     */
    suspend fun loadUsers(page: Int = 1): List<User>
}

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): UserRepository {
    override suspend fun loadUsers(page: Int): List<User> {
        return apiService.loadUsers(page).response().results
    }
}
