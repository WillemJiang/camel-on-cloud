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
