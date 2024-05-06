package com.example.mmm.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import java.time.LocalTime
import kotlin.math.absoluteValue

@Configuration
class IpContainer : HashMap<String, LocalTime>() {
    @Value("\${keep_time}")
    private lateinit var keepTime: String

    fun addIp(ip: String) = super.put(ip, LocalTime.now())
    /**
     * 每次有请求过来,过滤掉没有登录过的请求
     * 另外,如果之前太久没有活跃,也不能通过
     */
    fun isIpValid(ip: String, nowTime: LocalTime): Boolean {
        return if (super.get(ip) == null) false
        else {
            val until = super.get(ip)!!.until(nowTime, java.time.temporal.ChronoUnit.SECONDS)
            if (until.absoluteValue <= keepTime.toLong()) {
                super.replace(ip, nowTime)
                true
            } else {
                super.remove(ip)
                false
            }
        }
    }
}

