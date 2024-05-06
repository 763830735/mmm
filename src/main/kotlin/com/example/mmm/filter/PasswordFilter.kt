package com.example.mmm.filter

import com.example.mmm.config.IpContainer
import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.io.PrintWriter
import java.time.LocalTime


@Component
class PasswordFilter:Filter{
    @Autowired
    lateinit var ipContainer: IpContainer

    /**
     * 如果请求访问的是/或者/login,则通过
     * 如果请求访问的是其他,则进行过滤,只有isIpValid(ip,now())为true,才通过
     */
    override fun doFilter(p0: ServletRequest?, p1: ServletResponse?, p2: FilterChain?) {
        val httpServletRequest = p0 as HttpServletRequest
        val remoteAddr = httpServletRequest.remoteAddr
        if (httpServletRequest.requestURI.equals("/") || httpServletRequest.requestURI.equals("/login")||httpServletRequest.requestURI.equals("/find")||httpServletRequest.requestURI.equals("/version"))
            p2?.doFilter(p0, p1)
        else
            remoteAddr?.run {
                val ipValid = ipContainer.isIpValid(this, LocalTime.now())
                if (ipValid){
                    p2?.doFilter(p0, p1)
                    return
                }
                (p1 as HttpServletResponse).run{
                    this.setHeader("Content-Type","text/plain;charset=UTF-8")
                    // 获取响应的输出流
                    val out: PrintWriter = this.writer
                    // 向输出流中写入数据
                    out.println("请登录")
                }
                return
        }
    }
}