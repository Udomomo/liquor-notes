package com.udomomo.liquornotes.security

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

data class LoginRequestParams(
    val username: String,
    val password: String,
)

class ApiUsernamePasswordAuthenticationFilter(authenticationManager: AuthenticationManager) :
    UsernamePasswordAuthenticationFilter(authenticationManager) {
        // kotlin data classにdeserializeするためには、jackson-module-kotlinのmapperを使う必要がある。
        private val objectMapper = jacksonObjectMapper()

        init {
            // ログインリクエスト時にこのフィルタを動作させるために必要。
            setRequiresAuthenticationRequestMatcher(AntPathRequestMatcher("/api/login", "POST"))

            // 認証成功/失敗時のレスポンスを設定。
            this.setAuthenticationSuccessHandler { _, response, _ ->
                response.status = HttpServletResponse.SC_OK
            }
            this.setAuthenticationFailureHandler { _, response, _ ->
                response.status = HttpServletResponse.SC_UNAUTHORIZED
            }
        }

        override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
            val loginRequestParams = objectMapper.readValue(request.inputStream, LoginRequestParams::class.java)
            val authenticationToken =
                UsernamePasswordAuthenticationToken(loginRequestParams.username, loginRequestParams.password)
            return authenticationManager.authenticate(authenticationToken)
        }
    }
