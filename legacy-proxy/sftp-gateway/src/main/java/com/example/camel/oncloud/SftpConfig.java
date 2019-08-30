package com.example.camel.oncloud;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SftpConfig {
    public final static String ADDITION_OPTIONS = "&streamDownload=true&useUserKnownHostsFile=false";
    @Value("${sftp.hostname}")
    String hostName;
    @Value("${sftp.username}")
    String userName;
    @Value("${sftp.password}")
    String password;
    @Value("${sftp.pollFolder}")
    String pollFolder;
    @Value("${sftp.postProcess.archive}")
    String postProcessArchive;
    @Value("${sftp.workDirectory")
    String workDirectory;

    public String getURI() {
        //TODO Add some validation to the configuration
        String result = String.format("sftp://%s@%s/%s?password=%s&move=%s&localWorkDirectory=%s", userName, hostName, pollFolder, password, postProcessArchive, workDirectory);
        //Setup other options here
        result = result + ADDITION_OPTIONS;
        return result;
    }

}
