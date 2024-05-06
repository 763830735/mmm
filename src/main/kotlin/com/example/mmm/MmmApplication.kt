package com.example.mmm

import com.example.mmm.config.TrayConfig
import com.example.mmm.util.AutoShutDownUtil
import com.example.mmm.util.CMDUtil
import com.example.mmm.util.GuiUtil
import com.example.mmm.util.RoomName
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.io.File
import kotlin.system.exitProcess

private val logger= LoggerFactory.getLogger(MmmApplication::class.java)


@SpringBootApplication
class MmmApplication

fun main(args: Array<String>) {
    runApplication<MmmApplication>(*args)
    System.setProperty("java.awt.headless", "false")
    if (args.isNotEmpty()){
        RoomName.updateRoomName(args.joinToString("-"))
    }
    else{
        if (RoomName.getRoomName()==""){
            logger.error("启动失败,请输入教室名称")
            GuiUtil.showMessageDialog("启动失败,请输入教室名称")
            exitProcess(-1)
        }
    }
    TrayConfig.init()
    /*设置开机自启*/
    var startup = CMDUtil.run("echo %APPDATA%/Microsoft/Windows/Start Menu/Programs/Startup")
    startup=startup.substring(0,startup.length-1)
    val mmmRun=File("$startup/mmm-run.bat")
    if (!mmmRun.exists()){
        val result = CMDUtil.run("mmm-run-always.bat")
        GuiUtil.showMessageDialog(result,"开机自启结果:")
    }
    /*
    设置自动关机
     */
    AutoShutDownUtil.shutDownAtTime(AutoShutDownUtil.getShutDownTime())
}
