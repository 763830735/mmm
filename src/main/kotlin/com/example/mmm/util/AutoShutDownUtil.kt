package com.example.mmm.util

import org.slf4j.LoggerFactory
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*


/**
 * 枚举下课时间,在这些时间自动关机
 * 配置在resources目录下,可以实时修改
 */
private val logger= LoggerFactory.getLogger(AutoShutDownUtil::class.java)
class AutoShutDownUtil {
    companion object{
        class DoSomething: TimerTask() {
            override fun run() {
                logger.info("正在执行自动关机")
                GuiUtil.showCancelDialog()
            }
        }

        /**
         * 获取到shutDownTime.txt中预设的关机时间
         */
        fun getShutDownTime():List<String>{
//            val resource = AutoShutDownUtil::class.java.classLoader.getResource("shutDownTime.txt")
//            val path = resource.path
            val file=File("shutDownTime.mmm")
            if (!file.exists()){
                logger.error("shutDownTime.mmm不存在")
                return ArrayList()
            }
            val fileOutputStream = FileReader(file)
            val bufferedReader = BufferedReader(fileOutputStream)

            val arrayList = ArrayList<String>()
            var time:String?=bufferedReader.readLine()
            while (time!=null){
                arrayList.add(time)
                time=bufferedReader.readLine()
            }
            logger.info("设置自动关机时间:{}",arrayList)
            return arrayList
        }
        fun shutDownAtTime(list: List<String>){
            val timer = Timer()
            for (s in list){
                val parts= s.split(":")
                val hour: Int = parts[0].toInt()
                val minute: Int = parts[1].toInt()
                val time = LocalTime.of(hour, minute)
                // 结合今天的日期和时间创建LocalDateTime对象
                val dateTime = LocalDateTime.of(LocalDate.now(), time)
                //如果任务开始时间小于当前时间,该任务会立刻执行,需要排除掉
                if(Date().after(Date.from(dateTime.atZone(java.time.ZoneId.systemDefault()).toInstant())))
                    continue
                timer.schedule(
                    DoSomething(), Date.from(dateTime.atZone(java.time.ZoneId.systemDefault()).toInstant()),86400000L
                )
            }
        }
    }
}