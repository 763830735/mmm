package com.example.mmm.util

import com.example.mmm.MmmApplication
import org.slf4j.LoggerFactory
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException

private val logger= LoggerFactory.getLogger(MmmApplication::class.java)

class RoomName {
    companion object{
        /**
         * 获取教室名
         */
        fun getRoomName(): String {
            val file: File = File("room-name.txt")
            if (!file.exists()) {
                file.createNewFile()
            }
            val fileReader = FileReader(file)
            return fileReader.readText()
        }

        /**
         * 修改教室名
         */
        fun updateRoomName(str:String){
            try {
                val file=File("room-name.txt")
                logger.info("{}",file.absolutePath)
                if (!file.exists())
                    file.createNewFile()
                logger.info("initial args:{}",str)
                val fileWriter = FileWriter(file)
                fileWriter.write(str)
                fileWriter.close() // 关闭文件写入流
            }catch (e:IOException){
                logger.error("Error writing to file: {}", e.message)
            }

        }
    }
}

