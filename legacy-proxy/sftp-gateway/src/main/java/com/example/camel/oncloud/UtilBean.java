package com.example.camel.oncloud;

import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@Component
public class UtilBean {
    public String calcMd5Checksum(File file) throws Exception {
        InputStream inputStream = new DataInputStream(new FileInputStream(file));
        String md5 = DigestUtils.md5DigestAsHex(inputStream);
        inputStream.close();
        return md5;
    }
}
