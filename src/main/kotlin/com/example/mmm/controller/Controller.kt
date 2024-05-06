package com.example.mmm.controller

import com.example.mmm.MmmApplication
import com.example.mmm.service.FileService
import com.example.mmm.service.VideoService
import com.example.mmm.util.*
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.net.InetAddress
import java.util.*

private val logger= LoggerFactory.getLogger(MmmApplication::class.java)

@RestController
class MessageController {

    @RequestMapping("/")
    fun index():String {
        val list = mutableListOf<String>()
        list.run {
            add("欢迎来到mmm")
//            add("这是一个简单的教师机管理软件")
//            add("以下是功能和路径,需要/login password")
//            add("获取网络信息 /network")
//            add("获取硬件信息 /deviceInfo")
//            add("关机(默认延迟60s) /shutdown time")
//            add("取消关机 /shutdown-cancel")
//            add("运行cmd命令 /cmd command")
//            add("客户端发现服务器路径 /find")
//            add("截图 /currentScreen")
        }
        logger.info("welcome")
        return list.toString()
//        return list.joinToString("\n")
    }
    /**
     * 返回网络信息
     * ipv4 ipv6 DNS 网关 mac地址
     */
    @RequestMapping("/network")
    fun network():String{
        return CMDUtil.run("ipconfig /all")
    }
    /**
     * 返回电脑的硬件信息
     * CPU 内存 硬盘 显卡 显示器 操作系统
     */
    @RequestMapping("/deviceInfo")
    fun deviceInfo(): MutableList<String>? {
        return OShiUtil.getDeviceInfo()
    }
    /**
     * 远程关机,如果请求体中存在time,代表延迟time秒,否则默认30s
     */
    @RequestMapping("/shutdown")
    fun shutdown(@RequestBody body: Map<String,String>):String{
        var time: String? = body["time"]
        time=if (time.isNullOrBlank()) "60" else time
        return CMDUtil.run("shutdown -s -t $time")
    }

    /**
     *
     */
    @RequestMapping("/shutdown-cancel")
    fun shutdownCancel():String{
        return CMDUtil.run("shutdown -a")
    }
    /**
     * 运行cmd命令
     */
    @RequestMapping("/cmd")
    fun runCmdCommand(@RequestBody body: Map<String, String>):String{
        val command: String? = body["command"]
        return if (command==null)
            "请输入指令"
        else
            CMDUtil.run(command)
    }
    /**
     * 发现路径,用于客户端寻找活跃的服务器
     */
    @RequestMapping("/find")
    fun find():String{
        return "${RoomName.getRoomName()} ${InetAddress.getLocalHost().hostAddress}"
    }

    /**
     * 捕捉当前的服务器画面
     * 返回的是jpg格式的字节数组的base64字符串
     */
    @RequestMapping("/currentScreen")
    fun currentScreen(): String? {
        return ScreenCapture.capture()
    }

    @Autowired
    lateinit var fileService:FileService
    /**
     * 传输文件至用户的下载目录
     */
    @RequestMapping("/transferToDownload")
    fun transferToDownload(@RequestParam files:List<MultipartFile>):String{
        if (files.isEmpty()){
            return "请添加文件"
        }
        val transferToDownload = fileService.transferToDownload(files)
        return if (transferToDownload==="200")
            "上传成功"
        else
            transferToDownload
    }
    /**
     * 传输文件至项目根目录
     */
    @RequestMapping("/transferToRoot")
    fun transferToRoot(@RequestParam files:List<MultipartFile>):String{
        if (files.isEmpty()){
            return "请添加文件"
        }
        val transferToDownload = fileService.transferToRootNormal(files)
        return if (transferToDownload==="200")
            "上传成功"
        else
            transferToDownload
    }
    /**
     * gui输出信息
     */
    @RequestMapping("/showInfo")
    fun showInfo(@RequestBody str:Map<String,String>){
        logger.info("showInfo {}",str)
        GuiUtil.showMessageDialog(str["str"])
    }
    /**
     * 重启电脑
     */
    @RequestMapping("/restartPC")
    fun restartPC(){
        CMDUtil.run("shutdown /r /t 0")
    }

    @Autowired
    lateinit var videoService:VideoService
    /**
     * 调用服务端摄像头拍摄一张jpg格式的图片
     * 返回字节数组的base64字符串
     */
    @RequestMapping("/transferRecord")
    fun transferRecord():String?{
        val photoCapture = videoService.photoCapture()
        return if (photoCapture==null)
            null
        else
            Base64.getEncoder().encodeToString(photoCapture)
    }
    /**
     * 更新mmm的jar包和mmm-run.bat,然后运行mmm-run-always.bat,然后运行mmm-restart.bat(这个脚本先运行mmm-stop.bat,然后运行mmm-run.bat)
     */
    @RequestMapping("/updateMmm")
    fun updateMmm(@RequestParam files:List<MultipartFile>):String{
        if (files.isEmpty()){
            return "请添加文件"
        }
        if (files.size!=2)
            return "应该包含两个文件,jar和修改后的mmm-run.bat"
        for (file in files){
            if (!file.name.contains("jar") && (file.name == "mmm-run.bat"))
                return "应该包含两个文件,jar和修改后的mmm-run.bat"
        }
        val transferToRoot = fileService.transferToRoot(files)
        return if (transferToRoot=="200"){
            val run = CMDUtil.run("mmm-run-always.bat")
            logger.info("运行mmm-run-always.bat:{}",run)
            run
        }
        else
            transferToRoot
    }
    /**
     * 重新启动程序(运行mmm-restart.bat)
     */
    @RequestMapping("/restartMmm")
    fun restartMmm(){
        CMDUtil.run("mmm-restart.bat")
    }
    @Value("\${mmm-version}")
    private lateinit var version:String
    /**
     * 查看版本号
     */
    @RequestMapping("/version")
    fun version():String{
        return version
    }
    /**
     * 读取某个路径的那个文件的内容
     */
    @RequestMapping("/cat")
    fun cat(@RequestBody body: Map<String, String>):String{
        val path: String? = body["path"]
        return if (path==null)
            "请输入path"
        else
            fileService.catPathFile(path)
    }
}