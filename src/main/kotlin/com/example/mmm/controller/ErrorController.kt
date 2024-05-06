package com.example.mmm.controller

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.resource.NoResourceFoundException


@ControllerAdvice
class ErrorController {
    /**
     * 全局捕获异常的切面类
     */
    @ExceptionHandler(NoResourceFoundException::class) //也可以只对一个类进行捕获
    fun errorHandler(request: HttpServletRequest, response: HttpServletResponse, e: Exception?) {
        response.setHeader("content-type","text/html")
        response.sendRedirect("404.html")
    }
}