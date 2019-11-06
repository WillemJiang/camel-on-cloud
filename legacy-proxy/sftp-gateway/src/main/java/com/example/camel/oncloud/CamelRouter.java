/*
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

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


        from("file:" + localFileConfig.stagingFolder + "?delete=true&readLock=changed").routeId("local_file_processing")
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
