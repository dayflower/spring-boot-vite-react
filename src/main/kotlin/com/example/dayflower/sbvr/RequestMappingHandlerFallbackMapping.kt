package com.example.dayflower.sbvr

import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping
import javax.servlet.http.HttpServletRequest

class RequestMappingHandlerFallbackMapping : RequestMappingHandlerMapping() {
    override fun lookupHandlerMethod(lookupPath: String, request: HttpServletRequest): HandlerMethod? {
        val handlerMethod = super.lookupHandlerMethod(lookupPath, request)

        if (handlerMethod == null) {
            val handlerMethods = super.getHandlerMethodsForMappingName("proxyRequest")

            return handlerMethods?.firstOrNull()
        }

        return handlerMethod
    }
}
