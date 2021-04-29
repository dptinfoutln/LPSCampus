package com.univtln.univTlnLPS.common;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class WriteFile {
    public static boolean createFile(String path, String fileName) throws IOException {
        File directory = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + path);
        directory.mkdirs();
        File myFile = new File(directory.getPath() + File.separator, fileName);
        return myFile.createNewFile();
    }

    public static boolean writeToFile(String data, String path, String fileName, char mode) throws IOException {
        File myFile = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + path
                + File.separator, fileName);
        if(!myFile.exists()){
            return false;
        }
        OutputStreamWriter myOutWriter;
        if(mode != 'A')
            myOutWriter = new OutputStreamWriter(new FileOutputStream(myFile));
        else
            myOutWriter = new OutputStreamWriter(new FileOutputStream(myFile, true));
        myOutWriter.write(data);
        myOutWriter.close();

        return true;
    }
}
