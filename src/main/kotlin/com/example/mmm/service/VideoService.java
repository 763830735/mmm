package com.example.mmm.service;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class VideoService {
    private static final Logger logger= LoggerFactory.getLogger(VideoService.class);

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    public byte[] photoCapture() {
        VideoCapture capture = new VideoCapture(0);

        if (!capture.isOpened()) {
            logger.error("Error: 无法打开摄像头");
            return null;
        }

        Mat frame = new Mat();
        MatOfByte buffer=new MatOfByte();
        capture.read(frame);
        //不然摄像头会被占用,导致其他应用无法使用摄像头
        capture.release();
        Imgcodecs.imencode(".jpg", frame, buffer);
        return buffer.toArray();
//        FileOutputStream fileOutputStream = new FileOutputStream("asd.jpg");
//        fileOutputStream.write(byteArray);
//        fileOutputStream.close();
    }
}
