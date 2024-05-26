package com.example.mmm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ShutDownHookUtil {
    private static final Logger logger= LoggerFactory.getLogger(ShutDownHookUtil.class);
    static {
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            logger.info("虚拟机退出");
        }));
    }
}
