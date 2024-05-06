//package com.example.mmm
//
//import com.example.mmm.util.PasswordUtil
//import java.security.MessageDigest
//
//fun main(){
//    val plainText = ""
//    // 获取指定摘要算法的messageDigest对象
//    val messageDigest: MessageDigest = MessageDigest.getInstance("SHA") // 此处的sha代表sha1
//    // 调用digest方法，进行加密操作
//    val cipherBytes: ByteArray = messageDigest.digest(plainText.toByteArray())
//    val cipherStr: String = PasswordUtil.bytesToHex(cipherBytes)
//    println(cipherStr)
//}