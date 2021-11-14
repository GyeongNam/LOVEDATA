package com.project.love_data.util;

import java.io.File;
import java.util.*;

public class FileLastDateComparator implements Comparator<File> {

    @Override
    public int compare(File f1, File f2) {
        if (f1.lastModified() > f2.lastModified()) {
            return 1;
        } else if (f1.lastModified() < f2.lastModified()) {
            return -1;
        } else {
            return 0;
        }
    }
}
