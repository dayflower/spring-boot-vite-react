package com.example.dayflower.sbvr

import org.springframework.context.annotation.Profile
import org.springframework.http.*
import org.springframework.stereotype.Controller
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.ResourceAccessException
import org.springframework.web.client.RestTemplate
import java.net.ConnectException
import java.net.URI
import javax.servlet.http.HttpServletRequest

@Profile("local")
@Controller
class ProxyController(
    private val restTemplate: RestTemplate,
) {
    companion object {
        private const val FRONTEND_HOST = "localhost"
        private const val FRONTEND_PORT = 3000
        private val CONTENT_TYPE_JSON_HEADERS =
            HttpHeaders.readOnlyHttpHeaders(
                LinkedMultiValueMap<String, String>().apply {
                    add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                }
            )
    }

    @RequestMapping(name = "proxyRequest")
    fun proxyRequest(
        method: HttpMethod,
        @RequestBody(required = false)
        body: ByteArray?,
        @RequestHeader
        headers: HttpHeaders,
        req: HttpServletRequest,
    ): ResponseEntity<ByteArray> {
        val uri = URI("http", null, FRONTEND_HOST, FRONTEND_PORT, req.requestURI, req.queryString, null)
        val entity = HttpEntity<ByteArray>(body, headers)

        return try {
            restTemplate.exchange(uri, method, entity, ByteArray::class.java)
        } catch (ex: HttpClientErrorException) {
            ResponseEntity.status(ex.statusCode)
                .headers(ex.responseHeaders)
                .body(ex.responseBodyAsByteArray)
        } catch (ex: ResourceAccessException) {
            if (ex.rootCause is ConnectException) {
                return ResponseEntity
                    .status(HttpStatus.BAD_GATEWAY)
                    .headers(CONTENT_TYPE_JSON_HEADERS)
                    .body("""{ "status": 502, "error": "bad_gateway", "detail": "connect error" }""".toByteArray())
            }

            throw ex
        }
    }
}
