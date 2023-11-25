package com.udomomo.liquornotes.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.context.HttpSessionSecurityContextRepository
import org.springframework.security.web.context.SecurityContextRepository
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

data class LoginRequestParams(
    val username: String,
    val password: String,
)

@RestController
class LoginController(private val authenticationManager: AuthenticationManager) {
    private val securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy()
    private val securityContextRepository: SecurityContextRepository = HttpSessionSecurityContextRepository()

    @PostMapping("/api/login")
    fun login(
        @RequestBody loginRequestParams: LoginRequestParams,
        request: HttpServletRequest,
        response: HttpServletResponse,
    ) {
        val authenticationToken =
            UsernamePasswordAuthenticationToken(loginRequestParams.username, loginRequestParams.password)
        val authentication = authenticationManager.authenticate(authenticationToken)

        val securityContext = securityContextHolderStrategy.createEmptyContext()
        securityContext.authentication = authentication
        securityContextHolderStrategy.context = securityContext
        securityContextRepository.saveContext(securityContext, request, response)
    }
}
