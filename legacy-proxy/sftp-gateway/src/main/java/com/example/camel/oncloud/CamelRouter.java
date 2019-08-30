package com.example.camel.oncloud;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CamelRouter extends RouteBuilder {
    @Autowired
    SftpConfig sftpConfig;

    @Autowired
    LocalFileConfig localFileConfig;

    @Override
    public void configure() {
        
        from(sftpConfig.getURI()).routeId("sftp_downloading")
            .to("file:" + localFileConfig.stagingFolder)
            .log("staged file ${header.CamelFileName}");


        from("file:" + localFileConfig.stagingFolder + "?delete=true&readLock=changed")
             // Delete the staged file after processing to clean up resources
            .log("processing staged file : ${header.CamelFileName}")
            .inOnly("file:" + localFileConfig.archiveFolder)
            .to("bean:utilBean?method=calcMd5Checksum(${body})")
            .log("Checksum = %{body}")
            .setHeader("CamelFileName", simple("${headers.CamelFileName}.md5"))
            .to("file:" + localFileConfig.checksumFolder);

        // TODO aggregate the download file for notification
    }

}
