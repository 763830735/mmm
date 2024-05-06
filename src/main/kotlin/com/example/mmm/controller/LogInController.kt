package com.example.mmm.controller

import com.example.mmm.config.IpContainer
import com.example.mmm.util.PasswordUtil
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.MessageDigest


@RestController
class LoginController{
    @Value("\${password}")
    lateinit var encodePassword:String

    @Autowired
    lateinit var ipContainer: IpContainer

    @RequestMapping("/login")
    fun log(@RequestBody map: Map<String,String>,request: HttpServletRequest):String{
        val password = map["password"]
        if (password.isNullOrBlank())
            return "请输入密码"
//        val messageDigest: MessageDigest = MessageDigest.getInstance("SHA") // 此处的sha代表sha1
//        val digest = messageDigest.digest(password.toByteArray())
//        if (encodePassword != PasswordUtil.bytesToHex(digest))
        if (encodePassword != password)
            return "密码错误,登录失败"
        val clientIp = request.remoteAddr
        ipContainer.addIp(clientIp)
        return "登录成功"
    }
}
