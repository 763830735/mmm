package com.example.mmm.util

import org.slf4j.LoggerFactory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.swing.JFrame
import javax.swing.JOptionPane

private val logger= LoggerFactory.getLogger(GuiUtil::class.java)

class GuiUtil {
    companion object {
        fun showMessageDialog(str: String?) {
            val jFrame = JFrame()
            jFrame.setAlwaysOnTop(true)
            JOptionPane.showMessageDialog(jFrame, str.toString(), "图文信息中心", JOptionPane.INFORMATION_MESSAGE)
            jFrame.dispose()
        }

        fun showMessageDialog(str: String?, title: String) {
            val jFrame = JFrame()
            jFrame.setAlwaysOnTop(true)
            JOptionPane.showMessageDialog(jFrame, str.toString(), title, JOptionPane.INFORMATION_MESSAGE)
            jFrame.dispose()
        }

        fun showCancelDialog() {
            CMDUtil.run("shutdown -s -t 60")
            val jFrame = JFrame()
            jFrame.setAlwaysOnTop(true)
            val result = JOptionPane.showOptionDialog(
                jFrame,
                "你的电脑将在1分钟内自动关机",  // 对话框显示的消息
                "图文信息中心",  // 对话框的标题
                JOptionPane.YES_OPTION,  // 按钮的类型
                JOptionPane.INFORMATION_MESSAGE,  // 消息类型
                null, arrayOf("取消关机"),  // 按钮文本
                "点击按钮"
            )
            if (result==JOptionPane.YES_OPTION){
                CMDUtil.run("shutdown -a")
                showMessageDialog("你已取消自动关机")
                logger.info("取消自动关机")
            }
            jFrame.dispose()
        }
    }
}