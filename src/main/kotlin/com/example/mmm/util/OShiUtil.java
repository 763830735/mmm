package com.example.mmm.util;

import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.software.os.OperatingSystem;

import java.util.ArrayList;
import java.util.List;

public class OShiUtil {
    static List<String> oshi = new ArrayList<>();

    public static List<String> getDeviceInfo() {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        OperatingSystem os = si.getOperatingSystem();

        printOperatingSystem(os);

        printProcessor(hal.getProcessor());

        printMemory(hal.getMemory());

        printDisks(hal.getDiskStores());

        printDisplays(hal.getDisplays());

        printGraphicsCards(hal.getGraphicsCards());

        return oshi;
    }
    private static void printOperatingSystem(final OperatingSystem os) {
        oshi.add("操作系统:");
        oshi.add("  "+os);
    }
    private static void printProcessor(CentralProcessor processor) {
        oshi.add("CPU型号:");
        oshi.add("  "+processor.getProcessorIdentifier().getName());
    }
    private static void printMemory(GlobalMemory memory) {
        oshi.add("内存:");
        oshi.add("  "+memory.getTotal());
    }
    private static void printDisks(List<HWDiskStore> list) {
        oshi.add("硬盘");
        for (HWDiskStore disk : list) {
            oshi.add("  " + disk.getModel()+","+disk.getSize()/1024/1024/1024+"GB");
        }

    }
    private static void printDisplays(List<Display> list) {
        oshi.add("显示器:");
        int i = 0;
        for (Display display : list) {
            oshi.add("  显示器" + i + ":");
            oshi.add(display.toString().replace("\t\n","  \t\n"));
            i++;
        }
    }
    private static void printGraphicsCards(List<GraphicsCard> list) {
        oshi.add("显卡:");
        if (list.isEmpty()) {
            oshi.add("  !没有显卡");
        } else {
            for (GraphicsCard card : list) {
                oshi.add("  " + card.getName());
            }
        }
    }
}
