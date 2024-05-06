package com.example.mmm.service

import com.example.mmm.util.CMDUtil
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileReader

private val logger= LoggerFactory.getLogger(FileService::class.java)

@Service
class FileService {
    /**
     * 传输文件到根目录
     */
    fun transferToRootNormal(files:List<MultipartFile>):String{
        try {
            var path = CMDUtil.run("cd")
            path= path.trim().substring(0,path.length-1)
            for (file in files) {
                val dest = File(path+"/"+file.originalFilename!!)
                logger.info("文件名:{},保存路径:{}",file.originalFilename,dest.absolutePath)
                file.transferTo(dest)
            }
        }catch (e:Exception){
            return e.toString()
        }
        return "200"
    }
    /**
     * 传输jar和mmm-run.bat到根目录
     */
    fun transferToRoot(files:List<MultipartFile>):String{
        try {
            var path = CMDUtil.run("cd")
            path= path.trim().substring(0,path.length-1)
            for (file in files) {
                val dest = File(path+"/"+file.originalFilename!!)
                if (file.originalFilename!!.contains("jar"))
                    if (dest.exists())
                        return "存在同名的jar包"
                logger.info("文件名:{},保存路径:{}",file.originalFilename,dest.absolutePath)
                file.transferTo(dest)
            }
        }catch (e:Exception){
            return e.toString()
        }
        return "200"
    }
    /**
     * 传输文件到用户的下载目录
     */
    fun transferToDownload(files:List<MultipartFile>):String{
        try {
            var user = CMDUtil.run("echo %USERPROFILE%")
            user= user.trim().substring(0,user.length-1)+"/Downloads/"
            val dir = File(user)
            if (!dir.exists()) {
                dir.mkdirs()
            }
            for (file in files){
                // 生成保存文件的路径
                val filePath: String = user + file.originalFilename
                logger.info("文件名:{},保存路径:{}",file.originalFilename,filePath)
                // 将文件保存到服务器上
                val dest = File(filePath)
                file.transferTo(dest)
            }
        }catch (e:Exception){
            return e.toString()
        }
        return "200"
    }

    fun catPathFile(path: String): String {
        val file = File(path)
        val fileReader = FileReader(file)
        return fileReader.readText()
    }
}