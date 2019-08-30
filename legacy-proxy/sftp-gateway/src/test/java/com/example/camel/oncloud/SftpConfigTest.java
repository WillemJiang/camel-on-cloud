package com.example.camel.oncloud;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SftpConfigTest {

    @Test
    public void getURI() {
        SftpConfig sftpConfig = new SftpConfig();
        sftpConfig.hostName = "h1";
        sftpConfig.password = "pass";
        sftpConfig.userName = "user";
        sftpConfig.pollFolder = "/directory1";
        sftpConfig.postProcessArchive = "/directory2";
        sftpConfig.workDirectory = "/local/work";

        assertThat("sftp://user@h1//directory1?password=pass&move=/directory2&localWorkDirectory=/local/work" + SftpConfig.ADDITION_OPTIONS ,
                is(sftpConfig.getURI()));
    }
}
