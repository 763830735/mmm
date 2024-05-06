package com.example.mmm.util

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream
import java.awt.Rectangle
import java.awt.Robot
import java.awt.Toolkit
import java.util.*
import javax.imageio.ImageIO

class ScreenCapture {
    companion object{
        @Throws(Exception::class)
        fun capture(): String? {
            val robot = Robot()
            val screenRect = Rectangle(Toolkit.getDefaultToolkit().screenSize)
            val screenCapture = robot.createScreenCapture(screenRect)
            // 这里可以处理截取到的屏幕画面，如保存为图片文件或传送给客户端
            // 将BufferedImage转换为字节数组
            val outputStream = ByteArrayOutputStream()
            try {
                ImageIO.write(screenCapture, "jpg", outputStream)
            } catch (e: Exception) {
                e.printStackTrace()
                // 处理异常情况
            }
            return Base64.getEncoder().encodeToString(outputStream.toByteArray())
        }
    }
}
