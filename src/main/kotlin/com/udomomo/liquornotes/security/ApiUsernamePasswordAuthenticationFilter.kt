package com.udomomo.liquornotes.security

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

data class LoginRequestParams(
    val username: String,
    val password: String,
)

class ApiUsernamePasswordAuthenticationFilter(authenticationManager: AuthenticationManager) :
    UsernamePasswordAuthenticationFilter(authenticationManager) {
        private val objectMapper = ObjectMapper()
        override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
            val loginRequestParams = objectMapper.readValue(request.inputStream, LoginRequestParams::class.java)
            val authenticationToken =
                UsernamePasswordAuthenticationToken(loginRequestParams.username, loginRequestParams.password)
            return authenticationManager.authenticate(authenticationToken)
        }
    }
