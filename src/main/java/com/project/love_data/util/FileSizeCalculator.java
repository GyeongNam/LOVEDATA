package com.project.love_data.util;

public class FileSizeCalculator {
    public String execute(long size) {
        int i = 0;
        long tempSize = size;
        while (true) {
            if (tempSize >= 1024) {
                tempSize = tempSize / 1024;
                i++;
            } else {
                break;
            }
        }

        switch (i) {
            case 0 :
                return tempSize + " " + FileSize.Byte.toString();
            case 1 :
                return tempSize + " " + FileSize.KB.toString();
            case 2 :
                return tempSize + " " + FileSize.MB.toString();
            case 3 :
                return tempSize + " " + FileSize.GB.toString();
            case 4 :
                return tempSize + " " + FileSize.TB.toString();
            case 5 :
            default:
                return tempSize + " " + FileSize.PB.toString();
        }
    }

    enum FileSize {
        Byte,
        KB,
        MB,
        GB,
        TB,
        PB
    }
}

