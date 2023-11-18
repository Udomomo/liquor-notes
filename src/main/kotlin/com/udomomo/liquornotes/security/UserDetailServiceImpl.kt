package com.udomomo.liquornotes.security

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailServiceImpl(
    private val userRepository: UserRepository,
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val loginUser = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User not found")

        return LoginUserDetails(loginUser)
    }
}
