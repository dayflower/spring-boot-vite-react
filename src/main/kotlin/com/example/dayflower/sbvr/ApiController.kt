package com.example.dayflower.sbvr

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ApiController {
    @GetMapping("/api/random")
    fun getRandom(): GetRandomResponse {
        return GetRandomResponse(Math.random().toString())
    }

    data class GetRandomResponse(
        val value: String
    )
}
