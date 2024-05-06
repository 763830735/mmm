package com.example.mmm.config

import java.awt.SystemTray
import java.awt.TrayIcon
import javax.swing.ImageIcon


class TrayConfig {
    companion object{
        fun init(){
            // 判断是否支持系统托盘
            if (SystemTray.isSupported()) {
                // 获取图片所在的URL
                val url = TrayConfig::class.java.getResource("/static/icon.jpg")
                // 实例化图像对象
                val icon = ImageIcon(url)
                // 获得Image对象
                val image = icon.image
                // 创建托盘图标
                val trayIcon = TrayIcon(image)
                //这个不加就显示不了
                trayIcon.setImageAutoSize(true)
                // 为托盘添加鼠标适配器
//                trayIcon.addMouseListener(object : MouseAdapter() {
//                    // 鼠标事件
//                    override fun mouseClicked(e: MouseEvent) {
//                        // 判断是否双击了鼠标
//                        if (e.clickCount == 2) {
//
//                        }
//                    }
//                })
                // 添加工具提示文本
                trayIcon.setToolTip("mmm教室管理工具")
/*                // 创建弹出菜单
                val popupMenu = PopupMenu()
                //会乱码
                popupMenu.add(MenuItem("联系管理员:陈俊华 1982085432 图文信息中心"))
                // 为托盘图标加弹出菜弹
                trayIcon.setPopupMenu(popupMenu)*/
                // 获得系统托盘对象
                val systemTray = SystemTray.getSystemTray()
                try {
                    // 为系统托盘加托盘图标
                    systemTray.add(trayIcon)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}
