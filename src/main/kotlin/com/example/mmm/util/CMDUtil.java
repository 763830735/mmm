package com.example.mmm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class CMDUtil {
    private static final Logger logger=LoggerFactory.getLogger(CMDUtil.class);
    public static String run(String command) {
        StringBuilder result= new StringBuilder();
        BufferedReader br = null;
        try {
            File tmpFile = new File("./temp.tmp");//新建一个用来存储结果的缓存文件
            if (!tmpFile.exists()) {
                tmpFile.createNewFile();
            }
            ProcessBuilder pb = new ProcessBuilder().command("cmd.exe", "/c", command).inheritIO();
            pb.redirectErrorStream(true);//这里是把控制台中的红字变成了黑字，用通常的方法其实获取不到，控制台的结果是pb.start()方法内部输出的。
            pb.redirectOutput(tmpFile);//把执行结果输出。
            pb.start().waitFor();//等待语句执行完成，否则可能会读不到结果。
            InputStream in = new FileInputStream(tmpFile);
            br = new BufferedReader(new InputStreamReader(in,"gbk"));
            String line = null;
            while ((line = br.readLine()) != null) {
                result.append(line).append("\n");
            }
            br.close();
            br = null;
            tmpFile.delete();//卸磨杀驴。
        } catch (Exception e) {
            logger.error(e.toString());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    logger.error(e.toString());
                }
            }
        }
        return result.toString();
    }
}