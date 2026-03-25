package com.fatec.att_lddm_ktor

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform